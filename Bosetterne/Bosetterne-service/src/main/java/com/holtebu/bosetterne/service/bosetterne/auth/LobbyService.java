package com.holtebu.bosetterne.service.bosetterne.auth;

public interface LobbyService<S, LEG> {
	/**
	* Get the Principal information based on the identifier/ password in the
	* credentials
	*
	* @param leg
	* the credentials (e.g. username and password or some other type of
	* authentication)
	* @return the Spiller
	*/
	S getSpiller(LEG leg);
}
