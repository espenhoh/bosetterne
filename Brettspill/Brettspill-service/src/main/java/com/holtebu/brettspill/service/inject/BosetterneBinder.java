package com.holtebu.brettspill.service.inject;

import com.google.common.base.Optional;
import com.google.common.cache.LoadingCache;
import com.holtebu.brettspill.api.Bosetterne;
import com.holtebu.brettspill.api.lobby.Spill;
import com.holtebu.brettspill.api.lobby.Spiller;
import com.holtebu.brettspill.service.auth.BoardgameAuthenticator;
import com.holtebu.brettspill.service.auth.JDBILobbyService;
import com.holtebu.brettspill.service.auth.LobbyService;
import com.holtebu.brettspill.service.auth.sesjon.Polettlager;
import com.holtebu.brettspill.service.auth.sesjon.PolettlagerIMinne;
import com.holtebu.brettspill.service.core.AccessToken;
import com.holtebu.brettspill.service.core.Legitimasjon;
import com.holtebu.brettspill.service.resources.HelloWorldResource;
import com.holtebu.brettspill.service.resources.OAuthAccessTokenResource;
import com.holtebu.brettspill.service.resources.games.BosetterneResource;
import com.holtebu.brettspill.service.resources.lobby.LobbyResource;
import com.holtebu.brettspill.service.resources.lobby.OAuthAuthorizeResource;
import com.holtebu.brettspill.service.resources.lobby.RegistrerResource;
import io.dropwizard.auth.basic.BasicCredentials;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Espen on 15.04.2017.
 */
public class BosetterneBinder extends AbstractBinder {

    private final Integer INIT_ANTALL_SPILL = 20;

    @Override
    protected void configure() {
        bindClasses();

        bind(ResourceBundle.getBundle("bosetterne")).to(ResourceBundle.class);
        bind(ConcurrentHashMap.class).to(new TypeLiteral<Map<String, Spiller>>(){}).named("tokens");
        bind(ConcurrentHashMap.class).to(new TypeLiteral<Map<String, Legitimasjon>>(){}).named("codes");
        bind(ConcurrentSkipListSet.class).to(new TypeLiteral<Set<Spill>>() {}).named("spillCache");
        bind(PolettlagerIMinne.class).to(new TypeLiteral<Polettlager<AccessToken, Spiller, Legitimasjon, String>>(){}).in(Singleton.class);

        //Cache til JDBILobbyservice
        bindFactory(LobbyCacheFactory.class).to(new TypeLiteral<LoadingCache<String, Optional<Spiller>>>(){});
        bind(JDBILobbyService.class).to(new TypeLiteral<LobbyService<Optional<Spiller>, BasicCredentials>>(){}).in(Singleton.class);

        bind("ingenting").to(String.class).named("getit");

        //Bosetterne binding
        bind(Random.class).to(Random.class);
        bind(INIT_ANTALL_SPILL).to(Integer.class).named("antallSpill");
    }

    private void bindClasses() {
        bind(HelloWorldResource.class).to(HelloWorldResource.class).in(Singleton.class);
        bind(OAuthAccessTokenResource.class).to(OAuthAccessTokenResource.class).in(Singleton.class);
        bind(OAuthAuthorizeResource.class).to(OAuthAuthorizeResource.class).in(Singleton.class);
        bind(LobbyResource.class).to(LobbyResource.class).in(Singleton.class);
        bind(RegistrerResource.class).to(RegistrerResource.class).in(Singleton.class);
        bind(BosetterneResource.class).to(BosetterneResource.class).in(Singleton.class);
        bind(Bosetterne.class).to(Bosetterne.class).in(Singleton.class);
        bind(BoardgameAuthenticator.class).to(BoardgameAuthenticator.class).in(Singleton.class);
        bind(AtomicLong.class).to(AtomicLong.class);
    }
}
