package com.holtebu.brettspill.service.core.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.holtebu.brettspill.api.lobby.Historikk;
import com.holtebu.brettspill.api.lobby.Spill;
import com.holtebu.brettspill.api.lobby.Spiller;
import com.holtebu.brettspill.api.lobby.SpillerISpillMapper;
import com.holtebu.brettspill.api.lobby.SpillerMapper;
import com.holtebu.brettspill.service.core.Legitimasjon;

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
	
	@SqlQuery("SELECT SPILLER_I_SPILL.spill_id, SPILL.navn, SPILLER_I_SPILL.brukernavn, SPILLER_I_SPILL.plassering, SPILL.dato_tom IS NOT NULL AS fullfort " +
			"FROM SPILLER_I_SPILL " +
			"INNER JOIN SPILL " +
			"ON SPILLER_I_SPILL.spill_id=SPILL.spill_id " +
			"WHERE SPILLER_I_SPILL.brukernavn = :brukernavn")
	@RegisterMapper(Historikk.HistorikkMapper.class)
	List<Historikk> getHistorikk(@Bind("brukernavn") String navn);

    //@SqlQuery("select spill_id, type_spill, navn, dato_fom, dato_tom, max_poeng from SPILL WHERE (dato_fom > TIMESTAMP(DATE_SUB(NOW(), INTERVAL 14 day))) OR (dato_fom IS NULL) ORDER by dato_fom, dato_tom asc ")
    @SqlQuery("select spill_id, type_spill, navn, dato_fom, dato_tom, max_poeng from SPILL")
	@RegisterMapper(Spill.SpillMapper.class)
	List<Spill> getSpilliste();

	/**
	 * close with no args is used to close the connection
	 */
	void close();
}
