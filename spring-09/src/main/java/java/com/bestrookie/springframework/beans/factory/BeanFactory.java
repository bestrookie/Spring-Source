package java.com.bestrookie.springframework.beans.factory;

/**
 * @Author bestrookie
 * @Date 2023/11/20 14:01
 * @Desc
 */
public interface BeanFactory {
    /**
     * 获取bean
     * @param beanName beanName
     * @return bean
     */
    Object getBean(String beanName) throws Exception;

    /**
     * 获取bean 有参构造
     * @param name name
     * @param args 参数
     * @return bean
     */
    Object getBean(String name, Object... args) throws Exception;

    /**
     * 获取bean对象
     * @param name bean名称
     * @param requireType 类型
     * @param <T> 泛型
     * @throws Exception 异常
     */
    <T> T getBean(String name, Class<T> requireType) throws Exception;
}
