package com.hl.notify.sample;

import com.hl.notify.event.Event;

public class TestEvent extends Event<String> {
    public TestEvent(String data) {
        super(data);
    }
}
