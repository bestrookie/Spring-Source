package com.bestrookie.springframework.aop;


/**
 * @Author bestrookie
 * @Date 2024/3/18 14:21
 * @Desc
 */
public interface Pointcut {
    /**
     * 获取一个ClassFilter实例。
     *
     * @return 返回一个ClassFilter实例，该实例用于过滤类。
     */
    ClassFilter getClassFilter();


    /**
     * 获取方法匹配器。
     * <p>此方法不接受任何参数。
     *
     * @return MethodMatcher 返回一个方法匹配器对象。该对象用于匹配特定的方法。
     */
    MethodMatcher getMethodMatcher();
}
