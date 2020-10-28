package com.tim.scientific.portal.back.controllers.common.handlers;

import com.tim.scientific.portal.back.controllers.common.handlers.exception.ApiException;
import com.tim.scientific.portal.back.controllers.common.handlers.exception.InternalServerException;
import com.tim.scientific.portal.back.controllers.common.handlers.exception.NoSuchObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.tim.scientific.portal.back.controllers.common.handlers.exception.ApiException.createApiError;

@ControllerAdvice
@Slf4j
public class AwesomeExceptionHandlers extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity handleApiException(ApiException e) {
        return new ResponseEntity<>(e.getApiError(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchObject.class)
    protected ResponseEntity handleNoSuchObject() {
        return new ResponseEntity<>(createApiError("NoSuchObject", "NOT_FOUND"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity handleNullPointerException() {
        return new ResponseEntity<>(createApiError("NullPointerException", "NOT_FOUND"), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(NoSuchObjects.class)
//    protected ResponseEntity handleNoSuchObjects() {
//        return new ResponseEntity<>(, HttpStatus.NOT_FOUND);
//    }

//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    protected ResponseEntity handleNoHttpValidationParametrs() {
//        return new ResponseEntity<>(, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(InternalServerException.class)
    protected ResponseEntity handleIntermalServerError(Exception exc) {
        return new ResponseEntity<>(createApiError("InternalServerError", "INTERNAL_SERVER_ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(BadParamForRequest.class)
//    protected ResponseEntity handleBadParamForReqest(Exception exc) {
//        return new ResponseEntity<>(, HttpStatus.BAD_REQUEST);
//    }

}
