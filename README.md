# EventNotify
EventNotify 

监听消息：
      1 创建一个Class XXXEvent 实现 Event
      2 实例化一个GlobalEventListener<TestEvent>  listener
      3 注册listener  EventNotify.listen(listener);
      4 销毁EventNotify.unListen(listener);
     
发送消息：
      EventNotify.sendEvent(new TestEvent("data"));
    