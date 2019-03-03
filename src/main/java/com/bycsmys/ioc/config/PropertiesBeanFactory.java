package com.bycsmys.ioc.config;

import com.bycsmys.ioc.FxNewsProvider;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;

/**
 * @Author wangqd
 * @DATE 2019-03-03
 */
public class PropertiesBeanFactory {

    public static void main(String[] args) {
        BeanDefinitionRegistry beanDefinitionRegistry = new DefaultListableBeanFactory();

        BeanFactory container = bindViaPropertiessFile(beanDefinitionRegistry);
        FxNewsProvider newsProvider = (FxNewsProvider) container.getBean("djNewsProvider");
        newsProvider.test();

    }

    public static BeanFactory bindViaPropertiessFile(BeanDefinitionRegistry registry) {
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(registry);

        reader.loadBeanDefinitions("properties-config.properties");
        return (BeanFactory) registry;
    }


}
