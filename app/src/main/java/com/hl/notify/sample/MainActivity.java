package com.hl.notify.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hl.notify.R;
import com.hl.notify.event.EventNotify;
import com.hl.notify.event.GlobalEventListener;

public class MainActivity extends AppCompatActivity {
    GlobalEventListener<TestEvent> listener;
    /**
     *监听消息：
     * 1 创建一个Class XXXEvent 实现 Event
     * 2 实例化一个GlobalEventListener<TestEvent>  listener
     * 3 注册listener  EventNotify.listen(listener);
     * 4 销毁EventNotify.unListen(listener);
     *
     *发送消息：
     * EventNotify.sendEvent(new TestEvent("data"));
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listener = new GlobalEventListener<TestEvent>() {
            @Override
            public void onChange(TestEvent data) {
                Toast.makeText(MainActivity.this, data.getData(), Toast.LENGTH_SHORT).show();
            }
        };
        //注册消息
        EventNotify.listen(listener);

    }

    public void onClick(View view) {
        //所以listen TestEvent的listener 都会收到通知
        EventNotify.sendEvent(new TestEvent("data"));
    }

    @Override
    protected void onDestroy() {
        //取消注册。这个很重要，如果不取消注册，将会导致内存泄漏。
        EventNotify.unListen(listener);
        super.onDestroy();
    }
}
