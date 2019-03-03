package com.bycsmys.ioc.config;

import com.bycsmys.ioc.FxNewsProvider;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @Author wangqd
 * @DATE 2019-03-03
 */
public class XmlBeanFactory {

    public static void main(String[] args) {
        DefaultListableBeanFactory registry = new DefaultListableBeanFactory();
        BeanFactory container = bindViaXmlFile(registry);

        FxNewsProvider provider = (FxNewsProvider) container.getBean("djNewsProvider");
        provider.test();
    }

    public static BeanFactory bindViaXmlFile(BeanDefinitionRegistry registry) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
        reader.loadBeanDefinitions("xml-config.xml");
        return (BeanFactory) registry;
    }
}
