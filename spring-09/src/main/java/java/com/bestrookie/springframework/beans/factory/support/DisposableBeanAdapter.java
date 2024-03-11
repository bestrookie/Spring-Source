package com.bestrookie.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.bestrookie.springframework.beans.factory.DisposableBean;
import com.bestrookie.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * @Author bestrookie
 * @Date 2024/3/6 15:24
 * @Desc
 */
public class DisposableBeanAdapter implements DisposableBean {
    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition){
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }
    @Override
    public void destory() throws Exception {
        //1.实现接口 DisposableBean
        if (bean instanceof DisposableBean){
            ((DisposableBean) bean).destory();
        }
        //2.配置信息 destroy-method
        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))){
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            destroyMethod.invoke(bean);
        }
    }
}
