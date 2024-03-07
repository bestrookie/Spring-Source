package java.com.bestrookie.springframework.beans.factory;

/**
 * @Author bestrookie
 * @Date 2024/3/6 14:58
 * @Desc 初始化方法
 */
public interface InitializingBean {
    /**
     * Bean 处理了属性填充后调用
     *
     * @throws Exception exception
     */
    void afterPropertiesSet() throws Exception;
}
