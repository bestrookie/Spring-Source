package com.bestrookie.springframework.springframework.dto;

/**
 * @Author bestrookie
 * @Date 2024/3/18 14:52
 * @Desc
 */
public interface IUserService {
    String queryUserInfo();

    String register(String userName);
}
