package com.bestrookie.springframework.context.event;

import com.bestrookie.springframework.context.ApplicationContext;
import com.bestrookie.springframework.context.ApplicationEvent;

/**
 * @Author bestrookie
 * @Date 2024/3/14 14:27
 * @Desc 定义时间的抽象类，所有的时间包括关闭、刷新，以及实现用户自己实现的事件，都需要继承这个类
 */
public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
