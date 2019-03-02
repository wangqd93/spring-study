package com.bycsmys.ioc;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * 手动ioc注入
 * from spring 揭秘 page 24
 *
 * @Author wangqd
 * @DATE 2019-03-02
 */
public class BeanFactoryTest {

    public static void main(String[] args) {
        DefaultListableBeanFactory registry = new DefaultListableBeanFactory();

        BeanFactory container = bindViaCode(registry);

        FxNewsProvider newsProvider = (FxNewsProvider) container.getBean("djNewsProvider");
        newsProvider.test();
    }

    public static BeanFactory bindViaCode(BeanDefinitionRegistry registry) {
        AbstractBeanDefinition newProvider = new RootBeanDefinition(FxNewsProvider.class);

        AbstractBeanDefinition newsListener = new RootBeanDefinition(DowJonesNewListener.class);
        AbstractBeanDefinition newsPersister = new RootBeanDefinition(DowJonesNewsPersister.class);

        //将bean对象注册到ioc容器
        registry.registerBeanDefinition("djNewsProvider", newProvider);
        registry.registerBeanDefinition("djListener", newsListener);
        registry.registerBeanDefinition("djPersister", newsPersister);

        //构造方法注入
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(0, newsListener);
        argumentValues.addIndexedArgumentValue(1, newsPersister);

        newProvider.setConstructorArgumentValues(argumentValues);

        //属性注入
//        MutablePropertyValues propertyValues = new MutablePropertyValues();
//        propertyValues.addPropertyValue(new PropertyValue("newListener", newsListener));
//        propertyValues.addPropertyValue(new PropertyValue("newPersister", newsPersister));
//        newProvider.setPropertyValues(propertyValues);
        return (BeanFactory) registry;

    }
}
