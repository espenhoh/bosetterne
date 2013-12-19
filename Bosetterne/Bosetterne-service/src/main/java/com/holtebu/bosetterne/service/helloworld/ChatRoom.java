package com.holtebu.bosetterne.service.helloworld;

import java.io.IOException;

import org.atmosphere.cache.UUIDBroadcasterCache;
import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.AtmosphereResponse;
import org.atmosphere.handler.OnMessage;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.BroadcastOnPostAtmosphereInterceptor;
import org.atmosphere.interceptor.HeartbeatInterceptor;
import org.atmosphere.interceptor.TrackMessageSizeB64Interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holtebu.bosetterne.api.helloworld.Data;


@AtmosphereHandlerService(path="/chat",
broadcasterCache = UUIDBroadcasterCache.class,
interceptors = { AtmosphereResourceLifecycleInterceptor.class,
                 BroadcastOnPostAtmosphereInterceptor.class,
                 TrackMessageSizeB64Interceptor.class,
                 HeartbeatInterceptor.class
               })
public class ChatRoom extends OnMessage<String> {

	private final ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void onMessage(AtmosphereResponse response, String message)
			throws IOException {
		response.write(mapper.writeValueAsString(mapper.readValue(message, Data.class)));
	}

}
