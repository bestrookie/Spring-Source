import com.bestrookie.springframework.context.support.ClassPathXmlApplicationContext;
import com.bestrookie.springframework.dto.cachedto.Husband;
import com.bestrookie.springframework.dto.cachedto.Wife;
import org.junit.Test;

/**
 * @Author bestrookie
 * @Date 2024/6/5 15:56
 * @Desc
 */
public class ApiTest {
    @Test
    public void test_circular() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        Husband husband = applicationContext.getBean("husband", Husband.class);
        Wife wife = applicationContext.getBean("wife", Wife.class);
        System.out.println("老公的媳妇：" + husband.queryWife());
        System.out.println("媳妇的老公: " + wife.queryHusband());
    }
}
