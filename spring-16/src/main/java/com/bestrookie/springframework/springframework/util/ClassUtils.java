package com.bestrookie.springframework.springframework.util;

/**
 * @Author bestrookie
 * @Date 2023/12/7 11:43
 * @Desc
 */
public class ClassUtils {
    public static ClassLoader getDefaultClassLoader(){
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }catch (Exception e){

        }
        if (cl == null){
            cl = ClassUtils.class.getClassLoader();
        }
        return cl;
    }

    public static boolean isCglibProxyClass(Class<?> clazz){
        return clazz != null && isCglibProxyClassName(clazz.getName());
    }

    /**
     * 判断给定的类名是否是Cglib代理类的名称。
     * Cglib代理类的名称中通常会包含"$$"。
     *
     * @param className 待判断的类名
     * @return 如果类名不为null且包含"$$"，则返回true；否则返回false。
     */
    public static boolean isCglibProxyClassName(String className){
        return className != null && className.contains("$$");
    }
}
