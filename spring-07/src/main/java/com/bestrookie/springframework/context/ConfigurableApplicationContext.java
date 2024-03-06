package com.bestrookie.springframework.context;

/**
 * @Author bestrookie
 * @Date 2024/1/24 15:58
 * @Desc
 */
public interface ConfigurableApplicationContext extends ApplicationContext{

    /**
     * 刷新容器
     * @throws Exception exception
     */
    void refresh() throws Exception;
}
