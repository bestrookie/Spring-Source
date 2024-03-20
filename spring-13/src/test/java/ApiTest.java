import com.bestrookie.springframework.aop.AdvisedSupport;
import com.bestrookie.springframework.aop.ClassFilter;
import com.bestrookie.springframework.aop.TargetSource;
import com.bestrookie.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.bestrookie.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.bestrookie.springframework.aop.framework.ProxyFactory;
import com.bestrookie.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import com.bestrookie.springframework.context.support.ClassPathXmlApplicationContext;
import com.bestrookie.springframework.dto.IUserService;
import com.bestrookie.springframework.dto.UserServiceBeforeAdvice;
import com.bestrookie.springframework.dto.UserServiceImpl;
import com.bestrookie.springframework.dto.UserServiceInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author bestrookie
 * @Date 2024/3/19 17:05
 * @Desc
 */
public class ApiTest {

    private AdvisedSupport advisedSupport;

    @Before
    public void init() {
        // 目标对象
        IUserService userService = new UserServiceImpl();
        // 组装代理信息
        advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* com.bestrookie.springframework.dto.IUserService.*(..))"));
    }


    @Test
    public void test() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }

    @Test
    public void test_advisor() {
        // 目标对象
        IUserService userService = new UserServiceImpl();

        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression("execution(* com.bestrookie.springframework.dto.IUserService.*(..))");
        advisor.setAdvice(new MethodBeforeAdviceInterceptor(new UserServiceBeforeAdvice()));

        ClassFilter classFilter = advisor.getPointcut().getClassFilter();
        if (classFilter.matches(userService.getClass())) {
            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(userService);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(true); // false/true，JDK动态代理、CGlib动态代理

            IUserService proxy = (IUserService) new ProxyFactory(advisedSupport).getProxy();
            System.out.println("测试结果：" + proxy.queryUserInfo());
        }
    }

    @Test
    public void test_beforeAdvice() {
        UserServiceBeforeAdvice beforeAdvice = new UserServiceBeforeAdvice();
        MethodBeforeAdviceInterceptor interceptor = new MethodBeforeAdviceInterceptor(beforeAdvice);
        advisedSupport.setMethodInterceptor(interceptor);

        IUserService proxy = (IUserService) new ProxyFactory(advisedSupport).getProxy();
        System.out.println("测试结果：" + proxy.queryUserInfo());
    }

}
