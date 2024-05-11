package com.bestrookie.springframework.springframework.aop.framework;

import com.bestrookie.springframework.aop.AdvisedSupport;

/**
 * @Author bestrookie
 * @Date 2024/3/19 16:02
 * @Desc
 */
public class ProxyFactory {
    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport){
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy(){
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy(){
        if (advisedSupport.isProxyTargetClass()){
            return new Cglib2AopProxy(advisedSupport);
        }else {
            return new JdkDynamicAopProxy(advisedSupport);
        }
    }
}
