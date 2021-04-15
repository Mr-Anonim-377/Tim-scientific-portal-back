package com.tim.scientific.portal.back.controllers.common.handlers.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UploadFileSystemError extends ApiException {

    public UploadFileSystemError(String errorTitle, String errorType) {
        super(errorTitle, errorType);
    }
}
