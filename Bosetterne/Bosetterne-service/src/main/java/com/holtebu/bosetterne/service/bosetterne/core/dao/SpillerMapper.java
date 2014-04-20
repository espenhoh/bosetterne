package com.holtebu.bosetterne.service.bosetterne.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.holtebu.bosetterne.api.Spiller;

public class SpillerMapper implements ResultSetMapper<Spiller> {
	@Override
	public Spiller map(int index, ResultSet r, StatementContext ctx)
			throws SQLException {
		return new Spiller(
				r.getString("brukernavn"),
				r.getString("kallenavn"),
				r.getString("passord"),
				r.getString("epost"));
	}
}