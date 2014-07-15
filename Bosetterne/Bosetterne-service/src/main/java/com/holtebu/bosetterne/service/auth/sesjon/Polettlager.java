package com.holtebu.bosetterne.service.auth.sesjon;

import com.google.common.base.Optional;

/**
 * Token store
 * 
 * @param <T>
 *            the type of the OAuth2.0 access token to use
 * @param <I>
 *            the type of the client details (representing an OAuth2.0 client)
 *            to use
 * @param <C>
 *            the type of the OAuth2.0 authorization code to use
 * 
 */
public interface Polettlager<T, I, L, C> {

	/**
	 * Autoriserer en spiller. Lagrer spilleren sammen med en ny accesstoken.
	 * 
	 * @param leg
	 *            legitimasjon som inneholder en spiller.
	 * @return accessToken som kan brukes for å få tilgang til resources.
	 */
	T storeAccessToken(L leg) throws AutorisasjonsException;

	Optional<I> getSpillerByAccessToken(String accessToken);

	/**
	 * Dersom client_Id på <code>legitimasjon</code> stemmer med med client_Id på serveren genereres og lagres en ny autorisasjonskode.
	 * @param legitimasjon
	 * @return autorisasjonskode
	 * @throws AutorisasjonsException
	 */
	C storeAuthorizationCode(L legitimasjon) throws AutorisasjonsException;

	Optional<I> getSpillerByAuthorizationCode(C code);

}
