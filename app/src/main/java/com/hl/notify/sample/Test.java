package com.hl.notify.sample;

import com.hl.notify.event.EventNotify;
import com.hl.notify.event.GlobalEventListener;
import com.hl.notify.event.SafeEventNotify;


public class Test {
    public static void main(String[] arg) {
        test();
        testSafe();

    }

    private static void test() {
        GlobalEventListener<TestEvent> listener;
        listener = new GlobalEventListener<TestEvent>() {
            @Override
            public void onChange(TestEvent data) {
                System.out.println(data.getData());
            }
        };
        EventNotify.listen(listener);
        EventNotify.sendEvent(new TestEvent("test"));
        EventNotify.unListen(listener);
        EventNotify.sendEvent(new TestEvent("test Two"));
    }

    private static void testSafe() {
        GlobalEventListener<TestEvent> listener;
        listener = new GlobalEventListener<TestEvent>() {
            @Override
            public void onChange(TestEvent data) {
                System.out.println(data.getData());
            }
        };
        SafeEventNotify.listen(listener);
        SafeEventNotify.sendEvent(new TestEvent("test safe"));
        SafeEventNotify.unListen(listener);
        SafeEventNotify.sendEvent(new TestEvent("test safe Two"));
    }
}
