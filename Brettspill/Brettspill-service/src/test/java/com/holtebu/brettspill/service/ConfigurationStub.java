package com.holtebu.brettspill.service;

import io.dropwizard.jackson.Jackson;

import java.io.FileInputStream;
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
	
	private static BosetterneConfiguration config;
	
	private static BosetterneConfiguration conf() throws JsonProcessingException, IOException {
		ObjectMapper mapper = Jackson.newObjectMapper();
		FileInputStream is = new FileInputStream("Brettspill.yml");
		YAMLFactory yamlFactory = new YAMLFactory();
		final JsonNode node = mapper.readTree(yamlFactory.createParser(is));
		return mapper.readValue(new TreeTraversingParser(node), BosetterneConfiguration.class);
	}
	
	public static BosetterneConfiguration getConf() throws JsonProcessingException, IOException {
		if (config == null) {
			config = conf();
		}
		return config;
	}
	
	public static void main(String[] args) throws JsonProcessingException, IOException{
		ConfigurationStub stub = new ConfigurationStub();
		System.out.println("Alt ok!");
	}
}
