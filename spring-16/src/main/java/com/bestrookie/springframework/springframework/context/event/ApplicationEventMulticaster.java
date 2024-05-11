package com.bestrookie.springframework.springframework.context.event;

import com.bestrookie.springframework.context.ApplicationEvent;
import com.bestrookie.springframework.context.ApplicationListener;

/**
 * @Author bestrookie
 * @Date 2024/3/14 14:34
 * @Desc
 */
public interface ApplicationEventMulticaster {
    /**
     * 添加一个应用程序监听器。
     * 该方法用于向系统中注册一个监听器，以便在特定事件发生时得到通知。
     *
     * @param listener 待添加的应用程序监听器。这是一个泛型参数，?表示监听器可以监听任意类型的事件。
     *                 通过传入具体的监听器实例，当系统中发生对应的事件时，该实例将会被调用。
     */
    void addApplicationLister(ApplicationListener<?> listener);


    /**
     * 移除指定的应用程序监听器。
     *
     * @param listener 要移除的应用程序监听器，类型为泛型 ApplicationListener。
     *                 该监听器之前通过 addApplicationListener 方法添加。
     *                 移除后，将不再接收应用程序相关的事件通知。
     */
    void removeApplicationListener(ApplicationListener<?> listener);


    /**
     * 向多个监听器广播事件。
     * 此方法用于将一个应用程序事件广播给所有注册的事件监听器。
     * 每个监听器都会收到此事件的副本，以便它们可以独立处理。
     *
     * @param event 要广播的应用程序事件。此参数不应为null。
     */
    void multicastEvent(ApplicationEvent event) throws Exception;

}
