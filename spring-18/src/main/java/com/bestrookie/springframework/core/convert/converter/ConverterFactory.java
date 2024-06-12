package com.bestrookie.springframework.core.convert.converter;

/**
 * @Author bestrookie
 * @Date 2024/6/6 16:21
 * @Desc
 */
public interface ConverterFactory<S, R> {
    <T extends R> Converter<S, T> getConverter(Class<T> targetType);
}
