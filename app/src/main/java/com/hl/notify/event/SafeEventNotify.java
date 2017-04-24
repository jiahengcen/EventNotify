package com.hl.notify.event;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


public class SafeEventNotify {
    private static SafeEventNotify instance;

    private static SafeEventNotify getInstance() {
        if (instance == null) {
            synchronized (SafeEventNotify.class) {
                if (instance == null) {
                    instance = new SafeEventNotify();
                }
            }
        }
        return instance;
    }

    private SafeEventNotify() {
    }

    Map<String, Set<WeakReference<GlobalEventListener<? extends Event>>>> mObserver = new HashMap<>();

    public static void listen(GlobalEventListener<? extends Event> listener) {
        getInstance();
        Type[] types = listener.getClass().getGenericInterfaces();
        ParameterizedType type = (ParameterizedType) types[0];
        String clsName = listener.getClass().getName();
        if (type.getActualTypeArguments()[0] instanceof Class) {
            clsName = ((Class) type.getActualTypeArguments()[0]).getName();
        }
        Set<WeakReference<GlobalEventListener<? extends Event>>> set = instance.mObserver.get(clsName);
        if (set == null) {
            set = new CopyOnWriteArraySet<>();
        }
        WeakReference<GlobalEventListener<? extends Event>> reference = new WeakReference<GlobalEventListener<? extends Event>>(listener);
        set.add(reference);
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
        Set<WeakReference<GlobalEventListener<? extends Event>>> set = instance.mObserver.get(clsName);
        if (set != null) {
            WeakReference<GlobalEventListener<?>> reference = null;
            Iterator<WeakReference<GlobalEventListener<? extends Event>>> iterator = set.iterator();
            while (iterator.hasNext()) {
                reference = iterator.next();
                if (reference.get() != null && reference.get().equals(listener)) {
                    break;
                }
                reference = null;
            }
            if (reference != null) {
                set.remove(reference);

            }
        }
        instance.mObserver.put(clsName, set);
    }

    public static void sendEvent(Event event) {
        getInstance();
        String clsName = event.getClass().getName();
        Set<WeakReference<GlobalEventListener<? extends Event>>> set = instance.mObserver.get(clsName);
        if (set != null) {
            for (WeakReference<GlobalEventListener<? extends Event>> reference : set) {
                GlobalEventListener listener = reference.get();
                if (listener != null) {
                    listener.onChange(event);
                }
            }
        }
    }

}
