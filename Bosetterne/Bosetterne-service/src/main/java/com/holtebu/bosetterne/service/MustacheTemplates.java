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

	public String getHjemTemplate() {
		return baseMappe + hjemTemplate;
	}

	public String getLoginTemplate() {
		return baseMappe + loginTemplate;
	}

}
