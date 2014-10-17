package com.holtebu.bosetterne.api.lobby;

public class Historikk {
	private int spillId;
	private String spill;
	private String spiller;
	private int plassering;
	private boolean fullfort;
	
	public Historikk(){}
	
	void setSpillId(int spillId) {
		this.spillId = spillId;
	}
	void setSpill(String spill) {
		this.spill = spill;
	}
	void setSpiller(String spiller) {
		this.spiller = spiller;
	}
	void setPlassering(int plassering) {
		this.plassering = plassering;
	}
	void setFullfort(boolean fullfort) {
		this.fullfort = fullfort;
	}
	public int getSpillId() {
		return spillId;
	}
	public String getSpill() {
		return spill;
	}
	public String getSpiller() {
		return spiller;
	}
	public int getPlassering() {
		return plassering;
	}
	public boolean isFullfort() {
		return fullfort;
	}
}


