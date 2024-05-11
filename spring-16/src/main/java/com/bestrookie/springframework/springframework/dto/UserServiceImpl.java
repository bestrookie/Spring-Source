package com.bestrookie.springframework.springframework.dto;


import java.util.Random;

/**
 * @Author bestrookie
 * @Date 2023/12/12 10:41
 * @Desc
 */
public class UserServiceImpl implements IUserService {
    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "小傅哥，100001，深圳";
    }

    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "注册用户：" + userName + " success！";
    }
}
