package com.bestrookie.springframework.beans.factory.config;

/**
 * @Author bestrookie
 * @Date 2024/3/19 16:15
 * @Desc
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{
    /**
     * 此方法作为BeanPostProcessor接口的实现，允许在目标Bean实例化之前对其进行处理。
     * 返回的Bean对象可以是一个代理，用以替代目标Bean，从而有效地抑制目标Bean的默认实例化。
     *
     * @param beanClass 目标Bean的类对象。表示即将被实例化的Bean的Class对象。
     * @param beanName 目标Bean的名称。在Spring容器中，每个Bean都有一个唯一的名称。
     * @return 返回一个Bean对象，它可以是目标Bean的代理，或者直接是目标Bean本身。
     *         如果返回null，则表示不对目标Bean进行任何处理，将继续进行正常的实例化流程。
     * @throws Exception 如果在处理过程中发生错误，抛出此异常。
     */
    Object postprocessBeforeInstantiation(Class<?> beanClass, String beanName) throws Exception;
}
