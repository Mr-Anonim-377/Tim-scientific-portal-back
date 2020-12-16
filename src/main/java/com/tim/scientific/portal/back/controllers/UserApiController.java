//package com.tim.scientific.portal.back.controllers;
//
//import com.google.gson.JsonObject;
//import com.tim.scientific.portal.back.dto.ApiError;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//
//@RestController
//@Validated
//@Api(value = "user", description = "the session API")
//public class UserApiController {
//
//    @ApiOperation(value = "validate and get session.", nickname = "sessionGet", notes = "метод валидации session, в случае отсутствия session - создание", tags = {})
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "OK"),
//            @ApiResponse(code = 401, message = "Error", response = ApiError.class)})
//    @RequestMapping(value = "/login", produces = {"application/json"}, method = RequestMethod.GET)
//    public ResponseEntity sessionGet(HttpServletRequest httpServletRequest) {
//
//    }
//}