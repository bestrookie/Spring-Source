import lombok.Data;

/**
 * @Author bestrookie
 * @Date 2023/11/30 09:21
 * @Desc
 */
@Data
public class UserService {
    private String uId;

    private UserDao userDao;

    public void queryUserInfo(){
        System.out.println("查询用户信息:" + userDao.queryUserName(uId));
    }
}
