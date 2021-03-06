package com.holtebu.brettspill.service.health;

import com.holtebu.brettspill.service.BosetterneConfiguration;
import com.codahale.metrics.health.HealthCheck;

public class TemplateHealthCheck extends HealthCheck {
    private final String template;

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