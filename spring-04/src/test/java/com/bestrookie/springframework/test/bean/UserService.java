package com.bestrookie.springframework.test.bean;

/**
 * @Author bestrookie
 * @Date 2023/11/20 15:33
 * @Desc
 */
public class UserService {
    private String name;

    public UserService(String name) {
        this.name = name;
    }

    public void queryUserInfo(){
        System.out.println("查询用户信息");
    }
}
