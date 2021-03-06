package com.holtebu.brettspill.api.helloworld;

import java.util.Date;

public class Data {
	private String message;
	private String author;
	private long time;
	
	public Data(String author, String message) {
		this.author = author;
		this.message = message;
		this.time = new Date().getTime();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
