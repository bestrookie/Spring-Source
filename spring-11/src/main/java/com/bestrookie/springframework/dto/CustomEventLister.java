package com.bestrookie.springframework.dto;

import com.bestrookie.springframework.context.ApplicationListener;

import java.util.Date;

/**
 * @Author bestrookie
 * @Date 2024/3/14 16:04
 * @Desc
 */
public class CustomEventLister implements ApplicationListener<CustomEvent> {
    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("收到：" + event.getSource() + "消息; 时间: "+ new Date());
        System.out.println("消息：" + event.getId() + ":" + event.getMessage());
    }
}
