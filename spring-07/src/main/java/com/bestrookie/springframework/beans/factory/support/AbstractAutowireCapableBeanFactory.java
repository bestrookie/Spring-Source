package com.bestrookie.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.bestrookie.springframework.beans.PropertyValue;
import com.bestrookie.springframework.beans.PropertyValues;
import com.bestrookie.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.bestrookie.springframework.beans.factory.config.BeanDefinition;
import com.bestrookie.springframework.beans.factory.config.BeanPostProcessor;
import com.bestrookie.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

/**
 * @Author bestrookie
 * @Date 2023/11/24 10:07
 * @Desc
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();
    @Override
    protected Object crateBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws Exception{
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition, beanName, args);
            //给 Bean 填充属性
            applyPropertyValues(beanName, bean, beanDefinition);
            // 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);
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

    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition){

    }

    @Override
    public Object applyBeanPostProcessorsBeForeInitialization(Object existingBean, String beanName) throws Exception {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()){
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (null == current){
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws Exception {
        Object result = existingBean;
        for (BeanPostProcessor postProcessor : getBeanPostProcessors()){
            Object current = postProcessor.postProcessAfterInitialization(result, beanName);
            if (null == current ){
                return result;
            }
            result = current;
        }
        return result;
    }

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        //1.执行 BeanPostProcessor before 处理
        Object wrappedBean = applyBeanPostProcessorsBeForeInitialization(bean, beanName);
        //待完成内容
        invokeInitMethods(beanName, wrappedBean, beanDefinition);
        //2.执行 BeanPostProcessor after处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        return wrappedBean;
    }

}
