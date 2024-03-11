package com.bestrookie.springframework.util;

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
}
