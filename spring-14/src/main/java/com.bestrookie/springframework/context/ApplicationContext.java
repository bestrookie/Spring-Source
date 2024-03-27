package com.bestrookie.springframework.context;

import com.bestrookie.springframework.beans.factory.HierarchicalBeanFactory;
import com.bestrookie.springframework.beans.factory.ListableBeanFactory;
import com.bestrookie.springframework.core.io.ResourceLoader;

/**
 * @Author bestrookie
 * @Date 2024/1/24 15:48
 * @Desc
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
