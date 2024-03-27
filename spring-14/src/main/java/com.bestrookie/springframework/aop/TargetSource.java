package com.bestrookie.springframework.aop;

/**
 * @Author bestrookie
 * @Date 2024/3/18 15:13
 * @Desc
 */
public class TargetSource {
    private final Object target;
    public TargetSource(Object object){
        this.target = object;
    }

    public Class<?>[] getTargetClass(){
        return this.target.getClass().getInterfaces();
    }

    public Object getTarget() {
        return this.target;
    }
}
