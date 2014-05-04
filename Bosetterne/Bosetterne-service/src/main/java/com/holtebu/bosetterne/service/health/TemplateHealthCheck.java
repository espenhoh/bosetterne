package com.holtebu.bosetterne.service.health;

import com.google.inject.Inject;
import com.holtebu.bosetterne.service.BosetterneConfiguration;
import com.codahale.metrics.health.HealthCheck;

public class TemplateHealthCheck extends HealthCheck {
    private final String template;

    @Inject
    public TemplateHealthCheck(BosetterneConfiguration configuration) {
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