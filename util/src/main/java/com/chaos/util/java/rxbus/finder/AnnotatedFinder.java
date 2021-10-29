package com.chaos.util.java.rxbus.finder;

import androidx.annotation.Nullable;

import com.chaos.util.java.rxbus.annotation.Produce;
import com.chaos.util.java.rxbus.annotation.Subscribe;
import com.chaos.util.java.rxbus.annotation.Tag;
import com.chaos.util.java.rxbus.entity.EventType;
import com.chaos.util.java.rxbus.entity.ProducerBaseEvent;
import com.chaos.util.java.rxbus.entity.SubscriberBaseEvent;
import com.chaos.util.java.rxbus.thread.EventThread;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @decs: AnnotatedFinder
 * <p>
 * Helper methods for finding methods annotated with {@link Produce} and {@link Subscribe}.
 * @author: 郑少鹏
 * @date: 2019/8/28 11:08
 */
public final class AnnotatedFinder {
    /**
     * Cache event bus producer methods for each class.
     */
    private static final ConcurrentMap<Class<?>, Map<EventType, SourceMethod>> PRODUCERS_CACHE = new ConcurrentHashMap<>();
    /**
     * Cache event bus subscriber methods for each class.
     */
    private static final ConcurrentMap<Class<?>, Map<EventType, Set<SourceMethod>>> SUBSCRIBERS_CACHE = new ConcurrentHashMap<>();

    private AnnotatedFinder() {
        // No instances.
    }

    private static void loadAnnotatedProducerMethods(Class<?> listenerClass, Map<EventType, SourceMethod> producerMethods) {
        Map<EventType, Set<SourceMethod>> subscriberMethods = new HashMap<>(9);
        loadAnnotatedMethods(listenerClass, producerMethods, subscriberMethods);
    }

    private static void loadAnnotatedSubscriberMethods(Class<?> listenerClass, Map<EventType, Set<SourceMethod>> subscriberMethods) {
        Map<EventType, SourceMethod> producerMethods = new HashMap<>(9);
        loadAnnotatedMethods(listenerClass, producerMethods, subscriberMethods);
    }

