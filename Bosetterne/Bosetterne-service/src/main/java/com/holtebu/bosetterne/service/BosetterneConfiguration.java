package com.holtebu.bosetterne.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import java.util.Collections;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

public class BosetterneConfiguration extends Configuration {
    @NotEmpty
    @JsonProperty
    private String template;

    @NotEmpty
    @JsonProperty
    private String defaultName = "Stranger";
    
    @Valid
    @NotNull
    @JsonProperty
    private OAuth2Cred oauth2 = new OAuth2Cred();
    
    @Valid
    @NotNull
    @JsonProperty
    private MustacheTemplates mustacheTemplates = new MustacheTemplates();


	@Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    @NotNull
    private Map<String, Map<String, String>> viewRendererConfiguration = Collections.emptyMap();
	
    public OAuth2Cred getOauth2() {
		return oauth2;
	}

    public MustacheTemplates getMustacheTemplates() {
		return mustacheTemplates;
	}
    
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public String getTemplate() {
        return template;
    }

    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty("viewRendererConfiguration")
    public Map<String, Map<String, String>> getViewRendererConfiguration() {
        return viewRendererConfiguration;
    }

/*
    @JsonProperty("viewRendererConfiguration")
    public void setViewRendererConfiguration(Map<String, Map<String, String>> viewRendererConfiguration) {
        ImmutableMap.Builder<String, Map<String, String>> builder = ImmutableMap.builder();
        for (Map.Entry<String, Map<String, String>> entry : viewRendererConfiguration.entrySet()) {
            builder.put(entry.getKey(), ImmutableMap.copyOf(entry.getValue()));
        }
        this.viewRendererConfiguration = builder.build();
    }*/
}
