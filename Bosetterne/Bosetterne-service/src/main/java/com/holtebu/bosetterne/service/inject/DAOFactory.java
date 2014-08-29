package com.holtebu.bosetterne.service.inject;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;
import org.skife.jdbi.v2.DBI;

import com.holtebu.bosetterne.service.core.dao.LobbyDAO;

public class DAOFactory {
	
	private DBI jdbi;
	
	public DAOFactory(DBI jdbi){
		this.jdbi = jdbi;
	}
	
	public DAOFactories<LobbyDAO> lobbyDAOFactory() {
		return new DAOFactories<LobbyDAO>(LobbyDAO.class);
	}
	
	public class DAOFactories<T> implements Factory<T> {

		private Class<T> clazz;

		public DAOFactories(Class<T> clazz){
			this.clazz = clazz;
		}
		
		@Override
		public T provide() {
			return jdbi.onDemand(clazz);
		}

		@Override
		public void dispose(T instance) {
			
		}
		
	}

}
