package com.bestrookie.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanException;
import com.bestrookie.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.bestrookie.springframework.beans.factory.config.BeanDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author bestrookie
 * @Date 2023/11/24 10:27
 * @Desc
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws Exception {
         BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanName == null){
            throw new Exception("No bean name " + beanName +" is defined");
        }
        return beanDefinition;
    }

    @Override
    public void preInstantiateSingletons() throws Exception {
        for (String s : beanDefinitionMap.keySet()) {
            this.getBean(s);
        }
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public <T> Map<String, T> getBeanOfType(Class<T> type) throws Exception {
        Map<String, T> result = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                try {
                    result.put(beanName, (T) getBean(beanName));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public <T> T getBean(Class<T> requireType) throws Exception {
        List<String> beanNames = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            Class beanClass = entry.getValue().getBeanClass();
            if (requireType.isAssignableFrom(beanClass)){
                beanNames.add(entry.getKey());
            }
        }
        if (1 == beanNames.size()){
            return getBean(beanNames.get(0), requireType);
        }
        throw new BeanException(requireType + "expected single bean but found " + beanNames.size() + " :" + beanNames);
    }
}
