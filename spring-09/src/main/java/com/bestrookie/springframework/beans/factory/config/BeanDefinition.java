package com.bestrookie.springframework.beans.factory.config;

import com.bestrookie.springframework.beans.PropertyValues;
import lombok.Data;

/**
 * @Author bestrookie
 * @Date 2023/11/20 13:57
 * @Desc bean实例化对象
 */
@Data
public class BeanDefinition {
    private Class beanClass;
    private PropertyValues propertyValues;

    private String initMethodName;

    private String destroyMethodName;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues){
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }
}
