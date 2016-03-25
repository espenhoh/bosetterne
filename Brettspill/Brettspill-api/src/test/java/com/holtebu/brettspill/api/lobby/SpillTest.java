package com.holtebu.brettspill.api.lobby;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.hamcrest.number.*;
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
		assertThat("", spill.isStartet(), is(false));
		assertThat("", spill.isFullfort(), is(false));
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
    public void datoFomFormat() {
        Calendar date = new GregorianCalendar(2011, 3, 7, 23, 30, 56);
        Date datoFom = date.getTime();
        spill.setDatoFom(datoFom);
        assertThat("Dato fom skal ha spesifikt format \"dd/mm HH:MM\"",spill.getDatoFomStr(),is(equalTo("07/04 23:30")));
    }

    @Test
    public void datoFomNull() {
        spill.setDatoFom(null);
        assertThat("Dato fom skal være null",spill.getDatoFomStr(),is(nullValue()));
    }

    @Test
    public void datoTomFormat() {
        Calendar date = new GregorianCalendar(2011, 3, 7, 23, 30, 56);
        Date datoTom = date.getTime();
        spill.setDatoTom(datoTom);
        assertThat("Dato tom skal ha spesifikt format \"dd/mm HH:MM\"",spill.getDatoTomStr(),is(equalTo("07/04 23:30")));
    }

    @Test
    public void datoTomNull() {
        spill.setDatoTom(null);
        assertThat("Dato tom skal være null",spill.getDatoFomStr(),is(nullValue()));
    }
	
	@Test
	public void testGetTypeSpillMsgNull() throws Exception {
		spill.setTypeSpill("BOSETTERNE");
		
		assertThat("", spill.getTypeSpill() ,is(equalTo("BOSETTERNE")));
	}

	@Test
	public void testCompareToEqualNotStarted() throws Exception {
        Spill spill2 = new Spill();
        spill.setSpillId(1);
        spill.setNavn("test");
        spill.setDatoFom(new Date());
        spill.setDatoTom(new Date());
		spill2.setSpillId(spill.getSpillId());
        spill2.setNavn("tuller du??");
        spill2.setDatoFom(spill.getDatoFom());
        spill2.setDatoTom(spill.getDatoTom());

		assertThat("Spill2 og spill skulle vært samme objekt", spill2.compareTo(spill), is(equalTo(0)));
	}


	
	@Test
	public void id1LessThanid2WhenDatoFomNull() throws Exception {
        Spill spill2 = new Spill();
        spill.setSpillId(1);
        spill.setNavn("test");
        spill.setDatoFom(null);
        spill.setDatoTom(null);
        spill2.setSpillId(2);
        spill2.setNavn(spill.getNavn());
        spill2.setDatoFom(spill.getDatoFom());
        spill2.setDatoTom(spill.getDatoTom());

		assertThat("spill (id = 1) < spill2 (id = 2)", spill, is(OrderingComparison.lessThan(spill2)));
        assertThat("spill2 (id = 2) > spill (id = 1)", spill2, is(OrderingComparison.greaterThan(spill)));
	}

    @Test
    public void notStartedLessThanStarted() throws Exception {
        Spill spill2 = new Spill();
        spill.setSpillId(200);
        spill.setNavn("test");
        spill.setDatoFom(null);
        spill.setDatoTom(null);
        spill2.setSpillId(3);
        spill2.setNavn(spill.getNavn());
        spill2.setDatoFom(new Date());
        spill2.setDatoTom(spill.getDatoTom());

        assertThat("Spill (setDatoFom(null)) < spill2 setDatoFom(new Date())", spill, is(OrderingComparison.lessThan(spill2)));
        assertThat("Spill2 (setDatoFom(new Date())) > spill setDatoFom(null)", spill2, is(OrderingComparison.greaterThan(spill)));
    }

    @Test
    public void notStartedLessThanEnded() throws Exception {
        Spill spill2 = new Spill();
        spill.setSpillId(20);
        spill.setNavn("test");
        spill.setDatoFom(null);
        spill.setDatoTom(null);
        spill2.setSpillId(3);
        spill2.setNavn(spill.getNavn());
        spill2.setDatoFom(new Date());
        spill2.setDatoTom(new Date());


        assertThat("Spill (not started) < spill2 (ended)", spill, is(OrderingComparison.lessThan(spill2)));
        assertThat("spill2 (ended) > Spill (not started)", spill2, is(OrderingComparison.greaterThan(spill)));
    }

    @Test
    public void startedLessThanEnded() throws Exception {
        Spill spill2 = new Spill();
        spill2.setDatoFom(new Date());
        spill.setSpillId(10);
        spill.setNavn("test");
        spill.setDatoFom(new Date());
        spill.setDatoTom(null);
        spill2.setSpillId(3);
        spill2.setNavn(spill.getNavn());
        spill2.setDatoTom(new Date());


        assertThat("Spill (started) < spill2 (ended)", spill, is(OrderingComparison.lessThan(spill2)));
        assertThat("spill2 (ended) > Spill (started)", spill2, is(OrderingComparison.greaterThan(spill)));
    }

    @Test
    public void startedLessThanstarted() throws Exception {
        Spill spill2 = new Spill();

        spill.setSpillId(10);
        spill.setNavn("test");
        spill.setDatoFom(new Date());
        spill.setDatoTom(null);
        spill2.setSpillId(30);
        spill2.setNavn(spill.getNavn());
        spill2.setDatoTom(spill.getDatoTom());
        spill2.setDatoFom(spill.getDatoFom());

        assertThat("Spill (ended) < spill2 (ended)", spill, is(OrderingComparison.lessThan(spill2)));
        assertThat("Spill2 (ended) > spill (ended)", spill2, is(OrderingComparison.greaterThan(spill)));
    }

    @Test
    public void endedLessThanEnded() throws Exception {
        Spill spill2 = new Spill();

        spill.setSpillId(10);
        spill.setNavn("test");
        spill.setDatoFom(new Date());
        spill.setDatoTom(new Date());
        spill2.setSpillId(30);
        spill2.setNavn(spill.getNavn());
        spill2.setDatoTom(spill.getDatoTom());
        spill2.setDatoFom(spill.getDatoFom());

        assertThat("Spill (ended) < spill2 (ended)", spill, is(OrderingComparison.lessThan(spill2)));
        assertThat("Spill2 (ended) > spill (ended)", spill2, is(OrderingComparison.greaterThan(spill)));
    }

}
