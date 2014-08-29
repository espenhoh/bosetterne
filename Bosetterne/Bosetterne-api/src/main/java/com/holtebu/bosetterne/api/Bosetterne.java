package com.holtebu.bosetterne.api;

import java.util.Random;
import java.util.concurrent.*;

import javax.inject.Inject;

public class Bosetterne extends ConcurrentHashMap<String, Spill>  {

	private static final long serialVersionUID = 6227758345313528992L;
	
	private final Random random;
	
	@Inject
	private Bosetterne(int initAntallSpill, Random random) {
		super(initAntallSpill);
		this.random = random;
	}
	
	public String opprettSpill() {
		return "";
		//Random
	}
}
