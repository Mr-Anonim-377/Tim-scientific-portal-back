package com.tim.scientific.portal.back.utils;

import com.tim.scientific.portal.back.controllers.common.handlers.exception.ApiException;
import com.tim.scientific.portal.back.controllers.common.handlers.exception.NoSuchObject;
import com.tim.scientific.portal.back.utils.ObjectUtils;

public abstract class AbstractController extends ObjectUtils {

    protected <T> void nullAssert(T t) {
        if (t == null) {
            throw new NoSuchObject();
        }
    }

    protected <T> void nullAssert(String message, T t) {
        try {
            nullAssert(t);
        } catch (NoSuchObject e) {
            throw new ApiException("NoSuchObject",message);
        }
    }

    protected <T> void nullAssert(ApiException apiException, T t) {
        if (t == null) {
            throw apiException;
        }
    }

}