package com.bestrookie.springframework.beans.factory;

import com.bestrookie.springframework.beans.PropertyValue;
import com.bestrookie.springframework.beans.PropertyValues;
import com.bestrookie.springframework.beans.factory.config.BeanDefinition;
import com.bestrookie.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.bestrookie.springframework.core.io.DefaultResourceLoader;
import com.bestrookie.springframework.core.io.Resource;

import java.util.Properties;

/**
 * @Author bestrookie
 * @Date 2024/3/28 14:43
 * @Desc
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;


    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws Exception {
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    if (!(value instanceof String)){
                        continue;
                    }
                    String strValue = (String) value;
                    StringBuilder buffer = new StringBuilder(strValue);
                    int startIdx = strValue.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                    int stopIdx = strValue.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                    if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx){
                        String propKey = strValue.substring(startIdx + 2, stopIdx);
                        String propValue = properties.getProperty(propKey);
                        buffer.replace(startIdx, stopIdx + 1, propValue);
                        propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), buffer.toString()));
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
