package com.holtebu.bosetterne.service.auth.sesjon;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.Map;
import java.util.UUID;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.OAuth2Cred;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;

/**
 * Token store
 * 
 */
public class PolettlagerIMinne implements
		Polettlager<AccessToken, Spiller, Legitimasjon, String> {

	private final Map<String, Spiller> accessTokens;
	private final Map<String, Legitimasjon> codes;
	private final OAuth2Cred oAuth2Verdier;

	@Inject
	public PolettlagerIMinne(
			Map<String, Spiller> accessTokens,
			Map<String, Legitimasjon> codes,
			OAuth2Cred oAuth2Verdier) {
		this.accessTokens = accessTokens;
		this.codes = codes;
		this.oAuth2Verdier = oAuth2Verdier;
	}


	@Override
	public AccessToken storeAccessToken(Legitimasjon leg) throws AutorisasjonsException {
		verifyClientId(leg);
		
		AccessToken accessToken = null;
		

		if(verifyClientSecret(leg)) {
			accessToken = new AccessToken(UUID.randomUUID().toString(),	Long.MAX_VALUE);
			accessTokens.put(accessToken.getAccessToken(), leg.getSpiller());
		}

		return accessToken;
	}

	@Override
	public Optional<Spiller> getSpillerByAccessToken(String accessToken) {
		Spiller clientDetails = accessTokens.get(accessToken);
		return Optional.fromNullable(clientDetails);
	}

	@Override
	public String storeAuthorizationCode(Legitimasjon leg) throws AutorisasjonsException {
		verifyClientId(leg);
		
		String code = UUID.randomUUID().toString();
		leg.setCode(code);
		codes.put(code, leg);
		return code;
	}

	@Override
	public Optional<Spiller> getSpillerByAuthorizationCode(String code) {
		Spiller spiller = null;
		
		Legitimasjon leg = codes.get(code);
		if (leg != null) {
			spiller = leg.getSpiller();
		}
		return Optional.fromNullable(spiller);
	}

	boolean verifyClientSecret(Legitimasjon leg) {
		if (leg == null) {
			return false;
		} else {
			return oAuth2Verdier.getClientSecret().equals(leg.getSecret());
		}
	}

	/**
	 * Verfiserer at OAuth 2.0 client id stemmer overens med dets som er konfigurert på serverK
	 * <p> 
	 * @param leg
	 * @throws AutorisasjonsException når leg er null, eller client id ikke stemmer.
	 */
	private void verifyClientId(Legitimasjon leg) throws AutorisasjonsException {
		boolean verified = false;
		if (leg != null) {
			verified = oAuth2Verdier.getClientId().equals(leg.getClientId());
		}
		
		if (!verified) {
			throw new AutorisasjonsException("Client_id mismatch. Legitimasjon var " + leg);
		}
		
		return;
	}

}