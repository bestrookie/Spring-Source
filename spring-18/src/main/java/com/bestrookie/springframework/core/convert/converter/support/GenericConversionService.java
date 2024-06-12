package com.bestrookie.springframework.core.convert.converter.support;

import cn.hutool.core.convert.Convert;
import com.bestrookie.springframework.core.convert.converter.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @Author bestrookie
 * @Date 2024/6/11 16:19
 * @Desc
 */
public class GenericConversionService implements ConversionService, ConverterRegistry {
    private Map<GenericConverter.ConvertiblePair, GenericConverter> converters = new HashMap<>();
    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        GenericConverter converter = getConverter(sourceType, targetType);
        return converter != null;
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        Class<?> sourceClass = source.getClass();
        GenericConverter converter = getConverter(sourceClass, targetType);
        return (T) converter.convert(source, sourceClass, targetType);
    }

    @Override
    public void addConverter(Converter<?, ?> converter) {
        GenericConverter.ConvertiblePair typeInfo = getRequiredTypeInfo(converter);
        ConverterAdapter converterAdapter = new ConverterAdapter(typeInfo, converter);
        for (GenericConverter.ConvertiblePair convertibleType : converterAdapter.getConvertibleTypes()) {
            converters.put(convertibleType, converterAdapter);
        }
    }

    @Override
    public void addConverter(GenericConverter converter) {
        for (GenericConverter.ConvertiblePair convertibleType : converter.getConvertibleTypes()) {
            converters.put(convertibleType, converter);
        }
    }

    @Override
    public void addConverterFactory(ConverterFactory<?, ?> converterFactory) {
        GenericConverter.ConvertiblePair typeInfo = getRequiredTypeInfo(converterFactory);
        ConvertFactoryAdapter convertFactoryAdapter = new ConvertFactoryAdapter(typeInfo, converterFactory);
        for (GenericConverter.ConvertiblePair convertibleType : convertFactoryAdapter.getConvertibleTypes()) {
            converters.put(convertibleType, convertFactoryAdapter);
        }
    }

    private GenericConverter.ConvertiblePair getRequiredTypeInfo(Object object){
        Type[] types = object.getClass().getGenericInterfaces();
        ParameterizedType parameterized = (ParameterizedType) types[0];
        Type[] actualTypeArguments = parameterized.getActualTypeArguments();
        Class sourceType = (Class) actualTypeArguments[0];
        Class tarfetType = (Class) actualTypeArguments[1];
        return new GenericConverter.ConvertiblePair(sourceType, tarfetType);
    }

    protected GenericConverter getConverter(Class<?> sourceType, Class<?> targetType){
        List<Class<?>> sourceCandidates = getClassHierarchy(sourceType);
        List<Class<?>> targetCandidates = getClassHierarchy(targetType);
        for (Class<?> sourceCandidate : sourceCandidates) {
            for (Class<?> targetCandidate : targetCandidates) {
                GenericConverter.ConvertiblePair convertiblePair = new GenericConverter.ConvertiblePair(sourceCandidate, targetCandidate);
                GenericConverter converter = converters.get(convertiblePair);
                if (converter != null){
                    return converter;
                }
            }
        }
        return null;
    }

    protected List<Class<?>> getClassHierarchy(Class<?> clazz){
        List<Class<?>> hierarchy = new ArrayList<>();
        while (clazz !=null){
            hierarchy.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return hierarchy;
    }

    private final class  ConverterAdapter implements GenericConverter{
        private final ConvertiblePair typeInfo;
        private final Converter<Object, Object> converter;

        public ConverterAdapter(ConvertiblePair typeInfo, Converter<?,?>converter) {
            this.converter = (Converter<Object, Object>) converter;
            this.typeInfo = typeInfo;
        }

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(typeInfo);
        }

        @Override
        public Object convert(Object source, Class sourceType, Class targetType) {
            return converter.conver(source);
        }
    }

    private final class ConvertFactoryAdapter implements GenericConverter{
        private final ConvertiblePair typeInfo;

        private final ConverterFactory<Object, Object> converterFactory;

        public ConvertFactoryAdapter(ConvertiblePair typeinfo, ConverterFactory<?,?> converterFactory){
            this.typeInfo = typeinfo;
            this.converterFactory = (ConverterFactory<Object, Object>) converterFactory;
        }

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(typeInfo);
        }

        @Override
        public Object convert(Object source, Class sourceType, Class targetType) {
            return converterFactory.getConverter(targetType).conver(source);
        }
    }
}
