package com.bestrookie.springframework.context;

import com.bestrookie.springframework.beans.factory.Aware;

/**
 * @Author bestrookie
 * @Date 2024/3/11 15:01
 * @Desc
 */
public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext) throws Exception;
}
