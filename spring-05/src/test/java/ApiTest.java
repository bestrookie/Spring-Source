import com.bestrookie.springframework.PropertyValue;
import com.bestrookie.springframework.PropertyValues;
import com.bestrookie.springframework.factory.config.BeanDefinition;
import com.bestrookie.springframework.factory.config.BeanReference;
import com.bestrookie.springframework.factory.support.DefaultListableBeanFactory;
import org.junit.Test;

/**
 * @Author bestrookie
 * @Date 2023/11/29 16:52
 * @Desc
 */
public class ApiTest {

    @Test
    public void test_BeanFactory() throws Exception {
        //1. 初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //2.UserDao注册
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        //3.UserService设置属性【uId、userDao】
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId","10001"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

        //4、UserService注入bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        //5.UserService 获取bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();

    }
}
