package com.holtebu.bosetterne.service;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuth2Cred {
	
	public OAuth2Cred(){}
	
	public OAuth2Cred (String clientId, String clientSecret){
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}
	
    @NotEmpty
    @JsonProperty
    private String clientId;

    @NotEmpty
    @JsonProperty
    private String clientSecret;

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}
}
