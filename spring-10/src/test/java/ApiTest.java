

import cn.hutool.core.io.IoUtil;
import com.bestrookie.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.bestrookie.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.bestrookie.springframework.core.io.DefaultResourceLoader;
import com.bestrookie.springframework.core.io.Resource;
import com.bestrookie.springframework.dto.UserService;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * @Author bestrookie
 * @Date 2023/12/13 10:14
 * @Desc
 */
public class ApiTest {
    private DefaultResourceLoader resourceLoader;


    @Before
    public void init(){
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void test_classpath() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_file() throws Exception {
        Resource resource = resourceLoader.getResource("src/main/resources/important.properties");

        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void text_xml() throws Exception {
        //1.初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //2.读取配置文件注册Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        //3.获取Bean对象调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果: " + result);
    }
}
