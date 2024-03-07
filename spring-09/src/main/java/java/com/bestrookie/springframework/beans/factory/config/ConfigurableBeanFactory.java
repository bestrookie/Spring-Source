package java.com.bestrookie.springframework.beans.factory.config;

import com.bestrookie.springframework.beans.factory.HierarchicalBeanFactory;

/**
 * @Author bestrookie
 * @Date 2024/1/24 17:11
 * @Desc
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
