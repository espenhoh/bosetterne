package com.holtebu.brettspill.service.resources.lobby;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static com.holtebu.bosetterne.TestConst.*;

import com.holtebu.brettspill.service.OAuth2Cred;
import com.holtebu.brettspill.service.auth.JDBILobbyService;
import com.holtebu.brettspill.service.auth.LobbyService;
import com.holtebu.brettspill.service.auth.sesjon.Polettlager;
import com.holtebu.brettspill.service.core.dao.LobbyDAO;
import io.dropwizard.auth.basic.BasicCredentials;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.holtebu.bosetterne.TestConst;
import com.holtebu.brettspill.api.lobby.Spill;
import com.holtebu.brettspill.api.lobby.Spiller;
import com.holtebu.brettspill.api.lobby.SpillerBuilder;
import com.holtebu.brettspill.service.auth.sesjon.PolettlagerIMinne;
import com.holtebu.brettspill.service.core.AccessToken;
import com.holtebu.brettspill.service.core.Legitimasjon;
import com.holtebu.brettspill.service.inject.LobbyCacheFactory;

public class OAuthAuthorizeResourceTest {

	

	private static final String STD_BRUKERNAVN = "brukernavn";
	private static final String STD_PASSORD = "passord";
	private static final String STD_RESPONSE_TYPE = "code";
	private static final String STD_REDIRECT = "redirectUri";
	private static final String STD_STATE = "state";
	private static final String STD_SCOPE = "BOSETTERNE";
	private static final String STD_AUTHCODE = UUID.randomUUID().toString();

	@Mock
	private LobbyDAO daoMock;

	private OAuth2Cred auth2Cred;

	private LobbyService<Optional<Spiller>, BasicCredentials> lobbyService;
	private Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore;

	Map<String, Spiller> accessTokens;
	Map<String, Legitimasjon> codes;

	private OAuthAuthorizeResource authResource;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		lobbyService = new JDBILobbyService(new LobbyCacheFactory(daoMock).provide(), new ConcurrentSkipListSet<Spill>(), daoMock);
		auth2Cred = new OAuth2Cred(TestConst.STD_CLIENTID, TestConst.STD_CLIENT_SECRET);
		accessTokens = new HashMap<>();
		codes = new HashMap<>();
		tokenStore = new PolettlagerIMinne(accessTokens, codes, auth2Cred);

		authResource = new OAuthAuthorizeResource(tokenStore, lobbyService,
				auth2Cred);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void spillerAutorisert() throws Exception {
		returnerStdSpiller();

		Response respons = authResource.login(STD_BRUKERNAVN, STD_PASSORD,
				STD_RESPONSE_TYPE, STD_REDIRECT, TestConst.STD_CLIENTID, STD_SCOPE,
				STD_STATE);

		assertThat("Spiller skal være autorisert.", respons.getStatus(),
				is(equalTo(Response.Status.SEE_OTHER.getStatusCode())));

		URI loc = (URI) respons.getMetadata().getFirst("Location");
		System.out.println(loc.getPath());

		assertThat("Path skal være " + STD_REDIRECT, loc.getPath(),
				is(equalTo(STD_REDIRECT)));

		Map<String, String> query = Splitter.on('&').trimResults()
				.withKeyValueSeparator('=').split(loc.getQuery());

		assertThat("Autorisasjonskode" + query.get("code")
				+ "skal finnes i hashmap",
				codes.containsKey(query.get("code")), is(true));
		assertThat("State skal være " + STD_STATE, query.get("state"),
				is(equalTo(STD_STATE)));
	}

	/** Given/When/Then syntax */
	@Test
	public void narSpilletIkkeKjennerClientIdSkalResponsenHaStatusUNAUTHORIZED() {
		returnerStdSpiller();

		Response respons = authResource.login(STD_BRUKERNAVN, STD_PASSORD,
				STD_RESPONSE_TYPE, STD_REDIRECT, "ukjent client_id", STD_SCOPE,
				STD_STATE);

		assertThat("Responsen skal ha status UNAUTHORIZED",
				respons.getStatus(),
				is(equalTo(Response.Status.UNAUTHORIZED.getStatusCode())));
	}

	/** Given/When/Then syntax */
	@Test
	public void narRedirectURIIkkeHarRettSyntaxSkalResponsenHaStatusUNAUTHORIZED() {
		returnerStdSpiller();

		Response respons = authResource.login(STD_BRUKERNAVN, STD_PASSORD,
				STD_RESPONSE_TYPE, "feil£\"||Syntakssssæøå", TestConst.STD_CLIENTID,
				STD_SCOPE, STD_STATE);

		assertThat("Responsen skal ha status UNAUTHORIZED",
				respons.getStatus(),
				is(equalTo(Response.Status.UNAUTHORIZED.getStatusCode())));
	}

