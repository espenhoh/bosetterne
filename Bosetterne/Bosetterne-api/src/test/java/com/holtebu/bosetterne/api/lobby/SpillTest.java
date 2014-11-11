package com.holtebu.bosetterne.api.lobby;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.StatementContext;

public class SpillTest {
	
	private Spill.SpillMapper mapper;
	private Spill spill;

	@Before
	public void setUp() throws Exception {
		mapper = new Spill.SpillMapper();
		
		spill = new Spill();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSpillmapper() throws SQLException{
		ResultSet r = mock(ResultSet.class);
		StatementContext ctx = mock(StatementContext.class);
		when(r.getInt(anyInt())).thenReturn(1);
		when(r.getString(anyInt())).thenReturn("BOSETTERNE");
		when(r.getBoolean(anyInt())).thenReturn(true);
		
		spill = mapper.map(0, r, ctx);
		
		assertThat("", spill.getSpillId(), is(1));
		assertThat("", spill.getNavn(), is("BOSETTERNE"));
		assertThat("", spill.isStartet(), is(true));
		assertThat("", spill.isFullfort(), is(true));
	}

	@Test
	public void testGetTypeSpill() throws Exception {
		Locale locale = new Locale("nb", "NO");
		ResourceBundle msg = ResourceBundle.getBundle("bosetterne_test", locale);
		
		spill.setMsg(msg);
		spill.setTypeSpill("BYERRIDDER");
		
		assertThat("", spill.getTypeSpill() ,is(equalTo("Byer og riddere")));
	}
	
	@Test
	public void testGetTypeSpillMsgNull() throws Exception {
		spill.setTypeSpill("BOSETTERNE");
		
		assertThat("", spill.getTypeSpill() ,is(equalTo("BOSETTERNE")));
	}
	

}
