package com.bestrookie.springframework.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.bestrookie.springframework.PropertyValue;
import com.bestrookie.springframework.PropertyValues;
import com.bestrookie.springframework.factory.config.BeanDefinition;
import com.bestrookie.springframework.factory.config.BeanReference;

import java.lang.reflect.Constructor;

/**
 * @Author bestrookie
 * @Date 2023/11/24 10:07
 * @Desc
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();
    @Override
    protected Object crateBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws Exception{
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition, beanName, args);
            applyPropertyValues(beanName, bean, beanDefinition);
        }catch (Exception e){
            throw new Exception("Instantiation of bean failed", e);
        }
        addSingleton(beanName, bean);
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) throws Exception {
        Constructor constructor = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        for (Constructor<?> ctor : declaredConstructors) {
            if (null != args && ctor.getParameterTypes().length == args.length){
                constructor = ctor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructor, args);
    }

    public InstantiationStrategy getInstantiationStrategy(){
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy){
        this.instantiationStrategy = instantiationStrategy;
    }

    protected void  applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                if (value instanceof BeanReference){
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                BeanUtil.setFieldValue(bean,name,value);

            }

        }catch (Exception e){
            throw new Exception("Error setting property values:"+ beanName);
        }
    }
}
