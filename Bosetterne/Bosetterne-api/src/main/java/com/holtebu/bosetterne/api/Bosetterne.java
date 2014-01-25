package com.holtebu.bosetterne.api;

import java.util.Random;
import java.util.concurrent.atomic.*;
import java.util.concurrent.*;

public class Bosetterne extends ConcurrentHashMap<String, Spill> {
	private static final int INITIELT_ANNTALL_SPILL = 20;
	
	private Random random;
	
	private Bosetterne() {
		super(INITIELT_ANNTALL_SPILL);
		random = new Random();
	}
	
	public String opprettSpill() {
		return "";
		//Random
	}
}
