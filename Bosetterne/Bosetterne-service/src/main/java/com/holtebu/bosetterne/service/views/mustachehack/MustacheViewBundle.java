package com.holtebu.bosetterne.service.views.mustachehack;

import com.google.common.collect.ImmutableSet;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewConfigurable;
import io.dropwizard.views.ViewMessageBodyWriter;
import io.dropwizard.views.ViewRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.ServiceLoader;

import static com.google.common.base.MoreObjects.firstNonNull;

/**
 * Created by espen on 3/14/15.
 */
public abstract class MustacheViewBundle<T extends Configuration> implements ConfiguredBundle<T>, ViewConfigurable<T> {
    private final MustacheHackRenderer hackRenderer;

    public MustacheViewBundle() {
        this(new MustacheHackRenderer());
    }

    public MustacheViewBundle(MustacheHackRenderer hackRenderer) {
        this.hackRenderer = hackRenderer;
    }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        //Map<String, Map<String, String>> options = getViewConfiguration(configuration);
        //Map<String, String> viewOptions = options.get(hackRenderer.getSuffix());
        //hackRenderer.configure(firstNonNull(viewOptions, Collections.<String, String>emptyMap()));
        ArrayList<ViewRenderer> renderers = new ArrayList<>(1);
        renderers.add(hackRenderer);
        environment.jersey().register(new ViewMessageBodyWriter(environment.metrics(), renderers));
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        // nothing doing
    }
}
