import com.bestrookie.springframework.aop.AdvisedSupport;
import com.bestrookie.springframework.aop.TargetSource;
import com.bestrookie.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.bestrookie.springframework.aop.framework.Cglib2AopProxy;
import com.bestrookie.springframework.aop.framework.JdkDynamicAopProxy;
import com.bestrookie.springframework.dto.IUserService;
import com.bestrookie.springframework.dto.UserService;
import interceptor.UserServiceInterceptor;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @Author bestrookie
 * @Date 2024/3/18 14:47
 * @Desc
 */
public class ApiTest {
    @Test
    public void test_aop() throws Exception{
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.bestrookie.springframework.dto.UserService.*(..))");
        Class<UserService> clazz = UserService.class;

        Method method = clazz.getDeclaredMethod("queryUserInfo");
        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));

    }

    @Test
    public void test_dynamic(){
        IUserService userService = new UserService();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* com.bestrookie.springframework.dto.IUserService.*(..))"));

        //代理对象 jdk
        IUserService proxy_jdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getPoxy();

        System.out.println("测试结果： "+ proxy_jdk.queryUserInfo());

        //代理对象 cglib
        IUserService poxy = (IUserService) new Cglib2AopProxy(advisedSupport).getPoxy();
        System.out.println("测试结果： "+ poxy.register("花花"));

    }
}
