package com.bestrookie.springframework.context;

/**
 * @Author bestrookie
 * @Date 2024/3/14 15:36
 * @Desc
 */
public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event) throws Exception;
}
