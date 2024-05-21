import com.bestrookie.springframework.context.support.ClassPathXmlApplicationContext;
import com.bestrookie.springframework.dto.IUserService;
import org.junit.Test;

/**
 * @Author bestrookie
 * @Date 2024/5/13 16:44
 * @Desc
 */
public class ApiTest {
    @Test
    public void apiTest() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.register("123"));
    }
}
