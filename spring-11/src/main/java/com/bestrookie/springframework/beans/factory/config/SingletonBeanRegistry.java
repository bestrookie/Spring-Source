package com.bestrookie.springframework.beans.factory.config;

/**
 * @Author bestrookie
 * @Date 2023/11/23 10:34
 * @Desc 单例注册接口
 */
public interface SingletonBeanRegistry {
    /**
     * 获取单例对象
     * @param beanName 对象名称
     * @return 对象
     */
    Object getSingleton(String beanName);

    /**
     * 销毁单利对象
     */
    void destroySingletons() throws Exception;
}
