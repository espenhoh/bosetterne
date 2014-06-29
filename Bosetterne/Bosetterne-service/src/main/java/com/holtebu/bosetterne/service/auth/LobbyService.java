package com.holtebu.bosetterne.service.auth;

/**
 * Hjelpemetode for utentisering
 * 
 * @author espen
 *
 * @param <S> En form for spiller
 * @param <L> En form for legitimasjon
 */
public interface LobbyService<S, L> {
	/**
	* Hent spilleren som identifiseres med brukernavn og passord funnet i leg.
	* Dersom spilleren ikke finnes, eller passordet er feil returneres null eller Optional.absent.
	* 
	*
	* @param leg
	* legitimasjon (feks. brukernavn og passord)
	* @return Spiller
	*/
	S getSpiller(L leg);
}
