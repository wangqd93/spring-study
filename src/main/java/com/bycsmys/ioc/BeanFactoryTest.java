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
 *
 * @Author wangqd
 * @DATE 2019-03-02
 */
public class BeanFactoryTest {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        BeanFactory container = bindViaCode(beanFactory);

        FxNewsProvider newsProvider = (FxNewsProvider) container.getBean("djNewsProvider");
        newsProvider.test();
    }

    public static BeanFactory bindViaCode(BeanDefinitionRegistry registry) {
        AbstractBeanDefinition newProvider = new RootBeanDefinition(FxNewsProvider.class);

        AbstractBeanDefinition newsListener = new RootBeanDefinition(DowJonesNewListener.class);
        AbstractBeanDefinition newsPersister = new RootBeanDefinition(DowJonesNewsPersister.class);

        registry.registerBeanDefinition("djNewsProvider", newProvider);
        registry.registerBeanDefinition("djListener", newsListener);
        registry.registerBeanDefinition("djPersister", newsPersister);

        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(0, newsListener);
        argumentValues.addIndexedArgumentValue(1, newsPersister);

        newProvider.setConstructorArgumentValues(argumentValues);

        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("newListener", newsListener));
        propertyValues.addPropertyValue(new PropertyValue("newPersister", newsPersister));
        newProvider.setPropertyValues(propertyValues);
        return (BeanFactory) registry;

    }
}
