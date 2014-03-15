package com.holtebu.bosetterne.service.bosetterne.resources;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.holtebu.bosetterne.api.helloworld.Saying;
import com.holtebu.bosetterne.service.bosetterne.BosetterneConfiguration;
import com.yammer.metrics.annotation.Timed;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
	private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    @Inject
    public HelloWorldResource(BosetterneConfiguration configuration, AtomicLong counter) {
        this.template = configuration.getTemplate();
        this.defaultName = configuration.getDefaultName();
        this.counter = counter;
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        return new Saying(counter.incrementAndGet(),
                          String.format(template, name.or(defaultName)));
    }
}
