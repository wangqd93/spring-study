package com.bycsmys.ioc.anno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author wangqd
 * @DATE 2019-03-03
 */
@Component
public class FXNewsProviderAnno {

    @Autowired
    private IFXNewsListenerAnno newsListener;

    @Autowired
    private IFXNewsPersisterAnno newsPersister;

    @Value("${sdb.long.renew.hand.all.open:test}")
    private String value;

    public String getValue() {
        return value;
    }

    public FXNewsProviderAnno() {
    }

    public FXNewsProviderAnno(IFXNewsListenerAnno newsListener, IFXNewsPersisterAnno newsPersister) {
        this.newsListener = newsListener;
        this.newsPersister = newsPersister;
    }

    public IFXNewsListenerAnno getNewsListener() {
        return newsListener;
    }

    public void setNewsListener(IFXNewsListenerAnno newsListener) {
        this.newsListener = newsListener;
    }

    public IFXNewsPersisterAnno getNewsPersister() {
        return newsPersister;
    }

    public void setNewsPersister(IFXNewsPersisterAnno newsPersister) {
        this.newsPersister = newsPersister;
    }

    public  void test(){
        System.out.println(this.newsListener.toString());
        System.out.println(this.newsListener.hashCode());

        System.out.println(this.newsPersister.toString());
        System.out.println(this.newsPersister.hashCode());
    }
}
