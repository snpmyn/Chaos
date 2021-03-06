package com.chaos.util.java.rxbus.entity;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * @decs: BaseEvent
 * @author: 郑少鹏
 * @date: 2019/8/28 11:00
 */
abstract class BaseEvent {
    /**
     * Throw a {@link RuntimeException} with given message and cause lifted from an {@link InvocationTargetException}.
     * If the specified {@link InvocationTargetException} does not have a cause, neither will the {@link RuntimeException}.
     *
     * @param msg String
     * @param e   InvocationTargetException
     */
    void throwRuntimeException(String msg, @NotNull InvocationTargetException e) {
        throwRuntimeException(msg, Objects.requireNonNull(e.getCause(), "e.getCause() must not be null"));
    }

    /**
     * Throw a {@link RuntimeException} with given message and cause lifted from an {@link InvocationTargetException}.
     * If the specified {@link InvocationTargetException} does not have a cause, neither will the {@link RuntimeException}.
     *
     * @param msg String
     * @param e   Throwable
     */
    private void throwRuntimeException(String msg, @NotNull Throwable e) {
        Throwable throwable = e.getCause();
        if (null != throwable) {
            throw new RuntimeException(msg + ": " + throwable.getMessage(), throwable);
        } else {
            throw new RuntimeException(msg + ": " + e.getMessage(), e);
        }
    }
}