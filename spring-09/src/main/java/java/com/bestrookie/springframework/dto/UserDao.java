package java.com.bestrookie.springframework.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author bestrookie
 * @Date 2023/12/12 10:36
 * @Desc
 */
public class UserDao {
    private static Map<String, String> hasMap = new HashMap<>();

    public void initDataMethod(){
        System.out.println("执行：init-method");
        hasMap.put("10001", "小傅哥");
        hasMap.put("10002", "八杯水");
        hasMap.put("10003", "阿毛");
    }

    public String queryUserName(String uId){
        return hasMap.get(uId);
    }

    public void destroyDataMethod(){
        System.out.println("执行：destroy-method");
        hasMap.clear();
    }
}
