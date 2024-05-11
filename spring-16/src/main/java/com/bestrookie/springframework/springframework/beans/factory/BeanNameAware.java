package com.bestrookie.springframework.springframework.beans.factory;

/**
 * @Author bestrookie
 * @Date 2024/3/11 15:00
 * @Desc
 */
public interface BeanNameAware extends Aware {
    void setBeanName(String name);
}
