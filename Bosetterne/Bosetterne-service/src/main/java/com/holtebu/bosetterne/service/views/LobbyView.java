package com.holtebu.bosetterne.service.views;

import java.util.ResourceBundle;

import com.holtebu.bosetterne.api.lobby.Spiller;

import io.dropwizard.views.View;

public class LobbyView extends View {

	public LobbyView(String templateName, ResourceBundle msg) {
		super(templateName);
		this.msg = msg;
	}
	
	protected final ResourceBundle msg;

	private Spiller spiller = null;
	
	public boolean getInnlogget() {
		return spiller != null;
	}

	public Spiller getSpiller() {
		return spiller;
	}

	public void setSpiller(Spiller spiller) {
		this.spiller = spiller;
	}
	
	public String getGame(){
		return msg.getString("game");
	}
	
	public String getLoggedin(){
		return msg.getString("loggedin");
	}
	
	public String getRegister(){
		return msg.getString("register");
	}
	
	public String getLogin(){
		return msg.getString("login");
	}
	
	public String getHome(){
		return msg.getString("home");
	}
	
	public String getBosetterne(){
		return msg.getString("bosetterne");
	}
	
	public String getHistorikk(){
		return msg.getString("historikk");
	}
	
	public String getLogout(){
		return msg.getString("logout");
	}
	
	public String getChat(){
		return msg.getString("chat");
	}
	
	public String getCopyright(){
		return msg.getString("copyright");
	}
	


}
