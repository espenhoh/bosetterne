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
public interface Polettlager<T, I, C> {

	T storeAccessToken(I spiller) throws AutorisasjonsUnntak;

	Optional<I> getSpillerByAccessToken(String accessToken);

	C storeAuthorizationCode(I spiller) throws AutorisasjonsUnntak;

	Optional<I> getSpillerByAuthorizationCode(C code);

}
