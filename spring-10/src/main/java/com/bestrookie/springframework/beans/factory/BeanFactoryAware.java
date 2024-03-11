package com.bestrookie.springframework.beans.factory;

/**
 * @Author bestrookie
 * @Date 2024/3/11 14:58
 * @Desc
 */
public interface BeanFactoryAware  extends Aware{
    void setBeanFactory(BeanFactory beanFactory) throws Exception;
}
