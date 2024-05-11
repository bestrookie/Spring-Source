package com.bestrookie.springframework.springframework.beans.factory;

import com.bestrookie.springframework.beans.PropertyValue;
import com.bestrookie.springframework.beans.PropertyValues;
import com.bestrookie.springframework.beans.factory.config.BeanDefinition;
import com.bestrookie.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.bestrookie.springframework.core.io.DefaultResourceLoader;
import com.bestrookie.springframework.core.io.Resource;
import com.bestrookie.springframework.util.StringValueResolver;

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

    /**
     * 在BeanFactory后处理期间调用，用于解析属性文件中的占位符并更新Bean定义的属性值。
     *
     * @param beanFactory 可配置的列表Bean工厂，允许访问和操作所有的Bean定义。
     * @throws Exception 如果处理过程中发生任何异常。
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws Exception {
        try {
            // 创建默认资源加载器，用于加载属性文件
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            // 根据给定的位置加载资源
            Resource resource = resourceLoader.getResource(location);

            // 加载属性文件内容到Properties对象
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            // 获取所有Bean定义的名称
            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

            // 遍历所有Bean定义
            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

                // 获取Bean定义的属性值
                PropertyValues propertyValues = beanDefinition.getPropertyValues();

                // 遍历所有属性值，解析并更新占位符
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    // 仅处理字符串类型的属性值
                    if (!(value instanceof String)){
                        continue;
                    }else {
                        // 解析字符串中的占位符，并更新属性值
                        value = resolvePlaceholder((String) value, properties);
                        // 将解析后的值添加到Bean定义的属性值中
                        propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), value));
                    }
                }
                PlaceholderResolvingValueResolver valueResolver = new PlaceholderResolvingValueResolver(properties);
                beanFactory.addEmbeddedValueResolver(valueResolver);
            }

        }catch (Exception e){
            // 打印异常堆栈信息
            e.printStackTrace();
        }

    }


    /**
     * 解析字符串中的占位符，并将其替换为相应属性值。
     *
     * @param value 包含占位符的字符串。
     * @param properties 包含属性键值对的集合，用于替换占位符。
     * @return 替换占位符后的字符串。
     */
    private String resolvePlaceholder(String value, Properties properties){
        String strVal = value;
        StringBuilder buffer = new StringBuilder(strVal);
        // 查找占位符的起始和结束位置
        int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        int stopIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
        // 如果找到完整的占位符（有起始和结束标志，并且起始在结束之前）
        if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx ){
            // 提取占位符代表的属性键
            String propKey = strVal.substring(startIdx + 2, stopIdx);
            // 获取属性键对应的属性值
            String propVal = properties.getProperty(propKey);
            // 使用属性值替换占位符
            buffer.replace(startIdx, stopIdx + 1, propVal);
        }
        return buffer.toString();
    }

    /**
     * 值解析器，用于解析字符串中的占位符。
     */
    private class PlaceholderResolvingValueResolver implements StringValueResolver{
        private final Properties properties;

        public PlaceholderResolvingValueResolver(Properties properties){
            this.properties = properties;
        }

        @Override
        public String resolveStringValue(String setVal) {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(setVal, properties);
        }
    }

}
