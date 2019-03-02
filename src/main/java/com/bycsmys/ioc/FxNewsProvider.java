package com.bycsmys.ioc;

/**
 * @Author wangqd
 * @DATE 2019-03-02
 */
public class FxNewsProvider {

    private IFXNewsListener newListener;
    private IFXNewsPersister newPersister;

    public FxNewsProvider() {
    }

    public FxNewsProvider(IFXNewsListener newListener, IFXNewsPersister newPersister) {
        this.newListener = newListener;
        this.newPersister = newPersister;
    }

    public IFXNewsListener getNewListener() {
        return newListener;
    }

    public void setNewListener(IFXNewsListener newListener) {
        this.newListener = newListener;
    }

    public IFXNewsPersister getNewPersister() {
        return newPersister;
    }

    public void setNewPersister(IFXNewsPersister newPersister) {
        this.newPersister = newPersister;
    }

    public  void test(){
        System.out.println(this.newListener.toString());
        System.out.println(this.newListener.hashCode());

        System.out.println(this.newPersister.toString());
        System.out.println(this.newPersister.hashCode());
    }
}
