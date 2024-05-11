package com.bestrookie.springframework.springframework.dto;

import com.bestrookie.springframework.context.ApplicationListener;
import com.bestrookie.springframework.context.event.ContextRefreshedEvent;

/**
 * @Author bestrookie
 * @Date 2024/3/14 16:06
 * @Desc
 */
public class CustomRefreshedEventLister implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("刷新事件：" + this.getClass().getName());
    }
}
