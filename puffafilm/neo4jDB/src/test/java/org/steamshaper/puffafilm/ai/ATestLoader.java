package org.steamshaper.puffafilm.ai;

import java.io.File;
import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:spring/neo4j.test.cfg.ctx.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class ATestLoader {

	public static String[] contexts = new String[] { "spring/neo4j.test.cfg.ctx.xml" };
	public ApplicationContext testContext = null;

	@Before
	public void setUp() throws Exception {
		setupDefaultLog();

	}

	private void setupDefaultLog() {
		System.err.println("Logging will be configured on DEFAULT SETTINGS!!!");
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.DEBUG);
		Enumeration<Appender> appenders = Logger.getRootLogger()
				.getAllAppenders();

		while (appenders.hasMoreElements()) {
			Appender currentAppender = appenders.nextElement();
			System.err.println("Changing conversion pattern for : "
					+ currentAppender.getName());
			currentAppender.setLayout(new PatternLayout(
					"[%d{MMM dd yyyy HH:mm:ss,SSS}] [%-5p] [%c{3} %x - %m]%n"));
		}

	}

	public ApplicationContext getTestContext() {
		return testContext;
	}

	@After
	public void tearDown() throws Exception {

	}


}
