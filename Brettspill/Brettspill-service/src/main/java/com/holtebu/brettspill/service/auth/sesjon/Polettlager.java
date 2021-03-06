package com.holtebu.brettspill.service.auth.sesjon;

import java.util.Map;

import org.jvnet.hk2.annotations.Contract;

import java.util.Optional;

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
@Contract
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
	 * Removes the player from the HashMap
	 * @param player the player to remove
	 * @throws AutorisasjonsException when player not found.
	 */
	void logOutSpiller(I player) throws AutorisasjonsException;

	/**
	 * Dersom client_Id på <code>legitimasjon</code> stemmer med med client_Id på serveren genereres og lagres en ny autorisasjonskode.
	 * @param legitimasjon
	 * @return autorisasjonskode
	 * @throws AutorisasjonsException
	 */
	C storeAuthorizationCode(L legitimasjon) throws AutorisasjonsException;

	/**
	 * Henter en spiller med aktuell autoriseringskode. Hvis spiller ikke finnes, returneres absent.
	 *  <p>
	 * @param code Autoriseringskode.
	 * @return Spilleren som tilsvarer autosriesingskoden.
	 */
	Optional<I> getSpillerByAuthorizationCode(C code);
	
	/**
	 * Henter alle innloggede spillere ved tidspunktet metoden blir kalt.
	 */
	Map<String, I> getInnloggedeSpillere();

}
