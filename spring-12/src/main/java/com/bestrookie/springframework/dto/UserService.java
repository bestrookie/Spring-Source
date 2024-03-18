package com.bestrookie.springframework.dto;


import java.util.Random;

/**
 * @Author bestrookie
 * @Date 2023/12/12 10:41
 * @Desc
 */
public class UserService implements IUserService{
    @Override
    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "bestrookie, 1000001, 青岛";
    }

    @Override
    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "注册用户： " + userName + "success";
    }

    @Override
    public String helloWorld() {
        return "不走监控";
    }
}
