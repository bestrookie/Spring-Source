package com.bestrookie.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import com.bestrookie.springframework.beans.PropertyValue;
import com.bestrookie.springframework.beans.PropertyValues;
import com.bestrookie.springframework.beans.factory.*;
import com.bestrookie.springframework.beans.factory.config.*;
import com.bestrookie.springframework.core.convert.converter.ConversionService;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @Author bestrookie
 * @Date 2023/11/24 10:07
 * @Desc
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();
    @Override
    protected Object crateBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws Exception{
        // 判断是否返回代理 Bean 对象
        Object bean = resolveBeforeInstantiation(beanName, beanDefinition);
        if (null != bean) {
            return bean;
        }

        return doCreateBean(beanName, beanDefinition, args);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws Exception {
        Object bean = null;
        try {
            // 实例化 Bean
            bean = createBeanInstance(beanDefinition, beanName, args);

            // 处理循环依赖，将实例化后的Bean对象提前放入缓存中暴露出来
            if (beanDefinition.isSingleton()) {
                Object finalBean = bean;
                addSingletonFactory(beanName, () -> {
                    try {
                        return getEarlyBeanReference(beanName, beanDefinition, finalBean);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            // 实例化后判断
            boolean continueWithPropertyPopulation = applyBeanPostProcessorsAfterInstantiation(beanName, bean);
            if (!continueWithPropertyPopulation) {
                return bean;
            }
            // 在设置 Bean 属性之前，允许 BeanPostProcessor 修改属性值
            applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);
            // 给 Bean 填充属性
            applyPropertyValues(beanName, bean, beanDefinition);
            // 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new Exception("Instantiation of bean failed", e);
        }

        // 注册实现了 DisposableBean 接口的 Bean 对象
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        // 判断 SCOPE_SINGLETON、SCOPE_PROTOTYPE
        Object exposedObject = bean;
        if (beanDefinition.isSingleton()) {
            // 获取代理对象
            exposedObject = getSingleton(beanName);
            registerSingleton(beanName, exposedObject);
        }
        return exposedObject;
    }

    /**
     * 获取早期bean引用，主要用于支持AOP代理的创建。
     * 在实际bean实例化之前，这个方法允许bean后处理器提前访问bean实例，从而可以对bean进行额外的处理。
     * 如果某个bean后处理器实现了InstantiationAwareBeanPostProcessor接口，那么它有机会在这个阶段获取到bean的引用。
     * 这个方法遍历所有的bean后处理器，如果某个后处理器返回了非null的引用，则使用该引用替换原有的bean实例。
     * 如果返回null，则表示该后处理器不处理这个bean，遍历下一个后处理器。
     *
     * @param beanName bean的名称
     * @param beanDefinition bean的定义
     * @param bean 实际的bean实例
     * @return 早期的bean引用，可能被bean后处理器修改过
     * @throws Exception 如果处理过程中发生异常
     */
    protected Object getEarlyBeanReference(String beanName, BeanDefinition beanDefinition, Object bean) throws Exception {
        Object exposedObject = bean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                exposedObject = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).getEarlyBeanReference(exposedObject, beanName);
                if (null == exposedObject) return exposedObject;
            }
        }

        return exposedObject;
    }


    protected  void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor){
                PropertyValues pvs = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
                if (null != pvs){
                    for (PropertyValue propertyValue : pvs.getPropertyValues()) {
                        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                    }
                }
            }
        }
    }
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition){
        if (!beanDefinition.isSingleton()){
            return;
        }
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())){
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
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
                }else{
                    Class<?> sourceType = value.getClass();
                    Class<?> targetType = (Class<?>)TypeUtil.getFieldType(bean.getClass(), name);
                    ConversionService conversionService = getConversionService();
                    if (conversionService != null){
                        if (conversionService.canConvert(sourceType, targetType)){
                            value = conversionService.convert(value, targetType);
                        }
                    }

                }
                BeanUtil.setFieldValue(bean,name,value);

            }

        }catch (Exception e){
            throw new Exception("Error setting property values:"+ beanName);
        }
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
        if (bean instanceof Aware){
            if (bean instanceof BeanFactoryAware){
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware){
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            if (bean instanceof BeanNameAware){
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }
        //1.执行 BeanPostProcessor before 处理
        Object wrappedBean = applyBeanPostProcessorsBeForeInitialization(bean, beanName);
        //待完成内容
        invokeInitMethods(beanName, wrappedBean, beanDefinition);
        //2.执行 BeanPostProcessor after处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        return wrappedBean;
    }

    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        //1.实现接口 initializingBean
        if(bean instanceof InitializingBean){
            ((InitializingBean) bean).afterPropertiesSet();
        }

        //2.配置信息 init0method {判断是为了避免二次执行销毁}
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)){
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (null == initMethod){
                throw new Exception("could not find an init method name "  + initMethodName + "on bean with name " + beanName);
            }
            initMethod.invoke(bean);
        }
    }

    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) throws Exception {
        Object bean = applyBeanPostProcessBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (null != bean){
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    public Object applyBeanPostProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws Exception{
        for (BeanPostProcessor processor : getBeanPostProcessors()){
            if (processor instanceof InstantiationAwareBeanPostProcessor){
                Object result = ((InstantiationAwareBeanPostProcessor) processor).postprocessBeforeInstantiation(beanClass, beanName);
                if (null != result) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * 在 bean 实例化后应用所有的 BeanPostProcessor。如果其中一个 InstantiationAwareBeanPostProcessor
     * 指示不应继续进行属性填充，则停止处理并返回 false。
     *
     * @param beanName 要处理的 bean 的名称。
     * @param bean 已经实例化的 bean 对象。
     * @return 如果处理完成后应继续填充属性，则返回 true；如果某个 InstantiationAwareBeanPostProcessor
     *         请求停止填充属性，则返回 false。
     * @throws Exception 如果在应用 BeanPostProcessor 时发生异常。
     */
    private boolean applyBeanPostProcessorsAfterInstantiation(String beanName, Object bean) throws Exception {
        // 默认继续属性填充
        boolean continueWithPropertyPopulation = true;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            // 检查是否为 InstantiationAwareBeanPostProcessor 实例
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor){
                InstantiationAwareBeanPostProcessor instantiationAwareBeanPostProcessor = (InstantiationAwareBeanPostProcessor) beanPostProcessor;
                // 如果该 BeanPostProcessor 请求停止填充属性，则设置标志并中断循环
                if (!instantiationAwareBeanPostProcessor.postProcessAfterInstantiation(bean, beanName)){
                    continueWithPropertyPopulation = false;
                    break;
                }
            }
        }
        return continueWithPropertyPopulation;
    }
}
