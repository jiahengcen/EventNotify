package com.hl.notify.event;


public abstract class Event<T> {
    private T mData;
    public T getData(){
        return mData;
    }
    public Event(T data){
        mData=data;
        updateEvent();
    }
    public  void updateEvent(){};
}
