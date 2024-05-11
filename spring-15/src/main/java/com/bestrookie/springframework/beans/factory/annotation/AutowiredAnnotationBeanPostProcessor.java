package com.bestrookie.springframework.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import com.bestrookie.springframework.beans.PropertyValues;
import com.bestrookie.springframework.beans.factory.BeanFactory;
import com.bestrookie.springframework.beans.factory.BeanFactoryAware;
import com.bestrookie.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.bestrookie.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.bestrookie.springframework.util.ClassUtils;

import java.lang.reflect.Field;

/**
 * @Author bestrookie
 * @Date 2024/4/3 16:20
 * @Desc
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws Exception {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return null;
    }

    @Override
    public Object postprocessBeforeInstantiation(Class<?> beanClass, String beanName) throws Exception {
        return null;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws Exception {
        Class<?> clazz = bean.getClass();
       clazz =  ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        Field[] declaredFields = clazz.getDeclaredFields();

        //处理@Value注解
        for (Field fileld : declaredFields) {
            Value valueAnnotation = fileld.getAnnotation(Value.class);
            if (null != valueAnnotation){
                String value = valueAnnotation.value();
                value = beanFactory.resolveEmbeddedValue(value);
                BeanUtil.setFieldValue(bean, fileld.getName(), value);
            }
        }
        //处理@Autowired注解
        for (Field field : declaredFields) {
            Autowired annotation = field.getAnnotation(Autowired.class);
            if (null != annotation){
                Class<?> fieldType = field.getType();
                String dependentBeanName = null;
                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                Object dependentBean = null;
                if (null != qualifier){
                    dependentBeanName = qualifier.value();
                    dependentBean = beanFactory.getBean(dependentBeanName, fieldType);
                }else {
                    dependentBean = beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }
        return pvs;
    }
}
