package com.holtebu.bosetterne.service.core;

import com.holtebu.bosetterne.api.Spiller;

/** Håndterer innlogging og sesjon */
public class Legitimasjon {


	public Legitimasjon() {}
	
	private String code;
    private String responseType;
    private String clientId;
    private String redirectUri;
    private String scope;
    private String state;
    private Spiller spiller;
    private String secret;
	


    public String getCode() {
		return code;
	}
	public Legitimasjon setCode(String code) {
		this.code = code;
		return this;
	}
	public String getResponseType() {
		return responseType;
	}
	public Legitimasjon setResponseType(String responseType) {
		this.responseType = responseType;
		return this;
	}
	public String getClientId() {
		return clientId;
	}
	public Legitimasjon setClientId(String clientId) {
		this.clientId = clientId;
		return this;
	}
	public String getRedirectUri() {
		return redirectUri;
	}
	public Legitimasjon setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
		return this;
	}
	public String getScope() {
		return scope;
	}
	public Legitimasjon setScope(String scope) {
		this.scope = scope;
		return this;
	}
	public String getState() {
		return state;
	}
	public Legitimasjon setState(String state) {
		this.state = state;
		return this;
	}
	public Spiller getSpiller() {
		return spiller;
	}
	public Legitimasjon setSpiller(Spiller spiller) {
		this.spiller = spiller;
		return this;
	}
	public String getSecret() {
		return secret;
	}
	public Legitimasjon setSecret(String secret) {
		this.secret = secret;
		return this;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Client id: " + clientId;
	}




}
