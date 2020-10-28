package com.tim.scientific.portal.back.controllers.common.handlers.exception;

import com.tim.scientific.portal.back.dto.ApiError;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ApiException extends RuntimeException {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-LL-yyyy/HH:mm:ss");

    ApiError apiError;

    public ApiException() {
    }


    public ApiException(ApiError apiError) {
        this.apiError = apiError;
    }

    public ApiException(String errorTitle, String errorType) {
        this.apiError = createApiError(errorTitle,errorType);
    }

    public static ApiError createApiError(String errorTitle, String errorType) {
        ApiError apiError = new ApiError();
        apiError.setDate(LocalDateTime.now().format(formatter));
        apiError.setErrorTitle(errorTitle);
        apiError.setErrorType(errorType);
        return apiError;
    }
}
