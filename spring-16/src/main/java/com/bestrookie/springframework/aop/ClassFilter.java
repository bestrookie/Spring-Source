package com.bestrookie.springframework.aop;

/**
 * @Author bestrookie
 * @Date 2024/3/18 14:23
 * @Desc
 */
public interface ClassFilter {

    /**
     * 检查当前对象是否与指定的类匹配。
     *
     * @param clazz 要检查匹配的类，这是一个泛型参数，允许任何类型的类。
     * @return 如果当前对象是参数指定的类或其子类的实例，则返回true；否则返回false。
     */
    boolean matches(Class<?> clazz);
}
