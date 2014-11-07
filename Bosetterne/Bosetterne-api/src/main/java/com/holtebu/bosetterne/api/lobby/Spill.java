package com.holtebu.bosetterne.api.lobby;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class Spill {

	private int spillId;
	
	private String navn;
	
	private String typeSpill;
	
	private boolean startet;
	
	private boolean fullfort;
	
	private int maxPoeng;
	
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

	public String getTypeSpill() {
		return typeSpill;
	}

	public void setTypeSpill(String typeSpill) {
		this.typeSpill = typeSpill;
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
	
	public int getMaxPoeng() {
		return maxPoeng;
	}

	public void setMaxPoeng(int maxPoeng) {
		this.maxPoeng = maxPoeng;
	}

	public static class SpillMapper implements ResultSetMapper<Spill> {
	
		public Spill map(int index, ResultSet r, StatementContext ctx) throws SQLException {
			Spill spill = new Spill();
			
			spill.setSpillId(r.getInt(1));
			spill.setTypeSpill(r.getString(2));
			spill.setNavn(r.getString(3));
			spill.setStartet(r.getBoolean(4));
			spill.setFullfort(r.getBoolean(5));
			spill.setMaxPoeng(r.getInt(6));
			
			return spill;
		}
		
	}
}
