package com.holtebu.bosetterne.service.bosetterne.core;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class SpillerMapper implements ResultSetMapper<Spiller> {
	@Override
	public Spiller map(int index, ResultSet r, StatementContext ctx)
			throws SQLException {
		return new Spiller(r.getInt("spiller_id"), r.getString("navn"),
				r.getString("passord"));
	}
}