package com.holtebu.bosetterne.service.bosetterne.auth;

import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.bosetterne.core.dao.LobbyDAO;

public class LobbyDAOStub implements LobbyDAO{

	@Override
	public Spiller finnSpillerVedNavn(String name) {
		return new Spiller("donald");
	}

	@Override
	public void close() {
		// Nothing needed here, not a real connection anyway;
	}

	@Override
	public void registrerSpiller(Spiller s) {
		
	}

	@Override
	public void slettSpiller(String navn) {
		// TODO Auto-generated method stub
	}

}
