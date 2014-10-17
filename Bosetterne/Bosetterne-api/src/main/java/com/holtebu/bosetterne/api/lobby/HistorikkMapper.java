package com.holtebu.bosetterne.api.lobby;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class HistorikkMapper implements ResultSetMapper<Historikk> {

	@Override
	public Historikk map(int index, ResultSet r, StatementContext ctx)
			throws SQLException {
		
		Historikk historikk = new Historikk();
		historikk.setSpillId(r.getInt("spill_id"));
		historikk.setSpill(r.getString("navn"));
		historikk.setSpiller(r.getString("brukernavn"));
		historikk.setPlassering(r.getInt("plassering"));
		historikk.setFullfort(r.getBoolean("fullfort"));
		return historikk;
	}

}
