package com.tim.scientific.portal.back.controllers;

import com.google.gson.JsonObject;
import com.tim.scientific.portal.back.dto.ApiError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Validated
@Api(value = "session", description = "the session API")
public class SessionApiController {

    @ApiOperation(value = "validate and get session.", nickname = "sessionGet", notes = "метод валидации session, в случае отсутствия session - создание", tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Error", response = ApiError.class)})
    @RequestMapping(value = "/session", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity sessionGet(HttpServletRequest httpServletRequest) {
        JsonObject responseJsonValue = new JsonObject();
        if (httpServletRequest.getSession(false) != null) {
            responseJsonValue.addProperty("session", "already created");
        } else {
            responseJsonValue.addProperty("session", "is create");
        }
        httpServletRequest.getSession().setMaxInactiveInterval((60 * 60) * 7);
        return new ResponseEntity(responseJsonValue.toString(), HttpStatus.OK);
    }
}