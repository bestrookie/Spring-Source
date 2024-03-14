package com.bestrookie.springframework.context.event;

import com.bestrookie.springframework.context.event.ApplicationContextEvent;

/**
 * @Author bestrookie
 * @Date 2024/3/14 14:29
 * @Desc spring 框架自己实现的两个事件类，可以用于监听关闭动作
 */
public class ContextCloseEvent extends ApplicationContextEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextCloseEvent(Object source) {
        super(source);
    }
}
