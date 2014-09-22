package com.holtebu.bosetterne;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

/**
 * Simple test to ensure Internationalization is working.
 * @author espen
 *
 */
public class ResourceBundleTest {
	
	private Locale locale;
	
	private ResourceBundle bosetterneText;
	
	@Before
	public void setUp() {
		locale = new Locale("nb", "NO");
		bosetterneText = ResourceBundle.getBundle("bosetterne", locale);
	}
	
	@Test
	public void readsPropertiesFile(){
		System.out.println(bosetterneText.getString("test"));
	}
	
}
