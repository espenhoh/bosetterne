package com.holtebu.brettspill.service.inject;

import com.holtebu.brettspill.service.BosetterneService;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.jdbi.DBIFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

/**
 * Created by Espen on 27.03.2016.
 */
public class StartupBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bindClasses();
        bindConst();
    }

    private void bindConst() {
    }

    private void bindClasses() {
        bind(BosetterneServiceBinder.class).to(BosetterneServiceBinder.class);
        bind(DBIFactory.class).to(DBIFactory.class);
        bind(BosetterneService.class).to(BosetterneService.class);
    }

}
