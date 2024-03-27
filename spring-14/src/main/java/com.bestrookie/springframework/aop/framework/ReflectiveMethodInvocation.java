package com.bestrookie.springframework.aop.framework;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * @Author bestrookie
 * @Date 2024/3/18 15:25
 * @Desc
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    protected final Object target;

    protected final Method method;

    protected final Object[] arguments;

    public ReflectiveMethodInvocation(Object target, Method method, Object[] args){
        this.target = target;
        this.method = method;
        this.arguments = args;
    }
    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    /**
     * 继续执行被拦截的方法。
     *
     * @return 返回被拦截方法的执行结果。
     * @throws Throwable 如果执行过程中发生异常，则抛出。
     */
    @Override
    public Object proceed() throws Throwable {
        // 调用被拦截的方法，并传入参数，返回执行结果
        return method.invoke(target, arguments);
    }


    @Override
    public Object getThis() {
        return target;
    }

    /**
     * 获取类的静态部分。
     * 这个方法重写了AccessibleObject类的getStaticPart方法，用于返回代表类静态成员的AccessibleObject。
     *
     * @return AccessibleObject 返回表示静态成员的AccessibleObject。
     */
    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}
