package com.holtebu.bosetterne.service;

import io.dropwizard.jackson.Jackson;

import java.io.File;
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
	
	BosetterneConfiguration config;
	
	private ConfigurationStub () throws JsonProcessingException, IOException {
		/*File file = new File("Bosetterne.yml");
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
		
		JsonNode node = mapper.readTree(file);
		
		config = mapper.readValue(new TreeTraversingParser(node), BosetterneConfiguration.class);*/
		
		//Ny
		ObjectMapper mapper = Jackson.newObjectMapper();
		FileInputStream is = new FileInputStream("Bosetterne.yml");
		YAMLFactory yamlFactory = new YAMLFactory();
		final JsonNode node = mapper.readTree(yamlFactory.createParser(is));
		config = mapper.readValue(new TreeTraversingParser(node), BosetterneConfiguration.class);
	}
	
	public static BosetterneConfiguration getConf() throws JsonProcessingException, IOException {
		return new ConfigurationStub().config;
	}
	
	public static void main(String[] args) throws JsonProcessingException, IOException{
		ConfigurationStub stub = new ConfigurationStub();
		System.out.println("Alt ok!");
	}
}
