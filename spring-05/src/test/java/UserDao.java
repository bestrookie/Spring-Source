import java.util.HashMap;
import java.util.Map;

/**
 * @Author bestrookie
 * @Date 2023/11/29 16:53
 * @Desc
 */
public class UserDao {
    private static Map<String, String> hasMap = new HashMap<>();
    static {
        hasMap.put("10001","嘎嘎");
        hasMap.put("10002","吆西");
        hasMap.put("10003","米西米西");
    }
    public String queryUserName(String uId){
        return hasMap.get(uId);
    }
}
