package com.holtebu.bosetterne.api.lobby;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class SpillerISpillMapper implements ResultSetMapper<SpillerISpill> {

	@Override
	public SpillerISpill map(int index, ResultSet r, StatementContext ctx)
			throws SQLException {
		
		SpillerMapper spillerMapper = new SpillerMapper();
		
		Spiller spiller = spillerMapper.map(index, r, ctx);
		
		SpillerISpill spillerISpill = new SpillerISpill();
		
		spillerISpill.setSpillerISpillId(r.getInt("spiller_i_spill_id"));
		spillerISpill.setSpiller(spiller);
		spillerISpill.setPlassering(r.getInt("plassering"));
		spillerISpill.setSpill(r.getString("spill"));
		
		return spillerISpill;
	}
}
