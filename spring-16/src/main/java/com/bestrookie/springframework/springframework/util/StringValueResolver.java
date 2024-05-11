package com.bestrookie.springframework.springframework.util;

/**
 * @Author bestrookie
 * @Date 2024/4/3 14:48
 * @Desc
 */
public interface StringValueResolver {
    /**
     * 解析字符串值。
     *
     * @param setVal 待解析的字符串。
     * @return 解析后的字符串结果。
     */
    String resolveStringValue(String setVal);

}
