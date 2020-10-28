package com.tim.scientific.portal.back.service;

import com.tim.scientific.portal.back.db.models.Content;
import com.tim.scientific.portal.back.db.models.Module;
import com.tim.scientific.portal.back.db.models.ModulesObject;
import com.tim.scientific.portal.back.db.models.Page;
import com.tim.scientific.portal.back.db.repository.ContentsRepository;
import com.tim.scientific.portal.back.db.repository.ModulesObjectRepository;
import com.tim.scientific.portal.back.db.repository.ModulesRepository;
import com.tim.scientific.portal.back.db.repository.PagesRepository;
import com.tim.scientific.portal.back.dto.PageTypeEnum;
import com.tim.scientific.portal.back.utils.AbstractService;
import com.tim.scientific.portal.back.utils.Mapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.tim.scientific.portal.back.utils.Mapper.toDtoModule;
import static com.tim.scientific.portal.back.utils.Mapper.toDtoPage;

@Service
public class CrmService extends AbstractService {

    Comparator<Page> pageComparator = Comparator.comparing(Page::getCreatedAt).reversed();

    final ModulesRepository modulesRepository;

    final ModulesObjectRepository modulesObjectRepository;

    final ContentsRepository contentsRepository;

    final PagesRepository pagesRepository;

    public CrmService(ModulesRepository modulesRepository, ModulesObjectRepository modulesObjectRepository, ContentsRepository contentsRepository, PagesRepository pagesRepository) {
        this.modulesRepository = modulesRepository;
        this.modulesObjectRepository = modulesObjectRepository;
        this.contentsRepository = contentsRepository;
        this.pagesRepository = pagesRepository;
    }

    public com.tim.scientific.portal.back.dto.Page getDtoPageByType(PageTypeEnum pageType) {
        return toDtoPage(getDbPageByType(pageType));
    }

    public Page getDbPageByType(PageTypeEnum pageType) {
        CheckedErrorFunction<PageTypeEnum, List<Page>> func = pagesRepository::findAllByPageType;
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
        listAssert(allByPageId, isNotEmptyList());
        return allByPageId.stream().map(Mapper::toDtoModule).collect(Collectors.toList());
    }

    public List<com.tim.scientific.portal.back.dto.Module> getDtoModules(PageTypeEnum pageType) {
        List<Module> modules = getDbPageByType(pageType).getModules();
        listAssert(modules, isNotEmptyList());
        return modules.stream().map(Mapper::toDtoModule).collect(Collectors.toList());
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
        return modulesObjects.stream().map(Mapper::toDtoModulesObject).collect(Collectors.toList());
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
}