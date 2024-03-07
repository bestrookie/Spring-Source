package java.com.bestrookie.springframework.context.support;

import com.bestrookie.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.bestrookie.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @Author bestrookie
 * @Date 2024/1/29 16:27
 * @Desc
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws Exception {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocation = getConfigLocation();
        if (null != configLocation){
            beanDefinitionReader.loadBeanDefinitions(configLocation);
        }
    }

    /**
     * 获取配置文件路径
     * @return 路径
     */
    protected abstract String[] getConfigLocation();
}
