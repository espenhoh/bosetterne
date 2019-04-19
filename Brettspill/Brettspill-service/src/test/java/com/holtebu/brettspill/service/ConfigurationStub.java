package com.holtebu.brettspill.service;

import com.holtebu.brettspill.service.core.dao.LobbyDAO;
import com.holtebu.brettspill.service.inject.BosetterneServiceBinder;
import com.holtebu.brettspill.service.inject.StartupBinder;
import io.dropwizard.jackson.Jackson;

import java.io.FileInputStream;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skife.jdbi.v2.DBI;

import static org.mockito.Mockito.when;

/**
 * Prosesserer YAML fila og legger verdiene på en ny BosettereneConfiguration. Til bruk ved test.
 * 
 * @author espen
 *
 */
public class ConfigurationStub {
	
	private static BosetterneConfiguration config;

	private static ServiceLocator locator;

    private static ServiceLocator simpleLocator;
	
	private static BosetterneConfiguration conf() throws JsonProcessingException, IOException {
		ObjectMapper mapper = Jackson.newObjectMapper();
		FileInputStream is = new FileInputStream("Brettspill.yml");
		YAMLFactory yamlFactory = new YAMLFactory();
		final JsonNode node = mapper.readTree(yamlFactory.createParser(is));
		return mapper.readValue(new TreeTraversingParser(node), BosetterneConfiguration.class);
	}
	
	public static BosetterneConfiguration getConf() throws JsonProcessingException, IOException {
		if (config == null) {
			System.out.println("Creates new config!");
			config = conf();
		}
		return config;
	}

	public static ServiceLocator getSimpleLocator() throws IOException {

	    if(locator == null){
            DBIFactory dbiFactoryMock = Mockito.mock(DBIFactory.class);
            DBI dbiMock = Mockito.mock(DBI.class);
            Environment environmentMock =  Mockito.mock(Environment.class);
            LobbyDAO lobbyDAOMock = Mockito.mock(LobbyDAO.class);
            BosetterneConfiguration configurationMock = Mockito.mock(BosetterneConfiguration.class);

            //when(dbiFactoryMock.build(environmentMock, configurationMock.getDataSourceFactory(), "mySQL")).thenReturn(dbiMock);
            //when(dbiMock.onDemand(LobbyDAO.class)).thenReturn(lobbyDAOMock);

            BosetterneServiceBinder binder = new BosetterneServiceBinder(dbiFactoryMock);
            //binder.setUpEnv(configurationMock, environmentMock);


            locator = ServiceLocatorUtilities.bind("SetupBrettspill", binder);
        }
        return  locator;
	}

    public static ServiceLocator getLocator() throws IOException {

        if(locator == null){
            DBIFactory dbiFactoryMock = Mockito.mock(DBIFactory.class);
            DBI dbiMock = Mockito.mock(DBI.class);
            Environment environmentMock =  Mockito.mock(Environment.class);
            LobbyDAO lobbyDAOMock = Mockito.mock(LobbyDAO.class);

            config = getConf();

            when(dbiFactoryMock.build(environmentMock, config.getDataSourceFactory(), "mySQL")).thenReturn(dbiMock);
            when(dbiMock.onDemand(LobbyDAO.class)).thenReturn(lobbyDAOMock);

            BosetterneServiceBinder binder = new BosetterneServiceBinder(dbiFactoryMock, config, environmentMock);
            binder.setUpEnv(config, environmentMock);


            locator = ServiceLocatorUtilities.bind("SetupBrettspill", binder);
        }
        return  locator;
    }
	
	public static void main(String[] args) throws JsonProcessingException, IOException{
		ServiceLocator locator = getSimpleLocator();
		System.out.println("Alt ok!");
	}
}
