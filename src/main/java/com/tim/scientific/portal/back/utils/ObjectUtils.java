package com.tim.scientific.portal.back.utils;

import com.tim.scientific.portal.back.controllers.common.handlers.exception.ApiException;
import com.tim.scientific.portal.back.controllers.common.handlers.exception.NoSuchObjects;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class ObjectUtils {
    public Predicate<List<?>> isEmptyList() {
        return List::isEmpty;
    }

    public Predicate<Object> isEmpty() {
        return Objects::isNull;
    }

    public Predicate<List<?>> isNotEmptyList() {
        return objects -> !objects.isEmpty();
    }

    public Predicate<Object> isNotEmpty() {
        return Objects::nonNull;
    }

    @FunctionalInterface
    public interface CheckedErrorFunction<T, R> {
        R apply(T t) throws Exception;
    }

    @FunctionalInterface
    public interface CheckedErrorSupplier<R> {
        R get() throws Exception;
    }

    @FunctionalInterface
    public interface CheckedErrorConsumer<T> {
        void accept(T t) throws Exception;
    }

    protected <T> void objectAssert(RuntimeException apiException, T actual, T expected) {
        if (!actual.equals(expected)) {
            throw apiException;
        }
    }

    protected <T> void objectAssert(RuntimeException apiException, Predicate<T> predicate, T t) {
        if (!predicate.test(t)) {
            throw apiException;
        }
    }

    protected <T, R> void objectAssert(RuntimeException apiException, BiPredicate<T, R> predicate, T actual, R expected) {
        if (!predicate.test(actual, expected)) {
            throw apiException;
        }
    }

    public void listAssert(List<?> list, Predicate<List<?>> predicate) {
        if (!predicate.test(list)) {
            throw new NoSuchObjects();
        }
    }

    public void listAssert(List<?> list, Predicate<List<?>> predicate, ApiException e) {
        if (!predicate.test(list)) {
            throw e;
        }
    }

    protected <R> R applyByException(CheckedErrorSupplier<R> function,
                                     Map<? extends Throwable, ? extends ApiException> exceptionFields) {
        R applyResult = null;
        try {
            applyResult = function.get();
        } catch (Throwable e) {
            throwCustomException(e, exceptionFields);
        }
        return applyResult;
    }

    protected <T, R> R applyByException(T t, CheckedErrorFunction<T, R> function,
                                        Map<? extends Throwable, ? extends ApiException> exceptionFields) {
        R applyResult = null;
        try {
            applyResult = function.apply(t);
        } catch (Throwable e) {
            throwCustomException(e, exceptionFields);
        }
        return applyResult;
    }

    protected <T> void applyByException(T t, CheckedErrorConsumer<T> consumer,
                                        Map<? extends Throwable, ? extends ApiException> exceptionFields) {
        try {
            consumer.accept(t);
        } catch (Throwable e) {
            throwCustomException(e, exceptionFields);
        }
    }

    private <T extends Throwable> void throwCustomException(
            T exception, Map<? extends Throwable, ? extends ApiException> exceptionFields) {
        try {
            Class<? extends Throwable> exceptionClass = exception.getClass();
            for (Map.Entry<? extends Throwable, ? extends ApiException> entry : exceptionFields.entrySet()) {
                if (entry.getKey().getClass() == exceptionClass) {
                    exception.printStackTrace();
                    throw entry.getValue();
                }
            }
            throw exception;
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ApiException(t.getMessage(), t.getMessage());
        }
    }
}