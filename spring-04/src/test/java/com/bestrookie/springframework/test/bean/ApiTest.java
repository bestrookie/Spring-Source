package com.bestrookie.springframework.test.bean;

import com.bestrookie.springframework.factory.config.BeanDefinition;
import com.bestrookie.springframework.factory.BeanFactory;
import com.bestrookie.springframework.factory.support.DefaultListableBeanFactory;
import org.junit.Test;

/**
 * @Author bestrookie
 * @Date 2023/11/20 15:36
 * @Desc
 */
public class ApiTest {

    @Test
    public void test_BeanFactory() throws Exception {
        //1.初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //2.注册bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        //3.第一次获取bean
        UserService userService = (UserService)beanFactory.getBean("userService","jiejiej");
        userService.queryUserInfo();
        UserService userService_singleton = (UserService) beanFactory.getBean("userService","jiejiej");
        userService_singleton.queryUserInfo();
    }
}
