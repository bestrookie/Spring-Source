package com.bestrookie.springframework.dto;

import com.bestrookie.springframework.beans.factory.BeanClassLoaderAware;
import com.bestrookie.springframework.beans.factory.BeanNameAware;
import com.bestrookie.springframework.context.ApplicationContext;
import com.bestrookie.springframework.context.ApplicationContextAware;
import lombok.Data;
import com.bestrookie.springframework.beans.factory.BeanFactory;
import com.bestrookie.springframework.beans.factory.BeanFactoryAware;

/**
 * @Author bestrookie
 * @Date 2023/12/12 10:41
 * @Desc
 */
@Data
public class UserService implements BeanNameAware, ApplicationContextAware, BeanFactoryAware, BeanClassLoaderAware {
    private String uId;
    private UserDao userDao;
    private String location;
    private String company;

    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;



    public String queryUserInfo(){
        return userDao.queryUserName(uId) + ","+ company + "," + location;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws Exception {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("bean name is:" + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws Exception {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) throws Exception {
        System.out.println("ClassLoader: + " + classLoader);
    }
}