    /**
     * Load all methods annotated with {@link Produce} or {@link Subscribe} into their respective caches for the specified class.
     *
     * @param listenerClass     Class<?>
     * @param producerMethods   Map<EventType, SourceMethod>
     * @param subscriberMethods Map<EventType, Set<SourceMethod>>
     */
    private static void loadAnnotatedMethods(@NotNull Class<?> listenerClass, Map<EventType, SourceMethod> producerMethods, Map<EventType, Set<SourceMethod>> subscriberMethods) {
        for (Method method : listenerClass.getDeclaredMethods()) {
            // The compiler sometimes creates synthetic bridge methods as part of the type erasure process.
            // As of JDK8 these methods now include the same annotations as the original declarations.
            // They should be ignored for subscribe/produce.
            if (method.isBridge()) {
                continue;
            }
            if (method.isAnnotationPresent(Subscribe.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation but requires " + parameterTypes.length + " arguments.  Methods must require a single argument.");
                }
                Class<?> parameterClazz = parameterTypes[0];
                if (parameterClazz.isInterface()) {
                    throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation on " + parameterClazz + " which is an interface.  Subscription must be on a concrete class type.");
                }
                if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
                    throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation on " + parameterClazz + " but is not 'public'.");
                }
                Subscribe annotation = method.getAnnotation(Subscribe.class);
                EventThread thread = null;
                if (null != annotation) {
                    thread = annotation.thread();
                }
                Tag[] tags = new Tag[0];
                if (null != annotation) {
                    tags = annotation.tags();
                }
                int tagLength = tags.length;
                do {
                    String tag = Tag.DEFAULT;
                    if (tagLength > 0) {
                        tag = tags[tagLength - 1].value();
                    }
                    EventType type = new EventType(tag, parameterClazz);
                    Set<SourceMethod> methods = subscriberMethods.get(type);
                    if (null == methods) {
                        methods = new HashSet<>();
                        subscriberMethods.put(type, methods);
                    }
                    methods.add(new SourceMethod(thread, method));
                    tagLength--;
                } while (tagLength > 0);
            } else if (method.isAnnotationPresent(Produce.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 0) {
                    throw new IllegalArgumentException("Method " + method + "has @Produce annotation but requires " + parameterTypes.length + " arguments.  Methods must require zero arguments.");
                }
                if (method.getReturnType() == Void.class) {
                    throw new IllegalArgumentException("Method " + method + " has a return type of void.  Must declare a non-void type.");
                }
                Class<?> parameterClazz = method.getReturnType();
                if (parameterClazz.isInterface()) {
                    throw new IllegalArgumentException("Method " + method + " has @Produce annotation on " + parameterClazz + " which is an interface.  Producers must return a concrete class type.");
                }
                if (parameterClazz.equals(Void.TYPE)) {
                    throw new IllegalArgumentException("Method " + method + " has @Produce annotation but has no return type.");
                }
                if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
                    throw new IllegalArgumentException("Method " + method + " has @Produce annotation on " + parameterClazz + " but is not 'public'.");
                }
                Produce annotation = method.getAnnotation(Produce.class);
                EventThread eventThread = null;
                if (null != annotation) {
                    eventThread = annotation.thread();
                }
                Tag[] tags = new Tag[0];
                if (null != annotation) {
                    tags = annotation.tags();
                }
                int tagLength = tags.length;
                do {
                    String tag = Tag.DEFAULT;
                    if (tagLength > 0) {
                        tag = tags[tagLength - 1].value();
                    }
                    EventType type = new EventType(tag, parameterClazz);
                    if (producerMethods.containsKey(type)) {
                        throw new IllegalArgumentException("Producer for type " + type + " has already been registered.");
                    }
                    producerMethods.put(type, new SourceMethod(eventThread, method));
                    tagLength--;
                } while (tagLength > 0);
            }
        }
        PRODUCERS_CACHE.put(listenerClass, producerMethods);
        SUBSCRIBERS_CACHE.put(listenerClass, subscriberMethods);
    }

    /**
     * This implementation finds all methods marked with a {@link Produce} annotation.
     *
     * @param listener Object
     * @return Map<EventType, ProducerBaseEvent>
     */
    static @NotNull Map<EventType, ProducerBaseEvent> findAllProducers(@NotNull Object listener) {
        final Class<?> listenerClass = listener.getClass();
        Map<EventType, ProducerBaseEvent> producersInMethod = new HashMap<>(9);
        Map<EventType, SourceMethod> methods = PRODUCERS_CACHE.get(listenerClass);
        if (null == methods) {
            methods = new HashMap<>(9);
            loadAnnotatedProducerMethods(listenerClass, methods);
        }
        if (!methods.isEmpty()) {
            for (Map.Entry<EventType, SourceMethod> e : methods.entrySet()) {
                ProducerBaseEvent producer = new ProducerBaseEvent(listener, e.getValue().method, e.getValue().thread);
                producersInMethod.put(e.getKey(), producer);
            }
        }
        return producersInMethod;
    }

    /**
     * This implementation finds all methods marked with a {@link Subscribe} annotation.
     *
     * @param listener Object
     * @return Map<EventType, Set < SubscriberBaseEvent>>
     */
    static @NotNull Map<EventType, Set<SubscriberBaseEvent>> findAllSubscribers(@NotNull Object listener) {
        Class<?> listenerClass = listener.getClass();
        Map<EventType, Set<SubscriberBaseEvent>> subscribersInMethod = new HashMap<>(9);
        Map<EventType, Set<SourceMethod>> methods = SUBSCRIBERS_CACHE.get(listenerClass);
        if (null == methods) {
            methods = new HashMap<>(9);
            loadAnnotatedSubscriberMethods(listenerClass, methods);
        }
        if (!methods.isEmpty()) {
            for (Map.Entry<EventType, Set<SourceMethod>> entry : methods.entrySet()) {
                Set<SubscriberBaseEvent> subscribers = new HashSet<>();
                for (SourceMethod sourceMethod : entry.getValue()) {
                    subscribers.add(new SubscriberBaseEvent(listener, sourceMethod.method, sourceMethod.thread));
                }
                subscribersInMethod.put(entry.getKey(), subscribers);
            }
        }
        return subscribersInMethod;
    }

    private static class SourceMethod {
        private final EventThread thread;
        private final Method method;

        private SourceMethod(EventThread thread, Method method) {
            this.thread = thread;
            this.method = method;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            return super.equals(obj);
        }
    }
}
