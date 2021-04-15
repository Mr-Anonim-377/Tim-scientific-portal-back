package com.tim.scientific.portal.back.service;

import com.tim.scientific.portal.back.db.models.Content;
import com.tim.scientific.portal.back.db.models.Module;
import com.tim.scientific.portal.back.db.models.ModulesObject;
import com.tim.scientific.portal.back.db.models.Page;
import com.tim.scientific.portal.back.db.models.crm.type.ContentType;
import com.tim.scientific.portal.back.db.models.crm.type.ModulesObjectType;
import com.tim.scientific.portal.back.db.models.crm.type.ModulesType;
import com.tim.scientific.portal.back.db.models.crm.type.PageType;
import com.tim.scientific.portal.back.db.repository.*;
import com.tim.scientific.portal.back.utils.AbstractService;
import com.tim.scientific.portal.back.utils.Mapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.tim.scientific.portal.back.utils.Mapper.*;

@Service
public class CrmService extends AbstractService {

    Comparator<Page> pageComparator = Comparator.comparing(Page::getCreatedAt).reversed();

    final ModulesRepository modulesRepository;

    final ModulesObjectRepository modulesObjectRepository;

    final ContentsRepository contentsRepository;

    final PagesRepository pagesRepository;

    final PageTypeRepository pageTypeRepository;

    final ContentsTypeRepository contentsTypeRepository;

    final ModulesTypeRepository modulesTypeRepository;

    final ModulesObjectTypeRepository modulesObjectTypeRepository;

    public CrmService(ModulesRepository modulesRepository,
                      ModulesObjectRepository modulesObjectRepository,
                      ContentsRepository contentsRepository,
                      PagesRepository pagesRepository,
                      PageTypeRepository pageTypeRepository,
                      ContentsTypeRepository contentsTypeRepository,
                      ModulesTypeRepository modulesTypeRepository,
                      ModulesObjectTypeRepository modulesObjectTypeRepository) {
        this.modulesRepository = modulesRepository;
        this.modulesObjectRepository = modulesObjectRepository;
        this.contentsRepository = contentsRepository;
        this.pagesRepository = pagesRepository;
        this.pageTypeRepository = pageTypeRepository;
        this.contentsTypeRepository = contentsTypeRepository;
        this.modulesTypeRepository = modulesTypeRepository;
        this.modulesObjectTypeRepository = modulesObjectTypeRepository;
    }

    public com.tim.scientific.portal.back.dto.Page getDtoPageByType(String pageType) {
        return toDtoPage(getDbPageByType(pageType));
    }

    public List<com.tim.scientific.portal.back.dto.Page> getDtoPages() {
        return getDbPages().stream().map(Mapper::toDtoPage).collect(Collectors.toList());
    }

    public List<Page> getDbPages() {
        CheckedErrorSupplier<List<Page>> func = pagesRepository::findAll;
        List<Page> pagesByType = applySqlFunctionAndListAssert(func, isNotEmptyList());
        pagesByType.sort(pageComparator);
        return pagesByType;
    }

    public Page getDbPageByType(String pageType) {
        CheckedErrorFunction<String, List<Page>> func = pagesRepository::findAllByPageType_TypeValue;
        List<Page> pagesByType = applySqlFunctionAndListAssert(func, pageType, isNotEmptyList());
        pagesByType.sort(pageComparator);
        return pagesByType.get(0);
    }

    public com.tim.scientific.portal.back.dto.Page getDtoPageById(UUID pageId) {
        CheckedErrorFunction<UUID, List<Page>> sqlFunction = pagesRepository::findAllByPageId;
        List<Page> pagesById = applySqlFunctionAndListAssert(sqlFunction, pageId, isNotEmptyList());
        pagesById.sort(pageComparator);
        return toDtoPage(pagesById.get(0));
    }

    public List<com.tim.scientific.portal.back.dto.Module> getDtoModules(UUID pageId) {
        CheckedErrorFunction<UUID, List<Module>> sqlFunction = modulesRepository::findAllByPage_PageId;
        List<Module> allByPageId = applySqlFunctionAndListAssert(sqlFunction, pageId, isNotEmptyList());
        return allByPageId.stream().map(Mapper::toDtoModule).collect(Collectors.toList());
    }

    public com.tim.scientific.portal.back.dto.Page getDtoPageByPage(String pageType) {
        Page dbPageByType = getDbPageByType(pageType);
        return toDtoPage(dbPageByType);
    }

    public com.tim.scientific.portal.back.dto.Module getDtoModule(UUID modulesId) {
        Module modelsByModuleId = getDbModule(modulesId);
        return toDtoModule(modelsByModuleId);
    }

