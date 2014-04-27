package com.holtebu.bosetterne.service.auth;

public interface LobbyService<S, L> {
	/**
	* Hent spilleren som identifiseres med brukernavn og passord funnet i L.
	*
	* @param leg
	* the credentials (e.g. username and password or some other type of
	* authentication)
	* @return the Spiller
	*/
	S getSpiller(L leg);
}
