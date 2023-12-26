package com.bestrookie.springframework.beans.factory;

import java.util.Map;

/**
 * @Author bestrookie
 * @Date 2023/12/7 11:18
 * @Desc
 */
public interface ListableBeanFactory  extends  BeanFactory{

    /**
     * 按照类型返回Bean实例
     * @param type type
     * @return  bean实例
     * @param <T> 泛型
     * @throws Exception 异常
     */
    <T>Map<String, T> getBeanOfType(Class<T> type) throws Exception;


    /**
     *获取所有Bean名称
     * @return 返回注册表中所有的Bean名称
     */
    String[] getBeanDefinitionNames();
}
