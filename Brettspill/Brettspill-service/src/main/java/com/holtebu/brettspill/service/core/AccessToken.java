

package com.holtebu.brettspill.service.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.jackson.JsonSnakeCase;

/**
 * An AccessToken
 * 
 * See https://tools.ietf.org/html/draft-ietf-oauth-v2-26#section-4.1.3
 * 
 */
@JsonSnakeCase
public class AccessToken {
	private String accessToken;
	private String refreshToken;
	private Long expiresIn;
	private String tokenType;
	private String scope;

	public AccessToken(String access_token, Long expires_in) {
		this(access_token, expires_in, "bearer", "bosetterne", "");
	}

	public AccessToken(String accessToken, Long expiresIn, String tokenType,
			String scope, String refreshToken) {
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.tokenType = tokenType;
		this.scope = scope;
		this.refreshToken = refreshToken;
	}

	
	@JsonProperty
	public String getAccessToken() {
		return accessToken;
	}

	@JsonProperty
	public String getRefreshToken() {
		return refreshToken;
	}

	@JsonProperty
	public Long getExpiresIn() {
		return expiresIn;
	}

	@JsonProperty
	public String getTokenType() {
		return tokenType;
	}

	@JsonProperty
	public String getScope() {
		return scope;
	}

	@Override
	public String toString() {
		return "AccessToken [access_token=" + accessToken + ", expires_in="
				+ expiresIn + ", scope=" + scope + "]";
	}
}