package com.holtebu.bosetterne;

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

}
