package com.holtebu.bosetterne.service.auth;

import java.util.List;

import com.holtebu.bosetterne.api.lobby.Historikk;
import com.holtebu.bosetterne.api.lobby.Spill;
import com.holtebu.bosetterne.api.lobby.Spiller;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;

public class LobbyDAOStub implements LobbyDAO{

	@Override
	public Spiller finnSpillerVedNavn(String name) {
		return new Spiller();
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

	@Override
	public Legitimasjon finnLegVedNavn(String brukernavn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void oppdaterSpiller(Spiller spiller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void oppdaterPassord(Spiller spiller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String epostEksisterer(String epost) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Historikk> getHistorikk(String navn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Spill> getSpilliste() {
		// TODO Auto-generated method stub
		return null;
	}

}
