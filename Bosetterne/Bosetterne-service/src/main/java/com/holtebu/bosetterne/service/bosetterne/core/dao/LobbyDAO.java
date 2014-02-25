package com.holtebu.bosetterne.service.bosetterne.core.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface LobbyDAO {
	@SqlQuery("SELECT passord FROM bosetterne.SPILLER WHERE navn = :navn")
	String findPassByName(@Bind("navn") String navn);

	/**
	 * close with no args is used to close the connection
	 */
	void close();
}
