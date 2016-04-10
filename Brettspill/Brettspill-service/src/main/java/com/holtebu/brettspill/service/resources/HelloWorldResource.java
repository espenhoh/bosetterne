package com.holtebu.brettspill.service.resources;

import com.holtebu.brettspill.api.components.CubeHexCoordinates;
import com.holtebu.brettspill.api.components.Hex;
import com.holtebu.brettspill.api.components.HexCoordinates;
import com.holtebu.brettspill.service.BosetterneConfiguration;

import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jvnet.hk2.annotations.Service;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
@Service
@Singleton
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

//    @GET
//    @Timed
//    public Saying sayHello() {
//        return sayHello(defaultName);
//    }
//
//    @GET
//    @Timed
//    public Saying sayHello(@QueryParam("name") String name) {
//        return new Saying(counter.incrementAndGet(),
//                          String.format(template, name));
//    }

    @GET
    @Path("/hex")
    public Hex sayHex() {
        CubeHexCoordinates coordinates = new CubeHexCoordinates(1,2,3);
        return new Hex(coordinates);
    }

}
