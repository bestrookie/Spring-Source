package com.bestrookie.springframework.springframework.context;

import java.util.EventListener;

/**
 * @Author bestrookie
 * @Date 2024/3/14 14:41
 * @Desc
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    /**
     * 当应用程序发生特定事件时调用此方法。
     *
     * @param event 事件对象，代表了发生的特定事件。
     *              该参数的类型为泛型 E，允许处理多种类型的事件。
     * @return 该方法没有返回值，因为它主要是为了触发事件的处理逻辑。
     */
    void onApplicationEvent(E event);
}
