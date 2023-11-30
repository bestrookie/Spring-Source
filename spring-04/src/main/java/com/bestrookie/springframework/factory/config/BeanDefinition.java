package com.bestrookie.springframework.factory.config;

/**
 * @Author bestrookie
 * @Date 2023/11/20 13:57
 * @Desc bean实例化对象
 */
public class BeanDefinition {
    private Class beanClass;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
