package com.chaos.util.java.rxbus.annotation;

import com.chaos.util.java.rxbus.Bus;
import com.chaos.util.java.rxbus.finder.AnnotatedFinder;
import com.chaos.util.java.rxbus.thread.EventThread;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @decs: Subscribe
 * Marks a method as an event subscriber, as used by {@link AnnotatedFinder} and {@link Bus}.
 * <p>
 * The method's first (and only) parameter and tag defines the event type.
 * If this annotation is applied to methods with zero parameters or more than one parameter, the object containing the method will not be able to register for event delivery from the {@link Bus}.
 * Bus fails fast by throwing runtime exceptions in these cases.
 * @author: 郑少鹏
 * @date: 2019/8/28 10:57
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscribe {
    Tag[] tags() default {};

    EventThread thread() default EventThread.MAIN_THREAD;
}