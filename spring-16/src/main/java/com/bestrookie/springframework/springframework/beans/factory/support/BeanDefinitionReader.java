package com.bestrookie.springframework.springframework.beans.factory.support;

import com.bestrookie.springframework.core.io.Resource;
import com.bestrookie.springframework.core.io.ResourceLoader;

/**
 * @Author bestrookie
 * @Date 2023/12/7 16:03
 * @Desc 读取文件流Bean
 */
public interface BeanDefinitionReader {
    /**
     * 获取注册方法
     * @return 注册方法
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 获取加载方法
     * @return 加载方法
     */
    ResourceLoader getResourceLoader();

    /**
     * 加载
     * @param resource resource
     * @throws Exception exception
     */
    void loadBeanDefinitions(Resource resource) throws Exception;

    /**
     * 加载
     * @param resources resource
     * @throws Exception exception
     */
    void loadBeanDefinitions(Resource... resources) throws Exception;

    /**
     * 加载
     * @param location location
     * @throws Exception exception
     */
    void loadBeanDefinitions(String location) throws Exception;

    void loadBeanDefinitions(String... location) throws Exception;



}
