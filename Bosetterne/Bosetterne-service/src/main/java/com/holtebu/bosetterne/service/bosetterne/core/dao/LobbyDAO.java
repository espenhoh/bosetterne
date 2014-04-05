package com.holtebu.bosetterne.service.bosetterne.core.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.holtebu.bosetterne.service.bosetterne.core.Spiller;
import com.holtebu.bosetterne.service.bosetterne.core.SpillerMapper;

@RegisterMapper(SpillerMapper.class)
public interface LobbyDAO {
	@SqlUpdate("INSERT INTO bosetterne.SPILLER (navn, passord, epost) VALUES (:navn, :passord, :epost)")
	void registrerSpiller(@BindBean Spiller s);
	
	@SqlQuery("SELECT navn, passord, epost FROM bosetterne.SPILLER WHERE navn = :navn")
	Spiller finnSpillerVedNavn(@Bind("navn") String navn);
	
	@SqlUpdate("DELETE FROM bosetterne.SPILLER WHERE navn = :navn")
	void slettSpiller(@Bind("navn") String navn);

	/**
	 * close with no args is used to close the connection
	 */
	void close();
}