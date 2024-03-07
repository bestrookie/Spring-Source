package com.bestrookie.springframework.beans.factory;

/**
 * @Author bestrookie
 * @Date 2024/3/6 15:00
 * @Desc 销毁方法接口
 */
public interface DisposableBean {

    /**
     * 销毁方法
     * @throws Exception exception
     */
    void destory() throws Exception;
}
