package com.bestrookie.springframework.springframework.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.bestrookie.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.bestrookie.springframework.beans.factory.config.BeanDefinition;
import com.bestrookie.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.Set;

/**
 * @Author bestrookie
 * @Date 2024/4/1 15:56
 * @Desc
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    /**
     * 对给定的基本包进行扫描，注册找到的所有候选组件。
     *
     * @param basePackages 扫描的基础包名，可以传入多个基础包名。
     *                     会递归扫描这些包及其子包下的所有组件。
     */
    public void doScan(String... basePackages){
        // 遍历所有基础包
        for (String basePackage : basePackages) {
            // 在指定包下寻找候选组件
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            // 遍历所有找到的候选组件
            for (BeanDefinition beanDefinition : candidates) {
                // 解析组件的作用域
                String beanScope = resolveBeanScope(beanDefinition);
                // 如果作用域非空，则设置组件的作用域
                if (StrUtil.isNotEmpty(beanScope)){
                    beanDefinition.setScope(beanScope);
                }
                // 注册组件，确定组件名称并注册到BeanDefinition注册器中
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }
        registry.registerBeanDefinition("com.bestrookie.springframework.context.annotation.internalAutowiredAnnotationProcessor", new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));

    }



    /**
     * 解析Bean的范围。
     * <p>此方法通过检查Bean定义的类上是否标注了Scope注解来确定Bean的范围。如果找到了Scope注解，则返回注解中指定的范围值；如果没有找到，则返回空字符串。</p>
     *
     * @param beanDefinition Bean的定义信息，包含Bean的类等信息。
     * @return Bean的范围，如果设置了Scope注解，则返回注解中的value值，否则返回空字符串。
     */
    private String resolveBeanScope(BeanDefinition beanDefinition){
        // 获取Bean定义的类
        Class<?> beanClass = beanDefinition.getBeanClass();

        // 尝试从类上获取Scope注解
        Scope scope = beanClass.getAnnotation(Scope.class);

        // 如果找到了Scope注解，返回注解中的value值；否则返回空字符串
        if (null != scope){
            return scope.value();
        }else {
            return StrUtil.EMPTY;
        }
    }


    /**
     * 确定Bean的名称。
     * 该方法通过读取Bean定义中的类信息，尝试从@Component注解中获取定义的bean名称。如果@Component注解中没有指定名称，
     * 则默认使用类的简单名称，并转换为小写首字母作为bean名称。
     *
     * @param beanDefinition Bean的定义信息，包含类和其他元数据。
     * @return String 返回确定的Bean名称。
     */
    private String determineBeanName(BeanDefinition beanDefinition){
        // 获取Bean定义对应的类
        Class<?> beanClass = beanDefinition.getBeanClass();
        // 尝试从类上获取@Component注解
        Component component = beanClass.getAnnotation(Component.class);
        // 获取注解中指定的Bean名称
        String value = component.value();
        // 如果注解中没有指定名称，返回类名的小写形式
        if (StrUtil.isEmpty(value)){
            return StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        // 如果注解中指定了名称，返回该名称
        return value;
    }

}
