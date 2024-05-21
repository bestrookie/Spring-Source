package com.bestrookie.springframework.beans.factory.config;

import com.bestrookie.springframework.beans.factory.HierarchicalBeanFactory;
import com.bestrookie.springframework.util.StringValueResolver;

/**
 * @Author bestrookie
 * @Date 2024/1/24 17:11
 * @Desc
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 添加一个Bean后处理器到列表中。
     * Bean后处理器是在Spring容器实例化Bean之后，但在Bean实际使用之前调用的一系列处理器。
     * 通过实现BeanPostProcessor接口，用户可以自定义逻辑在Bean的实例化过程中插入。
     *
     * @param beanPostProcessor 要添加的Bean后处理器实例。该参数必须不为空。
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);


    /**
     * 销毁单例对象。
     * 该方法负责销毁应用程序中所有的单例对象，释放相关资源。
     * 注意：此方法执行后，单例对象将不再可用。
     */
    void destroySingletons() throws Exception;


    /**
     * 添加一个嵌入式的字符串值解析器。
     * 该方法用于向系统中注册一个自定义的字符串值解析器，以便在解析特定的字符串时能够使用该解析器进行解析处理。
     * 这对于处理一些特殊格式的字符串或者需要自定义转换逻辑的情况非常有用。
     *
     * @param valueResolver 字符串值解析器，实现了StringValueResolver接口，用于解析特定格式的字符串。
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);


    /**
     * 解析嵌入式值。
     * 该方法用于解析给定字符串中的嵌入式值。具体的解析逻辑依赖于实现类的具体实现。
     * 通常，这会涉及到将字符串中的特定格式（如${...}）的表达式替换为其对应的值。
     *
     * @param value 待解析的字符串。该字符串可能包含一个或多个嵌入式表达式。
     * @return 解析后的字符串。嵌入式表达式会被其对应的值替换。
     */
    String resolveEmbeddedValue(String value);

}
