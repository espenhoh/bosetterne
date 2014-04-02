package com.holtebu.bosetterne.service.bosetterne.core;




public class Spiller {
	
	


	public Spiller(){}
	
    public Spiller(String navn) {
        this.navn = navn;
    }
    
    public Spiller(String navn, String passord, String epost) {
    	this.navn = navn;
        this.passord = passord;
        this.epost = epost;
    }
	
    private String epost;
    private String navn;
    private String passord;
    private String code;
    


	public String getEpost() {
	   return epost;
	}
	
    public String getNavn() {
        return navn;
    }
    
    public String getPassord() {
        return passord;
    }
    

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}


