package com.holtebu.bosetterne.service.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.holtebu.bosetterne.api.Game;
import com.holtebu.bosetterne.api.Spiller;

public class SpillerMapper implements ResultSetMapper<Spiller> {
	//TODO add games
	@Override
	public Spiller map(int index, ResultSet r, StatementContext ctx)
			throws SQLException {
		return new Spiller(
				r.getString("brukernavn"),
				r.getString("passord"),
				r.getString("kallenavn"),
				r.getBoolean("innlogget"),
				new HashSet<Game>(),
				r.getDate("dato_sist_innlogget"));
	}
}