    private Module getDbModule(UUID modulesId) {
        CheckedErrorFunction<UUID, Module> sqlFunction = modulesRepository::findByModuleId;
        Module modelsByModuleId = applyHibernateQuery(modulesId, sqlFunction);
        objectAssert(new NullPointerException(), isNotEmpty(), modelsByModuleId);
        return modelsByModuleId;
    }


    public List<com.tim.scientific.portal.back.dto.ModulesObject> getDtoModulesObjects(UUID modulesId) {
        List<ModulesObject> modulesObjects = getDbModule(modulesId).getModulesObjects();
        listAssert(modulesObjects, isNotEmptyList());
        return modulesObjects.stream()
                .map(Mapper::toDtoModulesObject)
                .sorted(Comparator.comparing(com.tim.scientific.portal.back.dto.ModulesObject::getObjectRank).reversed())
                .collect(Collectors.toList());
    }

    public List<com.tim.scientific.portal.back.dto.ModulesObject> getDtoModulesObjectsByTag(UUID modulesId, String tag) {
        CheckedErrorFunction<UUID, List<ModulesObject>> sqlFunction = uuid ->
                modulesObjectRepository.findByModule_ModuleId_AndTag_Tag(uuid, tag);
        List<ModulesObject> modulesObjects = applyHibernateQuery(modulesId, sqlFunction);
        listAssert(modulesObjects, isNotEmptyList());
        return modulesObjects.stream()
                .map(modulesObject ->
                        toDtoModulesObject(modulesObject)
                                .contents(contentsRepository
                                        .findAllByModulesObject_ModulesObjectsId(modulesObject.getModulesObjectsId())
                                        .stream()
                                        .map(Mapper::toDtoContent)
                                        .collect(Collectors.toList())))
                .sorted(Comparator.comparing(com.tim.scientific.portal.back.dto.ModulesObject::getObjectRank).reversed())
                .collect(Collectors.toList());
    }

    public ModulesObject getDbModuleObject(UUID objectId) {
        CheckedErrorFunction<UUID, ModulesObject> sqlFunction = modulesObjectRepository::findByModulesObjectsId;
        ModulesObject modulesObject = applySqlFunctionAndAssert(sqlFunction, objectId, isNotEmpty());
        objectAssert(new NullPointerException(), isNotEmpty(), modulesObject);
        return modulesObject;
    }


    public List<com.tim.scientific.portal.back.dto.Content> getContents(UUID objectId) {
        List<Content> modulesObjects = getDbModuleObject(objectId).getContents();
        listAssert(modulesObjects, isNotEmptyList());
        return modulesObjects.stream().map(Mapper::toDtoContent).collect(Collectors.toList());
    }

    public List<String> getContentTypes() {
        CheckedErrorSupplier<List<ContentType>> sqlFunction = contentsTypeRepository::findAll;
        List<ContentType> allTypes = applySqlFunctionAndListAssert(sqlFunction, isNotEmptyList());
        return allTypes.stream().map(ContentType::getTypeValue).collect(Collectors.toList());
    }

    public List<String> getModuleTypes() {
        CheckedErrorSupplier<List<ModulesType>> sqlFunction = modulesTypeRepository::findAll;
        List<ModulesType> allTypes = applySqlFunctionAndListAssert(sqlFunction, isNotEmptyList());
        return allTypes.stream().map(ModulesType::getTypeValue).collect(Collectors.toList());
    }

    public List<String> getObjectTypes() {
        CheckedErrorSupplier<List<ModulesObjectType>> sqlFunction = modulesObjectTypeRepository::findAll;
        List<ModulesObjectType> allTypes = applySqlFunctionAndListAssert(sqlFunction, isNotEmptyList());
        return allTypes.stream().map(ModulesObjectType::getTypeValue).collect(Collectors.toList());
    }

    public List<String> getPageTypes() {
        CheckedErrorSupplier<List<PageType>> sqlFunction = pageTypeRepository::findAll;
        List<PageType> allTypes = applySqlFunctionAndListAssert(sqlFunction, isNotEmptyList());
        return allTypes.stream().map(PageType::getTypeValue).collect(Collectors.toList());
    }

    @SneakyThrows
    public com.tim.scientific.portal.back.dto.Page getDtoPageByPageTypeAndFilters(@Valid String pageType, @Valid String key,
                                                                                  @Valid String value) {
        CheckedErrorFunction<String, List<Page>> func = type ->
                pagesRepository.findByTypeAndMetaData(type, key, value);
        List<Page> pagesByType = applySqlFunctionAndListAssert(func, pageType, isNotEmptyList());
        pagesByType.sort(pageComparator);
        return toDtoPage(pagesByType.get(0));
    }
}