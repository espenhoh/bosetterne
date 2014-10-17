package com.holtebu.bosetterne.service.auth.sesjon;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.jvnet.hk2.annotations.Contract;
import org.jvnet.hk2.annotations.Service;

import com.google.common.base.Optional;
import com.holtebu.bosetterne.api.lobby.Spiller;
import com.holtebu.bosetterne.service.OAuth2Cred;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;

/**
 * Token store
 * 
 */
@Service
@Singleton
public class PolettlagerIMinne implements
		Polettlager<AccessToken, Spiller, Legitimasjon, String> {

	private final Map<String, Spiller> accessTokens;
	private final Map<String, Legitimasjon> codes;
	private final OAuth2Cred oAuth2Verdier;

	@Inject
	public PolettlagerIMinne(
			@Named("tokens") Map<String, Spiller> accessTokens,
			@Named("codes") Map<String, Legitimasjon> codes,
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
			Spiller spiller = leg.getSpiller();
			spiller.setInnlogget(true);
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

	/**
	 * Hvis Spiller med autoriseringkode <code>code</code> finnes, returneres denne og spilleren fjernes fra hashmappet.
	 *  
	 */
	@Override
	public Optional<Spiller> getSpillerByAuthorizationCode(String code) {
		Spiller spiller = null;
		
		Legitimasjon leg = codes.remove(code);
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
	 * Verfiserer at OAuth 2.0 client id stemmer overens med dets som er konfigurert på server
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


	@Override
	public void logOutSpiller(Spiller spiller) throws AutorisasjonsException {
		Set<Entry<String,Spiller>> entrySet = accessTokens.entrySet();
		if(!spillerInnlogget(spiller, entrySet)){
			throw new AutorisasjonsException("Spiller " + spiller + " ikke logget inn!");
		}
		
	}


	private boolean spillerInnlogget(Spiller spiller,
			Set<Entry<String, Spiller>> entrySet) {
		for(Entry<String,Spiller> entry: entrySet){
			if(spiller.equals(entry.getValue())){
				accessTokens.remove(entry.getKey());
				spiller.setInnlogget(false);
				return true;
			}
		}
		return false;
	}


	@Override
	public Map<String, Spiller> getInnloggedeSpillere() {
		return accessTokens;
	}

}