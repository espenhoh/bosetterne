package com.holtebu.bosetterne.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

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
}
