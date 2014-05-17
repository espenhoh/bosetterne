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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.OAuth2Cred;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.google.common.base.Optional;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;
import com.google.inject.name.Named;

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
	public AccessToken storeAccessToken(Legitimasjon leg) {
		AccessToken accessToken = null;

		if(verifyClientId(leg) && verifyClientSecret(leg)) {
			accessToken = new AccessToken(UUID.randomUUID().toString(),	Long.MAX_VALUE);
			accessTokens.put(accessToken.getAccess_token(), leg.getSpiller());
		}

		return accessToken;
	}

	@Override
	public Optional<Spiller> getSpillerByAccessToken(String accessToken) {
		Spiller clientDetails = accessTokens.get(accessToken);
		return Optional.fromNullable(clientDetails);
	}

	@Override
	public String storeAuthorizationCode(Legitimasjon leg) {
		if(verifyClientId(leg)) {
			String code = UUID.randomUUID().toString();
			leg.setCode(code);
			codes.put(code, leg);
			return code;
		}

		return null;
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

	boolean verifyClientId(Legitimasjon leg) {
		if (leg == null) {
			return false;
		} else {
			return oAuth2Verdier.getClientId().equals(leg.getClientId());
		}
	}

}