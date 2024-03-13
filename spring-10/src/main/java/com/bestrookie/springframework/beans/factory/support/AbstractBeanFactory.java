package com.bestrookie.springframework.beans.factory.support;

import com.bestrookie.springframework.beans.factory.FactoryBean;
import com.bestrookie.springframework.beans.factory.config.BeanDefinition;
import com.bestrookie.springframework.beans.factory.config.BeanPostProcessor;
import com.bestrookie.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bestrookie.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author bestrookie
 * @Date 2023/11/24 09:51
 * @Desc 抽象定义模板方法
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
    @Override
    public Object getBean(String beanName) throws Exception {
        return doGetBean(beanName, null);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws Exception {
        return doGetBean(beanName, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requireType) throws Exception {
        return (T) getBean(name);
    }

    /**
     * 以泛型形式获取一个bean实例。
     *
     * @param name bean的名称。
     * @param args 创建bean时可能需要的参数。
     * @return 返回bean的实例，其类型为泛型T。
     * @throws Exception 如果过程中发生错误，则抛出异常。
     */
    protected <T> T doGetBean(final String name, final Object[] args) throws Exception {
       // 尝试从单例池中获取bean实例
       Object sharedInstance = getSingleton(name);
       if (sharedInstance != null){
           // 如果找到共享实例，直接返回该实例
           return (T) getObjectForBeanInstance(sharedInstance, name);
       }
       // 如果没有找到共享实例，则从bean定义中获取信息来创建新实例
       BeanDefinition beanDefinition = getBeanDefinition(name);
        // 根据bean定义和参数创建bean实例
        Object bean = crateBean(name, beanDefinition, args);
        // 返回新创建的bean实例
        return (T) getObjectForBeanInstance(bean, name);
    }


    protected abstract BeanDefinition getBeanDefinition(String beanName) throws Exception;

    protected abstract Object crateBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws Exception;

    /**
     * 添加一个BeanPostProcessor后置处理器到列表中。
     * 如果该处理器已经存在于列表中，则先移除再重新添加，确保其位置可能发生变化。
     *
     * @param beanPostProcessor 要添加的BeanPostProcessor实例
     */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        // 先尝试移除，确保即使已存在也能重新添加到列表的正确位置
        this.beanPostProcessors.remove(beanPostProcessor);
        // 将beanPostProcessor添加到列表中
        this.beanPostProcessors.add(beanPostProcessor);
    }


    public List<BeanPostProcessor> getBeanPostProcessors(){
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader(){
        return this.beanClassLoader;
    }

    /**
     * 根据bean实例获取相应的对象。
     * 如果bean实例不是FactoryBean的实例，则直接返回该实例。
     * 如果bean实例是FactoryBean的实例，则尝试从工厂bean中获取对象。
     *
     * @param beanInstance Bean的实例，可以是普通Bean或FactoryBean的实例。
     * @param beanName Bean的名称。
     * @return 从bean实例或工厂bean中获取的对象。
     * @throws Exception 如果过程中发生错误，则抛出异常。
     */
    private Object getObjectForBeanInstance(Object beanInstance, String beanName) throws Exception {
        // 如果bean实例不是FactoryBean，则直接返回该实例
        if (!(beanInstance instanceof FactoryBean)){
            return beanInstance;
        }

        // 尝试从缓存中获取对象，如果缓存中不存在，则从工厂bean中获取
        Object object = getCacheObjectForFactoryBean(beanName);
        if (object == null){
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }
        return object;
    }
}
