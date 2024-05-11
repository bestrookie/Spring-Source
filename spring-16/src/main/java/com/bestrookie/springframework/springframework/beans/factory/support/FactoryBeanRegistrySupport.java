package com.bestrookie.springframework.springframework.beans.factory.support;

import com.bestrookie.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author bestrookie
 * @Date 2024/3/12 14:18
 * @Desc
 */
public  abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected static final Object NULL_OBJECT = new Object();

    protected Object getCacheObjectForFactoryBean(String beanName){
        Object object = this.factoryBeanObjectCache.get(beanName);
        return object != NULL_OBJECT ? object : null;
    }

    /**
     * 从FactoryBean获取对象实例。
     *
     * @param factoryBean FactoryBean实例，用于生产对象。
     * @param beanName 要获取的对象在FactoryBean中的名称。
     * @return 返回FactoryBean生产的对象实例。如果对象是单例，则会缓存起来，多次调用返回相同的实例；如果不是单例，则每次调用返回新的实例。
     * @throws Exception 如果获取对象过程中发生错误，则抛出异常。
     */
    protected Object getObjectFromFactoryBean(FactoryBean factoryBean, String beanName) throws Exception {
        // 检查FactoryBean是否定义为单例
        if (factoryBean.isSingleton()){
            // 尝试从缓存中获取对象实例
            Object object = this.factoryBeanObjectCache.get(beanName);
            // 如果缓存中不存在，则从FactoryBean中创建并缓存对象实例
            if (object == null){
                object = doGetFormFactoryBean(factoryBean, beanName);
                // 将新创建的对象实例（或null）放入缓存
                this.factoryBeanObjectCache.put(beanName, object != null ? object : NULL_OBJECT);
            }
            // 返回缓存中的对象实例，如果为特定的NULL_OBJECT标志，则返回null
            return object != NULL_OBJECT ? object : null;
        }else {
            // 如果不是单例，直接从FactoryBean中获取对象实例，不进行缓存
            return doGetFormFactoryBean(factoryBean, beanName);
        }
    }


    private Object doGetFormFactoryBean(final FactoryBean factoryBean, final String beanName) throws Exception {
        try {
            Object object = factoryBean.getObject();
            return object;
        }catch (Exception e){
            throw new Exception("factoryBean threw exception on object[" + beanName + "] creation",e);
        }
    }
}
