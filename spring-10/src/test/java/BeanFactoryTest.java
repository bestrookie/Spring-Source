import com.bestrookie.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.bestrookie.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.bestrookie.springframework.common.MyBeanFactoryPostProcessor;
import com.bestrookie.springframework.common.MyBeanPostProcessor;
import com.bestrookie.springframework.context.support.ClassPathXmlApplicationContext;
import com.bestrookie.springframework.dto.UserService;
import org.junit.Test;

/**
 * @Author bestrookie
 * @Date 2024/3/5 10:47
 * @Desc
 */
public class BeanFactoryTest {
    //不应用上下文
    @Test
    public void test1() throws Exception {
        //1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //2.读取配置文件注册 Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        //3.BeanDefinition 加载完成 Bean 实例化之前，修改 BeanDefinition 的属性值
        MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        //4.Bean实例化之后，修改 Bean 属性
        MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(beanPostProcessor);

        //5,获取 Bean对象调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果:" + result);

    }

    //使用上下文
    @Test
    public void test2() throws Exception {
        //1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springPostProcessor.xml");

        //2.获取 Bean 对象调用方法
        UserService userService = applicationContext.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果: " + result);
    }

    @Test
    public void test3() throws Exception {
       //1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();
        //2.获取 Bean 对象调用方法
        UserService userService01 = applicationContext.getBean("userService", UserService.class);
        UserService userService02 = applicationContext.getBean("userService", UserService.class);

        //3.配置 scope
        System.out.println(userService01);
        System.out.println(userService02);
    }
    @Test
    public void test4() throws Exception {
        //1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        UserService userService = applicationContext.getBean("userService", UserService.class);
        System.out.println("测试结果:" + userService.queryUserInfo());
    }
}
