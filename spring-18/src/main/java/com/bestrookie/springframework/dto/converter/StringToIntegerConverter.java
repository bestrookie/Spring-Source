package com.bestrookie.springframework.dto.converter;

import com.bestrookie.springframework.core.convert.converter.Converter;

/**
 * @Author bestrookie
 * @Date 2024/6/12 15:30
 * @Desc
 */
public class StringToIntegerConverter implements Converter<String, Integer> {
    @Override
    public Integer conver(String source) {
        return Integer.valueOf(source);
    }
}
