package com.tim.scientific.portal.back.utils;

import com.tim.scientific.portal.back.db.models.Content;
import com.tim.scientific.portal.back.db.models.Module;
import com.tim.scientific.portal.back.db.models.ModulesObject;
import com.tim.scientific.portal.back.db.models.Page;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.stream.Collectors;

public class Mapper {

    private static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }

    public static com.tim.scientific.portal.back.dto.Page toDtoPage(Page page) {
        return new com.tim.scientific.portal.back.dto.Page()
                .byPageData(page.getByPageData())
                .active(page.getActive())
                .createdAt(toDate(page.getCreatedAt()))
                .id(page.getPageId())
                .name(page.getName())
                .pageType(page.getPageType().getTypeValue())
                .modules(page.getModules().stream().map(Mapper::toDtoModule).collect(Collectors.toList()));
    }

    public static com.tim.scientific.portal.back.dto.Module toDtoModule(Module module) {
        return new com.tim.scientific.portal.back.dto.Module()
                .createdAt(toDate(module.getCreatedAt()))
                .id(module.getModuleId())
                .moduleType(module.getModuleType().getTypeValue())
                .name(module.getName())
                .objectCount(module.getObjectCount())
                .rank(module.getRank());
    }

    public static com.tim.scientific.portal.back.dto.Content toDtoContent(Content content){
        return new com.tim.scientific.portal.back.dto.Content()
                .contentType(content.getContentType().getTypeValue())
                .id(content.getContentId())
                .name(content.getName())
                .value(content.getValue());
    }

    public static com.tim.scientific.portal.back.dto.ModulesObject toDtoModulesObject(ModulesObject modulesObject){
        return new com.tim.scientific.portal.back.dto.ModulesObject()
        .id(modulesObject.getModulesObjectsId())
        .name(modulesObject.getName())
        .objectRank(modulesObject.getObjectRank())
        .objectType(modulesObject.getModulesObjectType().getTypeValue())
        .pageLink(modulesObject.getPageLink());
    }

}