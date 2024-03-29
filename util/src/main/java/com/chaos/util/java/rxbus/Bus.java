package com.chaos.util.java.rxbus;

import com.chaos.util.java.rxbus.annotation.Subscribe;
import com.chaos.util.java.rxbus.annotation.Tag;
import com.chaos.util.java.rxbus.entity.DeadEvent;
import com.chaos.util.java.rxbus.entity.EventType;
import com.chaos.util.java.rxbus.entity.ProducerBaseEvent;
import com.chaos.util.java.rxbus.entity.SubscriberBaseEvent;
import com.chaos.util.java.rxbus.finder.Finder;
import com.chaos.util.java.rxbus.thread.ThreadEnforcer;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import rx.functions.Action1;

/**
 * @decs: Bus
 * Dispatches events to listeners, and provides ways for listeners to register themselves.
 * <p/>
 * <p>The Bus allows publish-subscribe-style communication between components without requiring the components to
 * explicitly register with one another (and thus be aware of each other).  It is designed exclusively to replace
 * traditional Android in-process event distribution using explicit registration or listeners. It is <em>not</em> a
 * general-purpose publish-subscribe system, nor is it intended for interprocess communication.
 * <p/>
 * <h2>Receiving Events</h2>
 * To receive events, an object should:
 * <ol>
 * <li>Expose a public method, known as the <i>event subscriber</i>, which accepts a single argument of the type of event
 * desired;</li>
 * <li>Mark it with a {@link Subscribe} annotation;</li>
 * <li>Pass itself to an Bus instance's {@link #register(Object)} method.
 * </li>
 * </ol>
 * <p/>
 * <h2>Posting Events</h2>
 * To post an event, simply provide the event object to the {@link #post(Object)} or {@link #post(String, Object)} method.
 * The Bus instance will determine the type of event and route it to all registered listeners.
 * <p/>
 * <p>Events are routed based on their type &mdash; and tag an event will be delivered to any subscriber for any type to which the
 * event is <em>assignable.</em>  This includes implemented interfaces, all superclasses, and all interfaces implemented
 * by superclasses.
 * <p/>
 * <p>When {@code post} is called, all registered subscribers for an event are run in sequence, so subscribers should be
 * reasonably quick.  If an event may trigger an extended process (such as a database load), spawn a thread or queue it
 * for later.
 * <p/>
 * <h2>Subscriber Methods</h2>
 * Event Subscriber methods must accept only one argument: the event.
 * <p/>
 * <p>The Bus by default enforces that all interactions occur on the main thread.  You can provide an alternate
 * enforcement by passing a {@link ThreadEnforcer} to the constructor.
 * <p/>
 * <h2>Producer Methods</h2>
 * Producer methods should accept no arguments and return their event type. When a subscriber is registered for a type
 * that a producer is also already registered for, the subscriber will be called with the return value from the
 * producer.
 * <p/>
 * <h2>Dead Events</h2>
 * If an event is posted, but no registered subscribers can accept it, it is considered "dead."  To give the system a
 * second chance to handle dead events, they are wrapped in an instance of {@link DeadEvent} and reposted.
 * <p/>
 * <p>This class is safe for concurrent use.
 * @author: 郑少鹏
 * @date: 2019/8/28 11:20
 */
public class Bus {
    private static final String DEFAULT_IDENTIFIER = "default";
    /**
     * All registered event subscribers, indexed by event type.
     */
    private final ConcurrentMap<EventType, Set<SubscriberBaseEvent>> subscribersByType = new ConcurrentHashMap<>();
    /**
     * All registered event producers, index by event type.
     */
    private final ConcurrentMap<EventType, ProducerBaseEvent> producersByType = new ConcurrentHashMap<>();
    /**
     * Identifier used to differentiate the event bus instance.
     */
    private final String identifier;
    /**
     * Thread enforcer for register, unregister, and posting events.
     */
    private final ThreadEnforcer enforcer;
    /**
     * Used to find subscriber methods in register and unregister.
     */
    private final Finder finder;
    private final ConcurrentMap<Class<?>, Set<Class<?>>> flattenHierarchyCache = new ConcurrentHashMap<>();

    /**
     * Creates a new Bus named "default" that enforces actions on the main thread.
     */
    public Bus() {
        this(DEFAULT_IDENTIFIER);
    }

