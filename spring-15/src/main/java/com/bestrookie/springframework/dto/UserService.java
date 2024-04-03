package com.bestrookie.springframework.dto;

import com.bestrookie.springframework.context.annotation.Component;

import java.util.Random;

/**
 * @Author bestrookie
 * @Date 2024/4/2 14:32
 * @Desc
 */
@Component("userService")
public class UserService implements IUserService{
    private String token;
    @Override
    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "bestrookie 100001  wu hu";
    }

    @Override
    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "注册用户：" + userName + " success!";
    }

    @Override
    public String toString() {
        return "UserService{" +
                "token='" + token + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
