package com.bestrookie.springframework.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.bestrookie.springframework.beans.factory.config.BeanDefinition;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Author bestrookie
 * @Date 2024/4/1 14:50
 * @Desc
 */
public class ClassPathScanningCandidateComponentProvider {
    /**
     * 在指定的包中查找所有标有@Component注解的组件。
     *
     * @param basePackage 基础包名，表示要扫描的包。
     * @return 返回一个包含所有找到的组件定义的集合。每个组件定义对应一个类。
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage){
        // 初始化一个有序的组件集合
        Set<BeanDefinition> candidate = new LinkedHashSet<>();
        // 扫描指定包中所有标有@Component注解的类
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        // 遍历扫描到的类，并为每个类创建一个BeanDefinition实例，添加到候选组件集合中
        for (Class<?> clazz : classes) {
            candidate.add(new BeanDefinition(clazz));
        }
        return candidate;
    }

}
