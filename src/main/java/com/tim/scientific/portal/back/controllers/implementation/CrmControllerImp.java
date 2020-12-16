package com.tim.scientific.portal.back.controllers.implementation;

import com.tim.scientific.portal.back.CrmApi;
import com.tim.scientific.portal.back.service.CrmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class CrmControllerImp implements CrmApi {

    final private CrmService crmService;

    public CrmControllerImp(CrmService crmService) {
        this.crmService = crmService;
    }

    @Override
    public ResponseEntity getPageByPageTypeAndFilters(@Valid String pageType, @Valid String key,
                                                                      @Valid String value) {
        return new ResponseEntity(crmService.getDtoPageByPageTypeAndFilters(pageType,key,value), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getPageByPageId(@Valid UUID pageId) {
        return new ResponseEntity(crmService.getDtoPageById(pageId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getPageByPageType(@Valid String pageType) {
        return new ResponseEntity(crmService.getDtoPageByPage(pageType), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getModulesObjectByModuleId(@Valid UUID moduleId) {
        return new ResponseEntity(crmService.getDtoModulesObjects(moduleId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getContentByModulesObjectId(@Valid UUID objectId) {
        return new ResponseEntity(crmService.getContents(objectId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getAllPage() {
        return new ResponseEntity(crmService.getDtoPages(), HttpStatus.OK);
    }


    @Override
    public ResponseEntity getContentTypes() {
        return new ResponseEntity(crmService.getContentTypes(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getModuleTypes() {
        return new ResponseEntity(crmService.getModuleTypes(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getObjectTypes() {
        return new ResponseEntity(crmService.getObjectTypes(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getPageTypes() {
        return new ResponseEntity(crmService.getPageTypes(), HttpStatus.OK);
    }
}