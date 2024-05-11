package com.bestrookie.springframework.beans.factory.support;

import com.bestrookie.springframework.beans.factory.DisposableBean;
import com.bestrookie.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author bestrookie
 * @Date 2023/11/23 10:37
 * @Desc 默认单例实现类
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private final Map<String, Object>singletonObjects = new HashMap<>();
    
    private final Map<String, DisposableBean> disposableBeanMap = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
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
        
}
