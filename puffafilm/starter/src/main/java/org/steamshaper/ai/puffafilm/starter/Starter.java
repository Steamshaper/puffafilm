package org.steamshaper.ai.puffafilm.starter;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.steamshaper.ai.puffafilm.util.Help;

public class Starter {

	private static Logger log = null;
	private static Starter singletonInstance = null;

	public static Starter getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new Starter();
		}
		return singletonInstance;
	}

	private Starter() {

	}

	public void start(String[] contexts) {
		if (contexts == null) {
			contexts = new String[] {};
		}

		System.err.println("Starting application ...");

		getLogger();

		initSpringContext(contexts);


	}

	private String[] initSpringContext(String[] contexts) {
		log.info("Loading spring context...");

		log.info("Loading follows context ...");
		for (int i = 0; i < contexts.length; i++) {
			log.info((i + 1) + ". [" + contexts[i] + "]");
		}
		ApplicationContext context = new ClassPathXmlApplicationContext(
				contexts);

		if (context instanceof ConfigurableApplicationContext) {
			log.debug("Register shutdown hook, for spring context!");
			((ConfigurableApplicationContext) context).registerShutdownHook();
		}

		Help.me.setContext(context);

		log.info("Loading spring context DONE!");
		return contexts;

	}

	private void getLogger() {
		log = Logger.getLogger(Starter.class);

	}



}
