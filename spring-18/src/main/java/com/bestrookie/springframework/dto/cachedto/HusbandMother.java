package com.bestrookie.springframework.dto.cachedto;

import com.bestrookie.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @Author bestrookie
 * @Date 2024/6/5 14:51
 * @Desc
 */
public class HusbandMother implements FactoryBean<IMother> {
    @Override
    public IMother getObject() throws Exception {
        return (IMother) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IMother.class}, ((proxy, method, args) -> "婚后媳妇妈妈的职责被婆婆代理了" + method.getName()));
    }

    @Override
    public Class<?> getObjectType() {
        return IMother.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
