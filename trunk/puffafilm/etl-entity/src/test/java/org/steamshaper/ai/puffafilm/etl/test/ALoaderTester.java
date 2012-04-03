package org.steamshaper.ai.puffafilm.etl.test;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.steamshaper.ai.puffafilm.util.Help;


public class ALoaderTester {
	@Autowired
	static ApplicationContext applicationContext;


	@BeforeClass
	public static void setUpContext(){

		String[] contexts  = new String[]{"spring/etl.test.cfg.ctx.xml"};
		if(applicationContext==null){
		applicationContext= new ClassPathXmlApplicationContext(
					contexts);
		}

			if (applicationContext instanceof ConfigurableApplicationContext) {
				((ConfigurableApplicationContext) applicationContext).registerShutdownHook();
			}

			Help.me.setContext(applicationContext);
	}

	@Before
	public  void setUp() throws Exception {
		setupDefaultLog();


	}

	@AfterClass
	public static void tearDown() throws Exception {
		Help.me.toCleanN4JDatabase();
	//	Help.me.toDeleteAllFileInAFolder(System.getProperty ("user.dir")+"/neo4j-data-test/");
	}

	private static void setupDefaultLog() {
		System.err.println("Logging will be configured on DEFAULT SETTINGS!!!");
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.DEBUG);
//		@SuppressWarnings("unchecked")
//		Enumeration<Appender> appenders = Logger.getRootLogger()
//				.getAllAppenders();
//
//		while (appenders.hasMoreElements()) {
//			Appender currentAppender = appenders.nextElement();
//			System.err.println("Changing conversion pattern for : "
//					+ currentAppender.getName());
//			currentAppender.setLayout(new PatternLayout(
//					"[%d{MMM dd yyyy HH:mm:ss,SSS}] [%-5p] [%c{3} %x - %m]%n"));
//		}

	}



}
