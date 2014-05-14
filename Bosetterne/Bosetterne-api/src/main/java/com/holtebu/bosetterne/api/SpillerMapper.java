package com.holtebu.bosetterne.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
		spiller.setISpill(r.getBoolean("i_spill"));
		return spiller;
	}
}