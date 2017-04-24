package com.hl.notify.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


public class EventNotify {
    private static EventNotify instance;

    private static EventNotify getInstance() {
        if (instance == null) {
            synchronized (EventNotify.class) {
                if (instance == null) {
                    instance = new EventNotify();
                }
            }
        }
        return instance;
    }

    private EventNotify() {
    }

    Map<String, CopyOnWriteArraySet<GlobalEventListener>> mObserver = new HashMap<>();

    public static void listen(GlobalEventListener<? extends Event> listener) {
        getInstance();
        Type[] types = listener.getClass().getGenericInterfaces();
        ParameterizedType type = (ParameterizedType) types[0];
        String clsName = listener.getClass().getName();
        if (type.getActualTypeArguments()[0] instanceof Class) {
            clsName = ((Class) type.getActualTypeArguments()[0]).getName();
        }
        CopyOnWriteArraySet<GlobalEventListener> set = instance.mObserver.get(clsName);
        if (set == null) {
            set = new CopyOnWriteArraySet<>();
        }
        set.add(listener);
        instance.mObserver.put(clsName, set);
    }

    public static void unListen(GlobalEventListener listener) {
        getInstance();
        Type[] types = listener.getClass().getGenericInterfaces();
        ParameterizedType type = (ParameterizedType) types[0];
        String clsName = listener.getClass().getName();
        if (type.getActualTypeArguments()[0] instanceof Class) {
            clsName = ((Class) type.getActualTypeArguments()[0]).getName();
        }
        CopyOnWriteArraySet<GlobalEventListener> set = instance.mObserver.get(clsName);
        if (set != null) {
            set.remove(listener);
        }
        instance.mObserver.put(clsName, set);
    }

    public static void sendEvent(Event event) {
        getInstance();
        String clsName = event.getClass().getName();
        Set<GlobalEventListener> set = instance.mObserver.get(clsName);
        if (set != null) {
            for (GlobalEventListener listener : set) {
                listener.onChange(event);
            }
        }
    }

}
