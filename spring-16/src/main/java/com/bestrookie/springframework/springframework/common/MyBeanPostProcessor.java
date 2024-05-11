package com.bestrookie.springframework.springframework.common;//package com.bestrookie.springframework.common;
//
//import com.bestrookie.springframework.beans.factory.config.BeanPostProcessor;
//import com.bestrookie.springframework.dto.UserService;
//
///**
// * @Author bestrookie
// * @Date 2024/3/5 10:07
// * @Desc
// */
//public class MyBeanPostProcessor implements BeanPostProcessor {
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
//        if ("userService".equals(beanName)){
//            UserService userService = (UserService) bean;
//            userService.setLocation("改为北京");
//        }
//        return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
//        return bean;
//    }
//}
