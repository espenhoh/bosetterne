package com.holtebu.bosetterne.service.helloworld.health;

import com.google.inject.Inject;
import com.holtebu.bosetterne.service.helloworld.HelloWorldConfiguration;
import com.yammer.metrics.core.HealthCheck;

public class TemplateHealthCheck extends HealthCheck {
    private final String template;

    @Inject
    public TemplateHealthCheck(HelloWorldConfiguration configuration) {
        super("template");
        this.template = configuration.getTemplate();
    }

    @Override
    protected Result check() throws Exception {
        final String saying = String.format(template, "TEST");
        if (!saying.contains("TEST")) {
            return Result.unhealthy("template doesn't include a name");
        }
        return Result.healthy();
    }
}