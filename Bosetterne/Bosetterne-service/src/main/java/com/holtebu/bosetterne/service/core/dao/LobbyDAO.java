package com.holtebu.bosetterne.service.core.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.api.SpillerMapper;
import com.holtebu.bosetterne.service.core.Legitimasjon;

@RegisterMapper(SpillerMapper.class)
public interface LobbyDAO {
	@SqlUpdate("INSERT INTO bosetterne.SPILLER (brukernavn, kallenavn, farge, epost, passord, dato_registrert) VALUES (:brukernavn, :kallenavn, :farge, :epost, :passord, CURDATE())")
	void registrerSpiller(@BindBean Spiller s);

	@SqlUpdate("UPDATE bosetterne.SPILLER "
			+ "SET dato_sist_innlogget = :s.sistInnlogget, innlogget = :s.innlogget , i_spill = :s.iSpill "
			+ "WHERE brukernavn = :s.brukernavn")
	void oppdaterSpiller(@BindBean("s") Spiller spiller);

	@SqlUpdate("UPDATE bosetterne.SPILLER " + "SET passord = :s.passord "
			+ "WHERE brukernavn = :s.brukernavn")
	void oppdaterPassord(@BindBean("s") Spiller spiller);

	@SqlQuery("SELECT * FROM bosetterne.SPILLER WHERE brukernavn = :brukernavn")
	Spiller finnSpillerVedNavn(@Bind("brukernavn") String brukernavn);
	
	@SqlQuery("SELECT epost FROM bosetterne.SPILLER WHERE epost = :epost")
	String epostEksisterer(@Bind("epost") String epost);
	

	@SqlQuery("SELECT brukernavn, kallenavn, passord, epost FROM bosetterne.SPILLER WHERE brukernavn = :brukernavn")
	Legitimasjon finnLegVedNavn(@Bind("brukernavn") String brukernavn);

	@SqlUpdate("DELETE FROM bosetterne.SPILLER WHERE brukernavn = :brukernavn")
	void slettSpiller(@Bind("brukernavn") String navn);

	/**
	 * close with no args is used to close the connection
	 */
	void close();
}
