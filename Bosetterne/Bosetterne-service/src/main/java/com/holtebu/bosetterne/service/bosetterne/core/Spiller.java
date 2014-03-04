package com.holtebu.bosetterne.service.bosetterne.core;




public class Spiller {
	
	public Spiller(){}
	
    public Spiller(String navn) {
        this.navn = navn;
    }
    
    public Spiller(Integer spiller_id, String navn, String passord) {
    	this.spiller_id = spiller_id;
    	this.navn = navn;
        this.passord = passord;
    }
	
	private Integer spiller_id;
    private String navn;
    private String passord;
    

	public Integer getId() {
	   return spiller_id;
	}
	
    public String getNavn() {
        return navn;
    }
    
    public String getPassord() {
        return passord;
    }
}


