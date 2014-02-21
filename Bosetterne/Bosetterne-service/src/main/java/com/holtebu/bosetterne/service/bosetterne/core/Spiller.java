package com.holtebu.bosetterne.service.bosetterne.core;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity
public class Spiller {
	private Spiller(){}
	
    public Spiller(String navn) {
        this.navn = navn;
    }
	
	private Integer spiller_id;
    private String navn;
    private String passord;
    
    @Id
    @GeneratedValue
	public Integer getId() {
	   return spiller_id;
	}
	
	public void setId(Integer spiller_id) {
	   this.spiller_id = spiller_id;
	}



    public String getNavn() {
        return navn;
    }
}
