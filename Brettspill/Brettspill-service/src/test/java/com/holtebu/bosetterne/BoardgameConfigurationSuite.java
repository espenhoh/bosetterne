package com.holtebu.bosetterne;

import org.glassfish.hk2.api.ServiceLocator;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.holtebu.brettspill.service.BosetterneConfiguration;
import com.holtebu.brettspill.service.ConfigurationStub;
import com.holtebu.brettspill.service.auth.BoardgameAuthenticatorTestSuiteCase;
import com.holtebu.brettspill.service.resources.lobby.LoggUtResourceTestSuiteCase;

@RunWith(Suite.class)
@SuiteClasses({ LoggUtResourceTestSuiteCase.class, BoardgameAuthenticatorTestSuiteCase.class })
public class BoardgameConfigurationSuite {
	
	public static BosetterneConfiguration conf;

	public static ServiceLocator locator;

	@ClassRule
	public static ExternalResource confResource = new ExternalResource() {
		@Override
		protected void before() throws Throwable {
			conf = ConfigurationStub.getConf();
			System.out.println("BosetterneConfiguration read");
		}

		@Override
		protected void after() {
			conf = null;
			System.out.println("Finished working with BosetterneConfiguration");
		}
	};

	@ClassRule
	public static ExternalResource locatorResource = new ExternalResource() {
		@Override
		protected void before() throws Throwable {
			locator = ConfigurationStub.getLocator();
			System.out.println("Locator made");
		}

		@Override
		protected void after() {
			locator = null;
			System.out.println("Finished working with ServiceLocator");
		}
	};

}
