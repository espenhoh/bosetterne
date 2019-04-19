package com.holtebu.brettspill.service.inject;

import com.google.common.base.Optional;
import com.google.common.cache.LoadingCache;
import com.holtebu.brettspill.api.Bosetterne;
import com.holtebu.brettspill.api.lobby.Spill;
import com.holtebu.brettspill.api.lobby.Spiller;
import com.holtebu.brettspill.service.BosetterneConfiguration;
import com.holtebu.brettspill.service.OAuth2Cred;
import com.holtebu.brettspill.service.auth.BoardgameAuthenticator;
import com.holtebu.brettspill.service.auth.BosetterneAuthorizer;
import com.holtebu.brettspill.service.auth.sesjon.Polettlager;
import com.holtebu.brettspill.service.auth.sesjon.PolettlagerIMinne;
import com.holtebu.brettspill.service.core.AccessToken;
import com.holtebu.brettspill.service.core.Legitimasjon;
import com.holtebu.brettspill.service.core.dao.LobbyDAO;
import com.holtebu.brettspill.service.resources.games.BosetterneResource;
import com.holtebu.brettspill.service.resources.HelloWorldResource;
import com.holtebu.brettspill.service.resources.OAuthAccessTokenResource;
import com.holtebu.brettspill.service.resources.lobby.LobbyResource;
import com.holtebu.brettspill.service.resources.lobby.OAuthAuthorizeResource;
import com.holtebu.brettspill.service.resources.lobby.RegistrerResource;
import com.holtebu.brettspill.service.auth.JDBILobbyService;
import com.holtebu.brettspill.service.auth.LobbyService;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.skife.jdbi.v2.DBI;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;

public class BosetterneServiceBinder extends BosetterneBinder {
	private final DBIFactory dbiFactory;
	private BosetterneConfiguration configuration;

	private Environment environment;
	private DBI jdbi;
	private DAOFactory daoFactory;
	private Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore;

	@Inject
	public BosetterneServiceBinder(final DBIFactory factory) {
		dbiFactory = factory;
	}

    public BosetterneServiceBinder(DBIFactory dbiFactory, BosetterneConfiguration configuration, Environment environment) {
        this(dbiFactory);
        setUpEnv(configuration, environment);
    }

    public void setUpEnv(BosetterneConfiguration configuration, Environment environment) {
		this.configuration = configuration;
		this.environment = environment;
		this.jdbi = dbiFactory.build(environment, configuration.getDataSourceFactory(), "mySQL");
		this.daoFactory = new DAOFactory(jdbi);
	}

	@Override
	protected void configure() {
        super.configure();

		//DAO bindingK,
		bindFactory(daoFactory.lobbyDAOFactory()).to(LobbyDAO.class);
		bind(configuration).to(BosetterneConfiguration.class);
		bind(environment).to(Environment.class);
		bind(configuration.getOauth2()).to(OAuth2Cred.class);
	}



    public OAuthCredentialAuthFilter filter(){
		return new OAuthCredentialAuthFilter.Builder<Spiller>()
                .setAuthenticator(new BoardgameAuthenticator(tokenStore))
                .setRealm("Bosetterne")
                .setPrefix("Bearer")
                .setAuthorizer(new BosetterneAuthorizer())
                .buildAuthFilter();
	}



}
