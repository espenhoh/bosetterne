package com.holtebu.bosetterne.service;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * Prosesserer YAML fila og legger verdiene på en ny BosettereneConfiguration. Til bruk ved test.
 * 
 * @author espen
 *
 */
public class ConfigurationStub {

	private static ConfigurationStub stub;
	
	BosetterneConfiguration config;
	
	private ConfigurationStub () throws JsonProcessingException, IOException {
		File file = new File("Bosetterne.yml");
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.TRUE);
		
		JsonNode node = mapper.readTree(file);
		
		config = mapper.readValue(new TreeTraversingParser(node), BosetterneConfiguration.class);
	}
	
	public static BosetterneConfiguration getConf() throws JsonProcessingException, IOException {
		if (stub == null) {
			stub = new ConfigurationStub();
		}
		return stub.config;
	}
}