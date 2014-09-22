package com.holtebu.bosetterne;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.holtebu.bosetterne.service.BosetterneConfiguration;
import com.holtebu.bosetterne.service.ConfigurationStub;
import com.holtebu.bosetterne.service.resources.lobby.LoggUtResourceTest;

@RunWith(Suite.class)
@SuiteClasses({ LoggUtResourceTest.class })
public class BosetterneConfigurationSuite {
	
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
			System.out.println("Finished working with BosetterneConfiguration");
		}
	};

}
