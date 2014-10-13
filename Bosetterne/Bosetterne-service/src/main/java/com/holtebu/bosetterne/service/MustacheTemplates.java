package com.holtebu.bosetterne.service;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MustacheTemplates {
	
    @NotEmpty
    @JsonProperty
    private String baseMappe;
    
    @NotEmpty
    @JsonProperty
    private String hjemTemplate;
    
    @NotEmpty
    @JsonProperty
    private String loginTemplate;
    
    @NotEmpty
    @JsonProperty
    private String bosetterneTemplate;

	public String getHjemTemplate() {
		return fullPath(hjemTemplate);
	}

	public String getLoginTemplate() {
		return fullPath(loginTemplate);
	}
	
	public String getBosetterneTemplate() {
		return fullPath(bosetterneTemplate);
	}
	
	private String fullPath(String template) {
		return baseMappe + template;
	}

}
