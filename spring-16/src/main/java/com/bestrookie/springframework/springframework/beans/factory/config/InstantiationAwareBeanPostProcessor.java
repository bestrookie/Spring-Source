package com.bestrookie.springframework.springframework.beans.factory.config;

import com.bestrookie.springframework.beans.PropertyValues;

/**
 * @Author bestrookie
 * @Date 2024/3/19 16:15
 * @Desc
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * 此方法作为BeanPostProcessor接口的实现，允许在目标Bean实例化之前对其进行处理。
     * 返回的Bean对象可以是一个代理，用以替代目标Bean，从而有效地抑制目标Bean的默认实例化。
     *
     * @param beanClass 目标Bean的类对象。表示即将被实例化的Bean的Class对象。
     * @param beanName  目标Bean的名称。在Spring容器中，每个Bean都有一个唯一的名称。
     * @return 返回一个Bean对象，它可以是目标Bean的代理，或者直接是目标Bean本身。
     * 如果返回null，则表示不对目标Bean进行任何处理，将继续进行正常的实例化流程。
     * @throws Exception 如果在处理过程中发生错误，抛出此异常。
     */
    Object postprocessBeforeInstantiation(Class<?> beanClass, String beanName) throws Exception;

    /**
     * 该方法定义了一个 Spring Bean 后处理器接口 `BeanPostProcessor` 中的抽象方法，
     * 用于在 Bean 属性设置阶段进行自定义处理。具体实现类应覆盖此方法以提供实际逻辑。
     *
     * @param pvs      类型为 {@link PropertyValues} 的参数，表示当前 Bean 的所有已解析和待设置的属性及其值集合。
     *                 这些属性值通常源自于 XML 配置文件、注解配置、Java 配置类等来源。
     * @param bean     类型为 `Object` 的参数，代表正在被处理的目标 Bean 实例。
     *                 开发者可以通过此参数访问并操作 Bean 的实际状态或执行相关业务逻辑。
     * @param beanName 类型为 `String` 的参数，表示当前 Bean 在 Spring 容器中的唯一标识名称。
     *                 该名称通常与 XML 配置中的 `id` 或 `name` 属性值，或 Java 配置类中 `@Bean` 注解的名称相对应。
     * @return 返回类型为 `PropertyValues` 的结果，表示经过处理后的属性值集合。
     * 返回 `null` 表示不进行任何更改，Spring 容器将继续使用原始 `pvs` 对象进行后续的 Bean 初始化；
     * 若返回非 `null` 值，Spring 容器将使用这个新的 `PropertyValues` 对象替代原始对象继续后续初始化。
     * @throws Exception 该方法允许抛出 `Exception` 异常，以便在属性值后处理过程中遇到问题时进行报告。
     *                   Spring 容器会捕获并记录此类异常，可能导致当前 Bean 的初始化过程终止。
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws Exception;

}
