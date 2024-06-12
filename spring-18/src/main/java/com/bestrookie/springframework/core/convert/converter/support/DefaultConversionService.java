package com.bestrookie.springframework.core.convert.converter.support;

import com.bestrookie.springframework.core.convert.converter.ConverterRegistry;
import com.bestrookie.springframework.core.convert.converter.GenericConverter;

/**
 * @Author bestrookie
 * @Date 2024/6/11 16:08
 * @Desc
 */
public class DefaultConversionService extends GenericConversionService {
    public DefaultConversionService(){
        addDefaultConverters(this);
    }
    public static void addDefaultConverters(ConverterRegistry converterRegistry){
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
    }
}
