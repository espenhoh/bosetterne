package com.holtebu.bosetterne.service.inject;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;
import org.skife.jdbi.v2.DBI;

import com.holtebu.bosetterne.service.core.dao.LobbyDAO;

public class DAOFactory {
	
	public static class LobbyDAOFactory implements Factory<LobbyDAO> {

		private DBI jdbi;
		
		@Inject
		public LobbyDAOFactory(DBI jdbi){
			this.jdbi = jdbi;
		}
		
		@Override
		public LobbyDAO provide() {
			return jdbi.onDemand(LobbyDAO.class);
		}

		@Override
		public void dispose(LobbyDAO instance) {
			
		}
		
	}

}
