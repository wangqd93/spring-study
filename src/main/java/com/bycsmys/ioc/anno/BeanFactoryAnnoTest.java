package com.bycsmys.ioc.anno;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author wangqd
 * @DATE 2019-03-03
 */
public class BeanFactoryAnnoTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("anno-config.xml");
        FXNewsProviderAnno newsProviderAnno = (FXNewsProviderAnno) applicationContext.getBean("FXNewsProviderAnno");
        newsProviderAnno.test();

        System.out.println("newsProviderAnno.getValue() = " + newsProviderAnno.getValue());
    }
}
