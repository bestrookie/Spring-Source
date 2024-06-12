import com.bestrookie.springframework.context.support.ClassPathXmlApplicationContext;
import com.bestrookie.springframework.core.convert.converter.Converter;
import com.bestrookie.springframework.core.convert.converter.support.StringToNumberConverterFactory;
import com.bestrookie.springframework.dto.cachedto.Husband;
import com.bestrookie.springframework.dto.converter.StringToIntegerConverter;
import org.junit.Test;

/**
 * @Author bestrookie
 * @Date 2024/6/12 15:57
 * @Desc
 */
public class ApiTest {
    @Test
    public void test_convert() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        Husband husband = applicationContext.getBean("husband", Husband.class);
        System.out.println("测试结果 :" + husband);
    }

    @Test
    public void test_StringToIntegerConverter() {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer num = converter.conver("1234");
        System.out.println("测试结果：" + num);
    }

    @Test
    public void test_StringToNumberConverterFactory() {
        StringToNumberConverterFactory converterFactory = new StringToNumberConverterFactory();

        Converter<String, Integer> stringToIntegerConverter = converterFactory.getConverter(Integer.class);
        System.out.println("测试结果：" + stringToIntegerConverter.conver("1234"));

        Converter<String, Long> stringToLongConverter = converterFactory.getConverter(Long.class);
        System.out.println("测试结果：" + stringToLongConverter.conver("1234"));
    }
}
