package com.holtebu.bosetterne.api.lobby;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class Spill {

	private int spillId;
	
	private String navn;
	
	private boolean startet;
	
	private boolean fullfort;
	
	public Spill(){}

	public int getSpillId() {
		return spillId;
	}

	public void setSpillId(int spillId) {
		this.spillId = spillId;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public boolean isStartet() {
		return startet;
	}

	public void setStartet(boolean startet) {
		this.startet = startet;
	}

	public boolean isFullfort() {
		return fullfort;
	}

	public void setFullfort(boolean fullfort) {
		this.fullfort = fullfort;
	}
	
	public static class SpillMapper implements ResultSetMapper<Spill> {
	
		public Spill map(int index, ResultSet r, StatementContext ctx) throws SQLException {
			Spill spill = new Spill();
			
			spill.setSpillId(r.getInt(1));
			spill.setNavn(r.getString(2));
			spill.setStartet(r.getBoolean(3));
			spill.setFullfort(r.getBoolean(4));
			
			return spill;
		}
		
	}
}
