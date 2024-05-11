package com.bestrookie.springframework.springframework.aop.framework.autoproxy;

import com.bestrookie.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.bestrookie.springframework.aop.framework.ProxyFactory;
import com.bestrookie.springframework.beans.PropertyValues;
import com.bestrookie.springframework.beans.factory.BeanFactory;
import com.bestrookie.springframework.beans.factory.BeanFactoryAware;
import com.bestrookie.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.bestrookie.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.bestrookie.springframework.springframework.aop.AdvisedSupport;
import com.bestrookie.springframework.springframework.aop.Advisor;
import com.bestrookie.springframework.springframework.aop.TargetSource;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;

/**
 * @Author bestrookie
 * @Date 2024/3/19 16:13
 * @Desc
 */
public class DefaultAdvisorAutoCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;
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
        return bean;
    }

    @Override
    public Object postprocessBeforeInstantiation(Class<?> beanClass, String beanName) throws Exception {
        if (isInfrastructureClass(beanClass)){
            return null;
        }
        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeanOfType(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            if (!classFilter.matches(beanClass)){
                continue;
            }
            AdvisedSupport advisedSupport = new AdvisedSupport();
            TargetSource targetSource = null;
            try {
                targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
            }catch (Exception e){
                e.printStackTrace();
            }
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);
            return new ProxyFactory(advisedSupport).getProxy();
        }
        return null;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws Exception {
        return pvs;
    }
}
