package com.bestrookie.springframework.beans.factory;

import cn.hutool.core.bean.BeanException;

/**
 * @Author bestrookie
 * @Date 2024/6/4 16:14
 * @Desc
 */
public interface ObjectFactory<T> {
    T getObject() throws BeanException;
}
