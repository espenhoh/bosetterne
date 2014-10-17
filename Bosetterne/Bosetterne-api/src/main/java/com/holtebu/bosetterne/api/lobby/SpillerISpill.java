package com.holtebu.bosetterne.api.lobby;

public class SpillerISpill {
	
	private int spillerISpillId;
	private Spiller spiller;
	private String spill;
	private int plassering;
	
	public SpillerISpill(){
		
	}
	
	void setSpillerISpillId(int spillerISpillId) {
		this.spillerISpillId = spillerISpillId;
	}
	void setSpiller(Spiller spiller) {
		this.spiller = spiller;
	}
	void setSpill(String spill) {
		this.spill = spill;
	}
	void setPlassering(int plassering) {
		this.plassering = plassering;
	}
	public int getSpillerISpillId() {
		return spillerISpillId;
	}
	public Spiller getSpiller() {
		return spiller;
	}
	public String getSpill() {
		return spill;
	}
	public int getPlassering() {
		return plassering;
	}

	
	

}
