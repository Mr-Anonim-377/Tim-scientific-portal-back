package com.tim.scientific.portal.back.controllers.common.handlers;

import com.tim.scientific.portal.back.controllers.common.handlers.exception.*;
import com.tim.scientific.portal.back.dto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.tim.scientific.portal.back.controllers.common.handlers.exception.ApiException.createApiError;

@ControllerAdvice
@Slf4j
public class AwesomeExceptionHandlers extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity handleApiException(ApiException e) {
        return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchObject.class)
    protected ResponseEntity handleNoSuchObject(ApiException e) {
        ApiError apiError = e.getApiError();
        return new ResponseEntity<>(apiError == null
                ? createApiError("Объект не найдер в системе", "NOT_FOUND")
                : createApiError(apiError.getErrorTitle(), apiError.getErrorType()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity handleUsernameNotFoundException(Exception e) {
        return new ResponseEntity<>(createApiError(e.getMessage(), "USER_NOT_FOUND"),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoSuchObjects.class)
    protected ResponseEntity handleNoSuchObjects(ApiException e) {
        ApiError apiError = e.getApiError();
        return new ResponseEntity<>(apiError == null
                ? createApiError("Объекты не найдены в системе", "NOT_FOUND")
                : createApiError(apiError.getErrorTitle(), apiError.getErrorType()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity handleNullPointerException(ApiException e) {
        ApiError apiError = e.getApiError();
        return new ResponseEntity<>(apiError == null
                ? createApiError("Ошиюка выполнения - попытка получить значение из null", "NPE")
                : createApiError(apiError.getErrorTitle(), apiError.getErrorType()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    protected ResponseEntity handleHttpServerErrorException() {
        throw new InternalServerException();
    }

    @ExceptionHandler(InternalServerException.class)
    protected ResponseEntity handleInternalServerException(ApiException e) {
        ApiError apiError = e.getApiError();
        return new ResponseEntity<>(apiError == null
                ? createApiError("Сервер временно недоступен", "INTERNAL_SERVER_ERROR")
                : createApiError(apiError.getErrorTitle(), apiError.getErrorType()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongRoleError.class)
    protected ResponseEntity handleBadParamForRequest(ApiException e) {
        ApiError apiError = e.getApiError();
        return new ResponseEntity<>(apiError == null
                ? createApiError("Не верная роль пользователя", "WRONG_ROLE")
                : createApiError(apiError.getErrorTitle(), apiError.getErrorType()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UploadFileEmptyError.class)
    protected ResponseEntity uploadFileEmptyErrorError(ApiException e) {
        ApiError apiError = e.getApiError();
        return new ResponseEntity<>(apiError == null
                ? createApiError("Загружфемый файл пуст", "UPLOAD_FILE_IS_EMPTY")
                : createApiError(apiError.getErrorTitle(), apiError.getErrorType()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UploadFileSystemError.class)
    protected ResponseEntity uploadFileSystemError(ApiException e) {
        ApiError apiError = e.getApiError();
        return new ResponseEntity<>(apiError == null
                ? createApiError("File don't save. System error", "I/O ERROR")
                : createApiError(apiError.getErrorTitle(), apiError.getErrorType()), HttpStatus.BAD_REQUEST);
    }

}