package com.bestrookie.springframework.springframework.beans.factory;

/**
 * @Author bestrookie
 * @Date 2024/3/12 14:16
 * @Desc
 */
public interface FactoryBean<T> {
    T getObject() throws Exception;

    Class<T> getObjectType();

    boolean isSingleton();

}
