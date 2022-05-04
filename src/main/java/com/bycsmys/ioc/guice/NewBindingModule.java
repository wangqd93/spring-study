package com.bycsmys.ioc.guice;

import com.bycsmys.ioc.service.DowJonesNewListener;
import com.bycsmys.ioc.service.DowJonesNewsPersister;
import com.bycsmys.ioc.service.IFXNewsListener;
import com.bycsmys.ioc.service.IFXNewsPersister;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class NewBindingModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IFXNewsListener.class).to(DowJonesNewListener.class).in(Scopes.SINGLETON);

        bind(IFXNewsPersister.class).to(DowJonesNewsPersister.class).in(Scopes.SINGLETON);
    }
}
