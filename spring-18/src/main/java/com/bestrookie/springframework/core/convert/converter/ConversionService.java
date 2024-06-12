package com.bestrookie.springframework.core.convert.converter;

import com.sun.istack.internal.Nullable;

/**
 * @Author bestrookie
 * @Date 2024/6/11 16:20
 * @Desc
 */
public interface ConversionService {
    boolean canConvert(@Nullable Class<?> sourceType, Class<?> targetType);

    <T> T convert(Object source, Class<T> targetType);
}
