package com.hl.notify.event;


public interface GlobalEventListener<T extends Event> {
    void onChange(T data);
}