    /**
     * Creates a new Bus with the given {@code identifier} that enforces actions on the main thread.
     *
     * @param identifier A brief name for this bus, for debugging purposes.
     *                   Should be a valid Java identifier.
     */
    private Bus(String identifier) {
        this(ThreadEnforcer.MAIN, identifier);
    }

    /**
     * Creates a new Bus named "default" with the given {@code enforcer} for actions.
     *
     * @param enforcer Thread enforcer for register, unregister, and post actions.
     */
    Bus(ThreadEnforcer enforcer) {
        this(enforcer, DEFAULT_IDENTIFIER);
    }

    /**
     * Creates a new Bus with the given {@code enforcer} for actions and the given {@code identifier}.
     *
     * @param enforcer   Thread enforcer for register, unregister, and post actions.
     * @param identifier A brief name for this bus, for debugging purposes.
     *                   Should be a valid Java identifier.
     */
    private Bus(ThreadEnforcer enforcer, String identifier) {
        this(enforcer, identifier, Finder.ANNOTATED);
    }

    /**
     * Test constructor which allows replacing the default {@code Finder}.
     *
     * @param enforcer   Thread enforcer for register, unregister, and post actions.
     * @param identifier A brief name for this bus, for debugging purposes.
     *                   Should be a valid Java identifier.
     * @param finder     Used to discover event subscribers and producers when registering/unregistering an object.
     */
    private Bus(ThreadEnforcer enforcer, String identifier, Finder finder) {
        this.enforcer = enforcer;
        this.identifier = identifier;
        this.finder = finder;
    }

    @NotNull
    @Override
    public String toString() {
        return "[Bus \"" + identifier + "\"]";
    }

    /**
     * Registers all subscriber methods on {@code object} to receive events and producer methods to provide events.
     * <p/>
     * If any subscribers are registering for types which already have a producer they will be called immediately with the result of calling that producer.
     * <p/>
     * If any producers are registering for types which already have subscribers, each subscriber will be called with the value from the result of calling the producer.
     *
     * @param object Object whose subscriber methods should be registered.
     * @throws NullPointerException If the object is null.
     */
    public void register(Object object) {
        if (null == object) {
            throw new NullPointerException("Object to register must not be null.");
        }
        enforcer.enforce(this);
        Map<EventType, ProducerBaseEvent> foundProducers = finder.findAllProducers(object);
        for (EventType type : foundProducers.keySet()) {
            final ProducerBaseEvent producer = foundProducers.get(type);
            ProducerBaseEvent previousProducer = producersByType.putIfAbsent(type, producer);
            // checking if the previous producer existed
            if (null != previousProducer) {
                if (null != producer) {
                    throw new IllegalArgumentException("Producer method for type " + type
                            + " found on type " + producer.getTarget().getClass()
                            + ", but already registered by type " + previousProducer.getTarget().getClass() + ".");
                }
            }
            Set<SubscriberBaseEvent> subscribers = subscribersByType.get(type);
            if ((null != subscribers) && !subscribers.isEmpty()) {
                for (SubscriberBaseEvent subscriber : subscribers) {
                    if (null != producer) {
                        dispatchProducerResult(subscriber, producer);
                    }
                }
            }
        }
        Map<EventType, Set<SubscriberBaseEvent>> foundSubscribersMap = finder.findAllSubscribers(object);
        for (EventType eventType : foundSubscribersMap.keySet()) {
            Set<SubscriberBaseEvent> subscribers = subscribersByType.get(eventType);
            if (null == subscribers) {
                // concurrent put if absent
                Set<SubscriberBaseEvent> subscribersCreation = new CopyOnWriteArraySet<>();
                subscribers = subscribersByType.putIfAbsent(eventType, subscribersCreation);
                if (null == subscribers) {
                    subscribers = subscribersCreation;
                }
            }
            final Set<SubscriberBaseEvent> foundSubscribers = foundSubscribersMap.get(eventType);
            if ((null != foundSubscribers) && !subscribers.addAll(foundSubscribers)) {
                throw new IllegalArgumentException("Object already registered.");
            }
        }
        for (Map.Entry<EventType, Set<SubscriberBaseEvent>> entry : foundSubscribersMap.entrySet()) {
            EventType eventType = entry.getKey();
            ProducerBaseEvent producer = producersByType.get(eventType);
            if ((null != producer) && producer.isValid()) {
                Set<SubscriberBaseEvent> subscriberEvents = entry.getValue();
                for (SubscriberBaseEvent subscriberEvent : subscriberEvents) {
                    if (!producer.isValid()) {
                        break;
                    }
                    if (subscriberEvent.isValid()) {
                        dispatchProducerResult(subscriberEvent, producer);
                    }
                }
            }
        }
    }

