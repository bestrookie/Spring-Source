package com.bestrookie.springframework.core.convert.converter.support;

import com.bestrookie.springframework.core.convert.converter.Converter;
import com.bestrookie.springframework.core.convert.converter.ConverterFactory;
import com.bestrookie.springframework.util.NumberUtils;
import com.sun.istack.internal.Nullable;

/**
 * @Author bestrookie
 * @Date 2024/6/11 16:09
 * @Desc
 */
public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {
    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToNumber<>(targetType);
    }

    public static final class StringToNumber<T extends Number> implements Converter<String, T>{
        private final Class<T> targetType;

        public StringToNumber(Class<T> targetType){
            this.targetType = targetType;
        }
        @Override
        @Nullable
        public T conver(String source) {
            if (source.isEmpty()){
                return null;
            }
            return NumberUtils.parseNumber(source, targetType);
        }
    }
}
