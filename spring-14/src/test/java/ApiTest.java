import com.bestrookie.springframework.context.support.ClassPathXmlApplicationContext;
import com.bestrookie.springframework.dto.IUserService;
import org.junit.Test;

/**
 * @Author bestrookie
 * @Date 2024/4/2 14:48
 * @Desc
 */
public class ApiTest {
    @Test
    public void test_property() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-property.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService);
        System.out.println(userService.queryUserInfo());
    }

    @Test
    public void test_scan() throws Exception{
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-scan.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果:" + userService.queryUserInfo());
    }
}
