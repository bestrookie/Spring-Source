import com.bestrookie.springframework.context.support.ClassPathXmlApplicationContext;
import com.bestrookie.springframework.dto.CustomEvent;
import org.junit.Test;

/**
 * @Author bestrookie
 * @Date 2024/3/14 16:10
 * @Desc
 */
public class ApiTest {
    @Test
    public void test() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 123456L, "吆西!"));
        applicationContext.registerShutdownHook();
    }
}
