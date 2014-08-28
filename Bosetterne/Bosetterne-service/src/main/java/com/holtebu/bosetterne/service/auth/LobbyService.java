package com.holtebu.bosetterne.service.auth;

import org.jvnet.hk2.annotations.Contract;

/**
 * Hjelpemetode for utentisering
 * 
 * @author espen
 *
 * @param <S> En form for spiller
 * @param <L> En form for legitimasjon
 */
@Contract
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
