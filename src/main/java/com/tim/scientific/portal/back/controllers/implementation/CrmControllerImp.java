package com.tim.scientific.portal.back.controllers.implementation;

import com.tim.scientific.portal.back.CrmApi;
import com.tim.scientific.portal.back.dto.PageTypeEnum;
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
    public ResponseEntity getPageByPageId(@Valid UUID pageId) {
        return new ResponseEntity(crmService.getDtoPageById(pageId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getModulesByPageType(@Valid PageTypeEnum pageType) {
        return new ResponseEntity(crmService.getDtoModules(pageType), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getModulesObjectByModuleId(@Valid UUID moduleId) {
        return new ResponseEntity(crmService.getDtoModulesObjects(moduleId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getContentByModulesObjectId(@Valid UUID objectId) {
        return new ResponseEntity(crmService.getContents(objectId), HttpStatus.OK);
    }

}