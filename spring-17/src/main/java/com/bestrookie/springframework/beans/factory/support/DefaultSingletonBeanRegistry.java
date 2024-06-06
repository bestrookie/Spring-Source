package com.bestrookie.springframework.beans.factory.support;

import com.bestrookie.springframework.beans.factory.DisposableBean;
import com.bestrookie.springframework.beans.factory.ObjectFactory;
import com.bestrookie.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author bestrookie
 * @Date 2023/11/23 10:37
 * @Desc 默认单例实现类
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    /**
     * 一级缓存,普通对象
     */
    private final Map<String, Object>singletonObjects = new ConcurrentHashMap<>();

    /**
     * 二级缓存，提前暴露对象，你没有完全实例化对象
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>();

    /**
     * 三级缓存， 存放代理对象
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();
    
    private final Map<String, DisposableBean> disposableBeanMap = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        Object singletonObject = singletonObjects.get(beanName);
        if (null == singletonObject){
            singletonObject = earlySingletonObjects.get(beanName);
            //判断二级缓存是否存有对象，这个对象是代理对象，因为只有二级对象才会放到三级缓存
            if (null == singletonObject){
                ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                if (singletonFactory != null){
                    singletonObject = singletonFactory.getObject();
                    //把三级缓存中的代理对象中的真实对象获取出来，放入二级缓存中
                    earlySingletonObjects.put(beanName, singletonObject);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    public void destroySingletons() throws Exception {
        Set<String> keySet = this.disposableBeanMap.keySet();
        Object[] disposableBeanNames = keySet.toArray();
        for (int i = disposableBeanNames.length -1 ; i >= 0 ; i--) {
            Object disposableBeanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeanMap.remove(disposableBeanName);
            try {
                disposableBean.destory();
            } catch (Exception e) {
                throw new Exception("Destroy method on bean with name '" + disposableBeanName + "' threw an exception", e);
            }
        }
    }


    public void registerDisposableBean(String beanName, DisposableBean bean){
        disposableBeanMap.put(beanName, bean);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory){
        if (!this.singletonObjects.containsKey(beanName)){
            this.singletonFactories.put(beanName, singletonFactory);
            this.earlySingletonObjects.remove(beanName);
        }
    }
        
}
