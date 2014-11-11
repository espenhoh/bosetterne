package com.holtebu.bosetterne.service.auth;

import java.util.Set;

import org.jvnet.hk2.annotations.Contract;

import com.holtebu.bosetterne.api.lobby.Spill;

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
	
	/**
	 * 
	 * @param spiller spilleren som skal logges ut/lagres
	 */
	void lagreSpiller(S spiller);
	
	/**
	 * Henter Liste med spill
	 * @return liste med alle spill på serveren.
	 */
	Set<Spill> hentListe();
}