    private void dispatchProducerResult(final SubscriberBaseEvent subscriberEvent, @NotNull ProducerBaseEvent producer) {
        producer.produce().subscribe((Action1<Object>) event -> {
            if (null != event) {
                dispatch(event, subscriberEvent);
            }
        });
    }

    /**
     * Whether all the subscriber methods on {@code object} to receive events and producer methods to provide events has registered.
     * <p/>
     * If any subscribers and producers has registered, it will return true, else false.
     *
     * @param object Object whose subscriber methods should be registered.
     * @throws NullPointerException If the object is null.
     */
    @Deprecated
    public boolean hasRegistered(Object object) {
        if (null == object) {
            throw new NullPointerException("Object to register must not be null.");
        }
        boolean hasProducerRegistered = false, hasSubscriberRegistered = false;
        Map<EventType, ProducerBaseEvent> foundProducers = finder.findAllProducers(object);
        for (EventType eventType : foundProducers.keySet()) {
            final ProducerBaseEvent producer = foundProducers.get(eventType);
            hasProducerRegistered = producersByType.containsValue(producer);
            if (hasProducerRegistered) {
                break;
            }
        }
        if (!hasProducerRegistered) {
            Map<EventType, Set<SubscriberBaseEvent>> foundSubscribersMap = finder.findAllSubscribers(object);
            for (EventType type : foundSubscribersMap.keySet()) {
                Set<SubscriberBaseEvent> subscribers = subscribersByType.get(type);
                if ((null != subscribers) && (subscribers.size() > 0)) {
                    final Set<SubscriberBaseEvent> foundSubscribers = foundSubscribersMap.get(type);
                    // check the first subscriber...
                    SubscriberBaseEvent foundSubscriber = null;
                    if (null != foundSubscribers) {
                        foundSubscriber = !foundSubscribers.isEmpty() ? foundSubscribers.iterator().next() : null;
                    }
                    hasSubscriberRegistered = subscribers.contains(foundSubscriber);
                    if (hasSubscriberRegistered) {
                        break;
                    }
                }
            }
        }
        return (hasProducerRegistered || hasSubscriberRegistered);
    }

    /**
     * Unregisters all producer and subscriber methods on a registered {@code object}.
     *
     * @param object Object whose producer and subscriber methods should be unregistered.
     * @throws IllegalArgumentException If the object was not previously registered.
     * @throws NullPointerException     If the object is null.
     */
    public void unregister(Object object) {
        if (null == object) {
            throw new NullPointerException("Object to unregister must not be null.");
        }
        enforcer.enforce(this);
        Map<EventType, ProducerBaseEvent> producersInListener = finder.findAllProducers(object);
        for (Map.Entry<EventType, ProducerBaseEvent> entry : producersInListener.entrySet()) {
            final EventType key = entry.getKey();
            ProducerBaseEvent producer = getProducerForEventType(key);
            ProducerBaseEvent value = entry.getValue();
            if ((null == value) || !value.equals(producer)) {
                throw new IllegalArgumentException("Missing event producer for an annotated method. Is " + object.getClass() + " registered?");
            }
            Objects.requireNonNull(producersByType.remove(key), "key must not be null").invalidate();
        }
        Map<EventType, Set<SubscriberBaseEvent>> subscribersInListener = finder.findAllSubscribers(object);
        for (Map.Entry<EventType, Set<SubscriberBaseEvent>> entry : subscribersInListener.entrySet()) {
            Set<SubscriberBaseEvent> currentSubscribers = getSubscribersForEventType(entry.getKey());
            Collection<SubscriberBaseEvent> eventMethodsInListener = entry.getValue();
            if ((null == currentSubscribers) || !currentSubscribers.containsAll(eventMethodsInListener)) {
                throw new IllegalArgumentException("Missing event subscriber for an annotated method. Is " + object.getClass() + " registered?");
            }
            for (SubscriberBaseEvent subscriber : currentSubscribers) {
                if (eventMethodsInListener.contains(subscriber)) {
                    subscriber.invalidate();
                }
            }
            currentSubscribers.removeAll(eventMethodsInListener);
        }
    }

