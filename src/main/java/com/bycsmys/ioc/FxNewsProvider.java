package com.bycsmys.ioc;

/**
 * @Author wangqd
 * @DATE 2019-03-02
 */
public class FxNewsProvider {

    private IFXNewsListener newsListener;
    private IFXNewsPersister newsPersister;

    public FxNewsProvider() {
    }

    public FxNewsProvider(IFXNewsListener newsListener, IFXNewsPersister newsPersister) {
        this.newsListener = newsListener;
        this.newsPersister = newsPersister;
    }

    public IFXNewsListener getNewsListener() {
        return newsListener;
    }

    public void setNewsListener(IFXNewsListener newsListener) {
        this.newsListener = newsListener;
    }

    public IFXNewsPersister getNewsPersister() {
        return newsPersister;
    }

    public void setNewsPersister(IFXNewsPersister newsPersister) {
        this.newsPersister = newsPersister;
    }

    public  void test(){
        System.out.println(this.newsListener.toString());
        System.out.println(this.newsListener.hashCode());

        System.out.println(this.newsPersister.toString());
        System.out.println(this.newsPersister.hashCode());
    }
}
