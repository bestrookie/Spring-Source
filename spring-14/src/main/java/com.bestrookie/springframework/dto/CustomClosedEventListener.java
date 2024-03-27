package com.bestrookie.springframework.dto;

import com.bestrookie.springframework.context.ApplicationListener;
import com.bestrookie.springframework.context.event.ContextCloseEvent;

/**
 * @Author bestrookie
 * @Date 2024/3/14 16:07
 * @Desc
 */
public class CustomClosedEventListener implements ApplicationListener<ContextCloseEvent> {
    @Override
    public void onApplicationEvent(ContextCloseEvent event) {
        System.out.println("关闭事件：" + this.getClass().getName());
    }
}