	/** Given/When/Then syntax */
	@Test
	public void narSpilletIkkeKjennerBrukernavnSkalResponsenHaStatusNOT_FOUND() {
		String brukernavn = "Ikke kjent brukernavn";

		when(daoMock.finnSpillerVedNavn(isA(String.class))).thenReturn(null);

		Response respons = authResource.login(brukernavn, STD_PASSORD,
				STD_RESPONSE_TYPE, STD_REDIRECT, TestConst.STD_CLIENTID, STD_SCOPE,
				STD_STATE);

		assertThat("Responsen skal ha status NOT FOUND", respons.getStatus(),
				is(equalTo(Response.Status.NOT_FOUND.getStatusCode())));
	}

	/** Given/When/Then syntax */
	@Test
	public void narSpilletIkkeKjennerPassordSkalResponsenHaStatusNOT_FOUND() {
		String passord = "galtPassord";

		Spiller spiller = returnerStdSpiller();

		Response respons = authResource.login(STD_BRUKERNAVN, passord,
				STD_RESPONSE_TYPE, STD_REDIRECT, STD_CLIENTID, STD_SCOPE,
				STD_STATE);

		assertThat("Responsen skal ha status NOT_FOUND", respons.getStatus(),
				is(equalTo(Response.Status.NOT_FOUND.getStatusCode())));
	}

	@Test
	public void hvisResponstypeIkkeErCodeSkalResponsenHaStatusBAD_REQUEST() {
		returnerStdSpiller();

		Response respons = authResource.login(STD_BRUKERNAVN, STD_PASSORD,
				"RESPONSE_TYPE ikke code", STD_REDIRECT, STD_CLIENTID,
				STD_SCOPE, STD_STATE);

		assertThat("Responsen skal ha status BAD REQUEST", respons.getStatus(),
				is(equalTo(Response.Status.BAD_REQUEST.getStatusCode())));
	}

	@Test
	public void hvisScopeIkkeErImplementerteSpillSkalResponsenHaStatusBAD_REQUEST() {
		returnerStdSpiller();

		Response respons = authResource.login(STD_BRUKERNAVN, STD_PASSORD,
				STD_RESPONSE_TYPE, STD_REDIRECT, STD_CLIENTID, "Ikke "
						+ STD_SCOPE, STD_STATE);

		assertThat("Responsen skal ha status BAD REQUEST", respons.getStatus(),
				is(equalTo(Response.Status.BAD_REQUEST.getStatusCode())));
	}

	@Test
	public void testAuthCodeStdSpiller() throws Exception {
		returnerStdSpiller();

		String code = authResource.authCode(STD_BRUKERNAVN, STD_PASSORD,
				STD_RESPONSE_TYPE, STD_REDIRECT, STD_CLIENTID, STD_SCOPE,
				STD_STATE);

		assertThat("code skal ikke være null.", code, not(nullValue()));
	}
	
	@Test
	public void testAuthCodeIngenSpiller() throws Exception {
		String code = authResource.authCode(STD_BRUKERNAVN, STD_PASSORD,
				STD_RESPONSE_TYPE, STD_REDIRECT, STD_CLIENTID, STD_SCOPE,
				STD_STATE);

		assertThat("code skal være null.", code, nullValue());
	}
	
	@Test
	public void testAuthCodeIkkeImplementertSpill() throws Exception {
		returnerStdSpiller();

		String code = authResource.authCode(STD_BRUKERNAVN, STD_PASSORD,
				STD_RESPONSE_TYPE, STD_REDIRECT, STD_CLIENTID, "Ikke_" + STD_SCOPE,
				STD_STATE);

		assertThat("code skal være null.", code, nullValue());
	}
	
	@Test
	public void testAuthCodeIkkeKorrektResponseType() throws Exception {
		returnerStdSpiller();

		String code = authResource.authCode(STD_BRUKERNAVN, STD_PASSORD,
				"ikke " +STD_RESPONSE_TYPE, STD_REDIRECT, STD_CLIENTID, STD_SCOPE,
				STD_STATE);

		assertThat("code skal være null.", code, nullValue());
	}

	private Spiller returnerStdSpiller() {
		Spiller spiller = new SpillerBuilder().withBrukernavn(STD_BRUKERNAVN)
				.withPassord(STD_PASSORD).build();
		when(daoMock.finnSpillerVedNavn(isA(String.class))).thenReturn(spiller);

		return spiller;
	}

}
