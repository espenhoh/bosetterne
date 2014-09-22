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
    private String loggUtTemplate;
    
    @NotEmpty
    @JsonProperty
    private String loginTemplate;

	public String getHjemTemplate() {
		return fullPath(hjemTemplate);
	}

	public String getLoginTemplate() {
		return fullPath(loginTemplate);
	}

	public String getLoggUtTemplate() {
		return fullPath(loggUtTemplate);
	}
	
	private String fullPath(String template) {
		return baseMappe + template;
	}

}
