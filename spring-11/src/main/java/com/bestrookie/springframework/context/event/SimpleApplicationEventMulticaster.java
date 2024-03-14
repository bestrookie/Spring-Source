package com.bestrookie.springframework.context.event;

import com.bestrookie.springframework.beans.factory.BeanFactory;
import com.bestrookie.springframework.context.ApplicationEvent;
import com.bestrookie.springframework.context.ApplicationListener;

/**
 * @Author bestrookie
 * @Date 2024/3/14 15:45
 * @Desc
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{
    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) throws Exception {
        setBeanFactory(beanFactory);
    }
    @Override
    public void multicastEvent(final ApplicationEvent event) throws Exception {
        for (final ApplicationListener listener : getApplicationListener(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
