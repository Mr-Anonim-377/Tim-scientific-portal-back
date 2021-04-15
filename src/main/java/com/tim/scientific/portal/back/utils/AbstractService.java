package com.tim.scientific.portal.back.utils;

import com.google.common.collect.ImmutableMap;
import com.tim.scientific.portal.back.controllers.common.handlers.exception.ApiException;
import com.tim.scientific.portal.back.controllers.common.handlers.exception.InternalServerException;
import com.tim.scientific.portal.back.controllers.common.handlers.exception.NoSuchObject;

import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractService extends ObjectUtils {

    protected <T, R> R applyHibernateQuery(T t, CheckedErrorFunction<T, R> function) {
        return applyByException(t, function,
                ImmutableMap.of(new RuntimeException(), new InternalServerException()));
    }

    protected <R> R applyHibernateQuery(CheckedErrorSupplier<R> function) {
        return applyByException(function,
                ImmutableMap.of(new RuntimeException(), new InternalServerException()));
    }

    protected <T> void applyHibernateQuery(T t, CheckedErrorConsumer<T> consumer) {
        applyByException(t, consumer, ImmutableMap.of(new RuntimeException(), new InternalServerException()));
    }

    protected <T, R extends List<?>> R applySqlFunctionAndListAssert(CheckedErrorFunction<T, R> function, T t,
                                                                     Predicate<List<?>> predicate) {
        R result = applyHibernateQuery(t, function);
        listAssert(result, predicate);
        return result;
    }

    protected <R extends List<?>> R applySqlFunctionAndListAssert(CheckedErrorSupplier<R> function,
                                                                  Predicate<List<?>> predicate) {
        R pagesByType = applyHibernateQuery(function);
        listAssert(pagesByType, predicate);
        return pagesByType;
    }

    protected <T, R> R applySqlFunctionAndAssert(CheckedErrorFunction<T, R> function, T t,
                                                 Predicate<Object> predicate) {
        return applyFunction(new NoSuchObject(),function, t, predicate);    }

    protected <T, R> R applySqlFunctionAndAssert(ApiException e,CheckedErrorFunction<T, R> function, T t,
                                                 Predicate<Object> predicate) {
        return applyFunction(e,function, t, predicate);
    }

    private <T, R> R applyFunction(ApiException e,CheckedErrorFunction<T, R> function, T t, Predicate<Object> predicate) {
        R result = applyHibernateQuery(t, function);
        objectAssert(e, predicate, result);
        return result;
    }


    protected <R> R applySqlFunctionAndAssert(CheckedErrorSupplier<R> function,
                                              Predicate<Object> predicate) {
        R result = applyHibernateQuery(function);
        objectAssert(new NoSuchObject(), predicate, result);
        return result;
    }
}