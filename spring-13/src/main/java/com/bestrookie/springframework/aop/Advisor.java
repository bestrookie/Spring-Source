package com.bestrookie.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * @Author bestrookie
 * @Date 2024/3/19 15:23
 * @Desc
 */
public interface Advisor {
    /**
     * 获取这个切面的建议部分。一个建议可以是一个拦截器、一个前置建议、一个异常抛出建议等。
     * 如果切入点匹配，该建议应当被应用。
     * @return 如果切入点匹配，返回应当应用的建议。
     * @see org.aopalliance.intercept.MethodInterceptor - 代表方法拦截器的接口。
     * @see BeforeAdvice - 代表前置建议的接口。
     */
    Advice getAdvice();
}
