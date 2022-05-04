package com.bycsmys.ioc.guice;

import com.bycsmys.ioc.service.FxNewsProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceProvider {
    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new NewBindingModule());
        FxNewsProvider instance = injector.getInstance(FxNewsProvider.class);
        instance.test();


    }
}
