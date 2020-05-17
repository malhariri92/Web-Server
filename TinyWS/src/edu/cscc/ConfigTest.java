package edu.cscc;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * CSCI-2469 Java II Group Project
 * design and implement a tiny web server
 * date 20200406
 * @author Kelly Waddell
 * @author Mutasem Alhariri 
 * @author Robert Doult
 * @since 20200406
 * @version 1.5  
 */ 

public class ConfigTest {

	@Test
	public void testConfig() {
		Config config = new Config();
		config.dumpProperties();
		assertNotNull("Missing port", config.getProperty(Config.PORT));
		assertNotNull("Missing HTML folder", config.getProperty(Config.DEFAULTFOLDER));
		assertNotNull("Missing default page", config.getProperty(Config.DEFAULTPAGE));
	}
}