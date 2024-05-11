import com.bestrookie.springframework.context.support.ClassPathXmlApplicationContext;
import com.bestrookie.springframework.dto.IUserService;
import org.junit.Test;

/**
 * @Author bestrookie
 * @Date 2024/5/11 10:57
 * @Desc
 */
public class Test1 {
    @Test
    public void test_scan() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果: "+ userService.queryUserInfo());
    }
}
