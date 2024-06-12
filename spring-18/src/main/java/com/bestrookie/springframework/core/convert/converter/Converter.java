package com.bestrookie.springframework.core.convert.converter;

/**
 * @Author bestrookie
 * @Date 2024/6/6 16:01
 * @Desc
 */
public interface Converter <S, T> {
    T conver(S source);
}
