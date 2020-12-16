package com.tim.scientific.portal.back.controllers.implementation;

import com.tim.scientific.portal.back.UtilsApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class CrmSystemMethodsControllerImp implements UtilsApi {

    @Override
    public ResponseEntity getPing() {
        return new ResponseEntity("pong", HttpStatus.OK);
    }
}