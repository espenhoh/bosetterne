package com.holtebu.bosetterne.service.inject;

import javax.inject.Provider;

import com.holtebu.bosetterne.service.resources.lobby.LoggInnResource;

public class LoggInnProvider implements Provider<LoggInnResource> {

	public LoggInnProvider() {}

	@Override
	public LoggInnResource get() {
		return new LoggInnResource();
	}

}
