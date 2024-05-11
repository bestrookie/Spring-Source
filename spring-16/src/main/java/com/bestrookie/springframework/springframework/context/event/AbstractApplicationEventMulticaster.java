package com.bestrookie.springframework.springframework.context.event;

import com.bestrookie.springframework.beans.factory.BeanFactory;
import com.bestrookie.springframework.beans.factory.BeanFactoryAware;
import com.bestrookie.springframework.context.ApplicationEvent;
import com.bestrookie.springframework.context.ApplicationListener;
import com.bestrookie.springframework.util.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @Author bestrookie
 * @Date 2024/3/14 14:49
 * @Desc
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {
    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws Exception {
        this.beanFactory = beanFactory;

    }

    @Override
    public void addApplicationLister(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    /**
     * 获取支持特定事件的应用程序监听器集合。
     * @param event 应用程序事件，用于检查哪些监听器支持该事件。
     * @return 返回一个包含所有支持给定事件的监听器的集合。
     * @throws Exception 如果在获取监听器过程中发生错误，则抛出异常。
     */
    protected Collection<ApplicationListener> getApplicationListener(ApplicationEvent event) throws Exception {
        LinkedList<ApplicationListener> allListeners = new LinkedList<>();
        // 遍历所有监听器，将支持特定事件的监听器添加到结果集合中
        for (ApplicationListener<ApplicationEvent> listener  : applicationListeners) {
            if (supportsEvent(listener, event)){
                allListeners.add(listener);
            }
        }
        return allListeners;
    }
    /**
     * 判断指定的事件监听器是否支持特定的事件。
     * @param applicationListener 应用事件监听器，不可为null。
     * @param event 需要判断是否被监听器支持的事件，不可为null。
     * @return 如果监听器支持该事件，则返回true；否则返回false。
     * @throws Exception 如果无法通过反射确定监听器支持的事件类型，则抛出异常。
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) throws Exception {
        // 获取监听器的类对象
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();

        // 检查是否为CGLIB代理类，是则获取其超类，否则直接获取当前类
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;

        // 获取监听器实现的第一个接口的泛型参数类型
        Type genericInterface = targetClass.getGenericInterfaces()[0];

        // 获取实际的泛型参数类型
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();

        // 尝试根据类型名称加载类
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        }catch (Exception e){
            // 如果类加载失败，则抛出异常
            throw new Exception("wrong event class name" + className);
        }

        // 判断事件类是否被监听器支持的事件类型所赋权
        return eventClassName.isAssignableFrom(event.getClass());

    }

}
