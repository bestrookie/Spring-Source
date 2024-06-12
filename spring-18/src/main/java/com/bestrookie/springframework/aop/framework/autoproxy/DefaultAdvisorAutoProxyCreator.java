package com.bestrookie.springframework.aop.framework.autoproxy;

import com.bestrookie.springframework.aop.*;
import com.bestrookie.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.bestrookie.springframework.aop.framework.ProxyFactory;
import com.bestrookie.springframework.beans.PropertyValues;
import com.bestrookie.springframework.beans.factory.BeanFactory;
import com.bestrookie.springframework.beans.factory.BeanFactoryAware;
import com.bestrookie.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.bestrookie.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author bestrookie
 * @Date 2024/3/19 16:13
 * @Desc
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;

    private final Set<Object> earlyProxyReference = Collections.synchronizedSet(new HashSet<>());
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws Exception {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    /**
     * 判断给定的类是否为基础设施类。
     * 基础设施类包括：建议类(Advice)、切点类(Pointcut)和顾问类(Advisor)。
     *
     * @param beanClass 待判断的类对象。
     * @return 如果给定的类是基础设施类，则返回true；否则返回false。
     */
    private boolean isInfrastructureClass(Class<?> beanClass){
        // 判断beanClass是否为Advice、Pointcut或Advisor的子类或实现类
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
       if (!earlyProxyReference.contains(bean)){
           return wrapIfNecessary(bean, beanName);
       }
        return bean;
    }

    @Override
    public Object postprocessBeforeInstantiation(Class<?> beanClass, String beanName) throws Exception {
        return null;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws Exception {
        return pvs;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws Exception {
        return true;
    }

    protected Object wrapIfNecessary(Object bean, String beanName) throws Exception {
        if (isInfrastructureClass(bean.getClass())) return bean;

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeanOfType(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            // 过滤匹配类
            if (!classFilter.matches(bean.getClass())) continue;

            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(bean);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(true);

            // 返回代理对象
            return new ProxyFactory(advisedSupport).getProxy();
        }

        return bean;
    }
    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws Exception {
        earlyProxyReference.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }
}
