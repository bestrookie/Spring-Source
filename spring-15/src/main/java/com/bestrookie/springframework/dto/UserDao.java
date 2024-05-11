package com.bestrookie.springframework.dto;

import com.bestrookie.springframework.context.annotation.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author bestrookie
 * @Date 2023/12/12 10:36
 * @Desc
 */
@Component
public class UserDao {
    private static Map<String, String> hasMap = new HashMap<>();
    static {
        hasMap.put("10001", "小傅哥，北京，亦庄");
        hasMap.put("10002", "八杯水，上海，尖沙咀");
        hasMap.put("10003", "阿毛，天津，东丽区");
    }


    public String queryUserName(String uId){
        return hasMap.get(uId);
    }

}
