package com.bestrookie.springframework.dto.converter;

import com.bestrookie.springframework.beans.factory.FactoryBean;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author bestrookie
 * @Date 2024/6/12 15:26
 * @Desc
 */
public class ConvertersFactoryBean implements FactoryBean<Set<?>> {
    @Override
    public Set<?> getObject() throws Exception {
        HashSet<Object> converters = new HashSet<>();
        StringToLocalDateConverter stringToLocalDateConverter = new StringToLocalDateConverter("yyyy-MM-dd");
        converters.add(stringToLocalDateConverter);
        return converters;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
