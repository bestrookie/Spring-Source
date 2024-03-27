package com.bestrookie.springframework.aop;

/**
 * @Author bestrookie
 * @Date 2024/3/19 15:25
 * @Desc
 */
public interface PointcutAdvisor extends Advisor{
    /**
     * 获取切面的切点。
     *
     * @return 返回一个Pointcut对象，该对象定义了切面要应用到的代码的特定部分。
     */
    Pointcut getPointcut();
}
