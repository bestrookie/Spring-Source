package com.bestrookie.springframework.springframework.core.io;

/**
 * @Author bestrookie
 * @Date 2023/12/7 15:33
 * @Desc 包装资源加载器
 */
public interface ResourceLoader {
    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 获取资源
     * @param location location地址
     * @return 资源
     */
    Resource getResource(String location);
}
