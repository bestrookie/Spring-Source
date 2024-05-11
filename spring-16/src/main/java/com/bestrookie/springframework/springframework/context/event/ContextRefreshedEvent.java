package com.bestrookie.springframework.springframework.context.event;

/**
 * @Author bestrookie
 * @Date 2024/3/14 14:31
 * @Desc spring 框架自己实现的两个事件类，可以用于监听刷新动作
 */
public class ContextRefreshedEvent extends ApplicationContextEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
