package java.com.bestrookie.springframework.context.support;

/**
 * @Author bestrookie
 * @Date 2024/1/30 09:35
 * @Desc 应用上下文实现类
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{
    private String[] configLocations;

    public ClassPathXmlApplicationContext(){

    }

    /**
     * 从 xml 中加载 BeanDefinition，并刷新上下文
     * @param configLocations configLocations
     * @throws Exception exception
     */

    public ClassPathXmlApplicationContext(String configLocations) throws Exception {
        this(new String[]{configLocations});
    }

    /**
     * 从 xml 中加载 BeanDefinition，并刷新上下文
     * @param configLocations configLocations
     * @throws Exception exception
     */
    public ClassPathXmlApplicationContext(String[] configLocations) throws Exception {
        this.configLocations = configLocations;
        refresh();
    }
    @Override
    protected String[] getConfigLocation() {
        return configLocations;
    }
}