    /**
     * Posts an event to all registered subscribers.
     * This method will return successfully after the event has been posted to all subscribers, and regardless of any exceptions thrown by subscribers.
     * <p/>
     * If no subscribers have been subscribed for {@code event}'s class, and {@code event} is not already a {@link DeadEvent}, it will be wrapped in a DeadEvent and reposted.
     *
     * @param event Event to post.
     * @throws NullPointerException If the event is null.
     */
    private void post(Object event) {
        post(Tag.DEFAULT, event);
    }

    /**
     * Posts an event to all registered subscribers.
     * This method will return successfully after the event has been posted to all subscribers, and regardless of any exceptions thrown by subscribers.
     * <p/>
     * If no subscribers have been subscribed for {@code event}'s class, and {@code event} is not already a{@link DeadEvent}, it will be wrapped in a DeadEvent and reposted.
     *
     * @param tag   Event tag to post.
     * @param event Event to post.
     * @throws NullPointerException If the event is null.
     */
    public void post(String tag, Object event) {
        if (null == event) {
            throw new NullPointerException("Event to post must not be null.");
        }
        enforcer.enforce(this);
        Set<Class<?>> dispatchClasses = flattenHierarchy(event.getClass());
        boolean dispatched = false;
        for (Class<?> clazz : dispatchClasses) {
            Set<SubscriberBaseEvent> wrappers = getSubscribersForEventType(new EventType(tag, clazz));
            if ((null != wrappers) && !wrappers.isEmpty()) {
                dispatched = true;
                for (SubscriberBaseEvent wrapper : wrappers) {
                    dispatch(event, wrapper);
                }
            }
        }
        if (!dispatched && !(event instanceof DeadEvent)) {
            post(new DeadEvent(this, event));
        }
    }

    /**
     * Dispatches {@code event} to the subscriber in {@code wrapper}.
     * This method is an appropriate override point for subclasses that wish to make event delivery asynchronous.
     *
     * @param event   Event to dispatch.
     * @param wrapper Wrapper that will call the handle.
     */
    private void dispatch(Object event, @NotNull SubscriberBaseEvent wrapper) {
        if (wrapper.isValid()) {
            wrapper.handle(event);
        }
    }

    /**
     * Retrieves the currently registered producer for {@code type}.
     * If no producer is currently registered for {@code type}, this method will return {@code null}.
     *
     * @param type Type of producer to retrieve.
     * @return Currently registered producer, or {@code null}.
     */
    private ProducerBaseEvent getProducerForEventType(EventType type) {
        return producersByType.get(type);
    }

    /**
     * Retrieves a mutable set of the currently registered subscribers for {@code type}.
     * If no subscribers are currently registered for {@code type}, this method may either return {@code null} or an empty set.
     *
     * @param type Type of subscribers to retrieve.
     * @return Currently registered subscribers, or {@code null}.
     */
    private Set<SubscriberBaseEvent> getSubscribersForEventType(EventType type) {
        return subscribersByType.get(type);
    }

    /**
     * Flattens a class's type hierarchy into a set of Class objects.
     * The set will include all superclasses (transitively), and all interfaces implemented by these superclasses.
     *
     * @param concreteClass Class whose type hierarchy will be retrieved.
     * @return {@code concreteClass}'s complete type hierarchy, flattened and uniqued.
     */
    private @NotNull Set<Class<?>> flattenHierarchy(Class<?> concreteClass) {
        Set<Class<?>> classes = flattenHierarchyCache.get(concreteClass);
        if (null == classes) {
            Set<Class<?>> classesCreation = getClassesFor(concreteClass);
            classes = flattenHierarchyCache.putIfAbsent(concreteClass, classesCreation);
            if (null == classes) {
                classes = classesCreation;
            }
        }
        return classes;
    }

    private @NotNull Set<Class<?>> getClassesFor(Class<?> concreteClass) {
        List<Class<?>> parents = new LinkedList<>();
        Set<Class<?>> classes = new HashSet<>();
        parents.add(concreteClass);
        while (!parents.isEmpty()) {
            Class<?> clazz = parents.remove(0);
            classes.add(clazz);
            Class<?> parent = clazz.getSuperclass();
            if (null != parent) {
                parents.add(parent);
            }
        }
        return classes;
    }
}
