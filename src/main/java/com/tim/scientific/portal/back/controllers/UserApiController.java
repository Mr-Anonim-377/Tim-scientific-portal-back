package com.tim.scientific.portal.back.controllers;


import com.tim.scientific.portal.back.UserApi;
import com.tim.scientific.portal.back.controllers.common.handlers.exception.ApiException;
import com.tim.scientific.portal.back.controllers.common.handlers.exception.WrongRoleError;
import com.tim.scientific.portal.back.db.models.security.User;
import com.tim.scientific.portal.back.dto.ContentValue;
import com.tim.scientific.portal.back.dto.Page;
import com.tim.scientific.portal.back.service.UserService;
import com.tim.scientific.portal.back.utils.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@RestController
@Validated
@Api(value = "user", description = "the session API")
public class UserApiController extends AbstractController implements UserApi {

    private final UserService userService;

    //TODO - сделать через swagger

    @Override
    public ResponseEntity<Page> createByComand(@Valid List<ContentValue> contentValue) {
        userService.
    }

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/user/registration", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity addUser(@RequestBody User newUser) {
        User createdUser = userService.saveUser(newUser, new HashSet<>(newUser.getRoles()));
        objectAssert(new ApiException("User saving db Error",
                "CREATING_USER_ERROR"), createdUser, isNotEmpty());
        return new ResponseEntity(createdUser, HttpStatus.OK);
    }

    @ApiOperation(value = "auth validation", nickname = "isAuth", notes = "Метод проверки аутентификации " +
            "(с какой ролью утентифицирован пользоаватель)", tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Error", response = AuthenticationException.class)})
    @RequestMapping(value = "/user/auth", method = RequestMethod.GET)
    public ResponseEntity auth(
            HttpServletResponse servletResponse, @RequestParam String roleName, HttpServletRequest httpServletRequest) throws IOException, ServletException {
        httpServletRequest.authenticate(servletResponse);
        if (httpServletRequest.isUserInRole(roleName)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        throw new WrongRoleError();
    }

    @ApiOperation(value = "auth method", nickname = "logIn", notes = "Метод аутентификации", tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Error", response = AuthenticationException.class)})
    @RequestMapping(value = "/user/logIn", method = RequestMethod.GET)
    public ResponseEntity logIn() {
        return new ResponseEntity(HttpStatus.OK);
    }


//    @PostMapping(value = "/utils/update/password", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity passwordUpdate(@RequestBody PasswordChange passwordChange) {
//        userService.passwordUpdate(passwordChange);
//        return new ResponseEntity(HttpStatus.OK);
//    }

    @GetMapping(value = "/utils/update/password", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity passwordUpdate(@RequestParam String login) {
        userService.passwordUpdate(login);
        return new ResponseEntity(HttpStatus.OK);
    }

//    @GetMapping(value = "/utils/update/request", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity createUpdateRequest(@RequestParam String login) {
//        userService.createUpdateRequest(login);
//        return new ResponseEntity(HttpStatus.OK);
//    }


}