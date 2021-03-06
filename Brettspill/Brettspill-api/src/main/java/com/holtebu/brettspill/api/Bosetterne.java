package com.holtebu.brettspill.api;

import java.util.Random;
import java.util.concurrent.*;

import javax.inject.Inject;
import javax.inject.Named;

import org.jvnet.hk2.annotations.Service;

@Service
public class Bosetterne extends ConcurrentHashMap<String, SpillType>  {

	private static final long serialVersionUID = 6227758345313528992L;
	
	private final Random random;
	
	@Inject
	private Bosetterne(@Named("antallSpill") int initAntallSpill, Random random) {
		super(initAntallSpill);
		this.random = random;
	}
	
	public String opprettSpill() {
		return "";
		//Random
	}
}
