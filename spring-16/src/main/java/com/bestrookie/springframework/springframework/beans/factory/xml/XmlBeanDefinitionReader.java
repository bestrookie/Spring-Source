package com.bestrookie.springframework.springframework.beans.factory.xml;


import cn.hutool.core.util.StrUtil;
import com.bestrookie.springframework.beans.PropertyValue;
import com.bestrookie.springframework.beans.factory.config.BeanDefinition;
import com.bestrookie.springframework.beans.factory.config.BeanReference;
import com.bestrookie.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.bestrookie.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bestrookie.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import com.bestrookie.springframework.core.io.Resource;
import com.bestrookie.springframework.core.io.ResourceLoader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @Author bestrookie
 * @Date 2023/12/7 16:24
 * @Desc xml读取实现类
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader){
        super(registry, resourceLoader);
    }
    @Override
    public void loadBeanDefinitions(Resource resource) throws Exception {
        try {
            try (InputStream inputStream = resource.getInputStream()) {
                doLoadBeanDefinitions(inputStream);
            }
        }catch (Exception e){
            throw new Exception("IOException parsing XML document form" + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws Exception {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws Exception {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);

    }

    @Override
    public void loadBeanDefinitions(String... location) throws Exception {
        for (String s : location) {
            loadBeanDefinitions(s);
        }
    }

    /**
     * 从输入流中加载Bean定义。
     * @param inputStream 输入流，包含Bean的配置信息。
     * @throws Exception 如果配置有误或加载过程中发生错误，则抛出异常。
     */
    protected void doLoadBeanDefinitions(InputStream inputStream) throws Exception {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(inputStream);

        Element root = doc.getRootElement();

        // 检查是否存在"comment-scan"元素，并获取其"base-package"属性值
        Element commentScan = root.element("component-scan");

        if (null != commentScan){
            String scanPath = commentScan.attributeValue("base-package");
            // 如果"base-package"属性为空或不存在，则抛出异常
            if (StrUtil.isEmpty(scanPath)){
                throw new Exception("the value of base-package attribute can not empty or null");
            }else {
                scanPackage(scanPath);
            }
        }

        // 获取所有"bean"元素，并遍历它们
        List<Element> beanList = root.elements("bean");
        for (Element bean : beanList) {

            // 获取bean的属性值：id、name、class等
            String id = bean.attributeValue("id");
            String name = bean.attributeValue("name");
            String className = bean.attributeValue("class");
            String initMethod = bean.attributeValue("init-method");
            String destroyMethodName = bean.attributeValue("destroy-method");
            String beanScope = bean.attributeValue("scope");


            Class<?> clazz = Class.forName(className);
            // 根据id和name确定bean的名称，优先使用id
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)){
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 根据类名创建BeanDefinition，并设置初始化和销毁方法名

            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethodName);

            // 如果指定了作用域，则设置Bean的作用域
            if (StrUtil.isNotEmpty(beanScope)){
                beanDefinition.setScope(beanScope);
            }

            // 遍历并处理"property"元素，填充Bean的属性值
            List<Element> propetryList = bean.elements("property");
            for (Element property : propetryList) {

                String attrName = property.attributeValue("name");

                String attrValue = property.attributeValue("value");

                String attrRef = property.attributeValue("ref");

                // 获取属性的实际值，可以是直接的值或对其他bean的引用
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;

                // 创建属性信息并添加到BeanDefinition中
                PropertyValue propertyValues = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValues);

            }

            // 检查是否已存在同名的bean定义，若已存在，则抛出异常
            if (getRegistry().containsBeanDefinition(beanName)){
                throw new Exception("Duplicate beanName[" + beanName + "] is not allowed");
            }

            // 注册BeanDefinition
            getRegistry().registerBeanDefinition(beanName, beanDefinition);

        }
    }


    /**
     * 扫描指定路径下的包，以注册为Spring Bean。
     * @param scanPath 需要扫描的包路径，多个路径用逗号分隔。
     */
    private void scanPackage(String scanPath){
        // 将扫描路径根据逗号分割为多个基础包名
        String[] basePackages = StrUtil.split(scanPath, ",");
        // 创建ClassPathBeanDefinitionScanner实例，用于扫描并注册Bean
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        // 执行扫描，将basePackages作为扫描范围
        scanner.doScan(basePackages);
    }

}
