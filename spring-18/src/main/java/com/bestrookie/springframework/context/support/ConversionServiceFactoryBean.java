package com.bestrookie.springframework.context.support;

import com.bestrookie.springframework.beans.factory.FactoryBean;
import com.bestrookie.springframework.beans.factory.InitializingBean;
import com.bestrookie.springframework.core.convert.converter.*;
import com.bestrookie.springframework.core.convert.converter.support.DefaultConversionService;
import com.bestrookie.springframework.core.convert.converter.support.GenericConversionService;
import com.sun.istack.internal.Nullable;

import java.util.Set;

/**
 * @Author bestrookie
 * @Date 2024/6/11 16:58
 * @Desc
 */
public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {
    @Nullable
    private Set<?> converters;

    @Nullable
    private GenericConversionService conversionService;

    @Override
    public ConversionService getObject() throws Exception {
        return conversionService;
    }

    @Override
    public Class<?> getObjectType() {
        return conversionService.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.conversionService = new DefaultConversionService();
        registerConverters(converters, conversionService);
    }

    private void registerConverters(Set<?> converters, ConverterRegistry registry){
        if (converters != null){
            for (Object converter : converters) {
                if (converter instanceof GenericConverter){
                    registry.addConverter((GenericConverter) converter);
                } else if (converter instanceof Converter<?,?>) {
                    registry.addConverter((Converter<?, ?>) converter);
                } else if (converter instanceof ConverterFactory<?, ?>) {
                    registry.addConverterFactory((ConverterFactory<?,?>) converter);
                }else {
                    throw new IllegalArgumentException("Each converter object must implement one of the " +
                            "Converter, ConverterFactory, or GenericConverter interfaces");
                }
            }
        }
    }

    public void setConverters(Set<?> converters){
        this.converters = converters;
    }
}
