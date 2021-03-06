package com.holtebu.brettspill.api.lobby;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class SpillerMapper implements ResultSetMapper<Spiller> {
	//TODO add games
	@Override
	public Spiller map(int index, ResultSet r, StatementContext ctx)
			throws SQLException {
		Spiller spiller = new Spiller(
				r.getString("brukernavn"),
				r.getString("kallenavn"),
				r.getString("farge"),
				r.getString("epost"),
				r.getString("passord"),
				r.getDate("dato_registrert"));
		
		spiller.setSistInnlogget(r.getTimestamp("dato_sist_innlogget"));
		spiller.setInnlogget(r.getBoolean("innlogget"));
		spiller.setiSpill(r.getBoolean("i_spill"));
		return spiller;
	}
}