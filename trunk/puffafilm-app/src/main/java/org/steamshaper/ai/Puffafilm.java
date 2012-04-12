package org.steamshaper.ai;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.xml.DOMConfigurator;
import org.steamshaper.ai.puffafilm.etl.KnowledgeExtractor;
import org.steamshaper.ai.puffafilm.starter.Starter;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.ai.runtime.ServiceRunner;

public class Puffafilm {

    private static Logger log ;

    public static void main(String[] args) throws IOException {
        Help.me.storeArgs(args);
    	configureLogging();
        log = Logger.getLogger("service.entry");
        log.info("Attempt to initializing Spring Contexts...");
        String[] contexts = new String[] { "spring/runtimeComponents.ctx.xml","spring/neo4j.cfg.ctx.xml" };
        Starter.getInstance().start(contexts);
        log.info("Attempt to initializing Spring Contexts DONE "+contexts);

        ServiceRunner runner = new ServiceRunner();
        KnowledgeExtractor.getInstance().setDatFileHomePath(System.getProperty("user.dir")+"/dataset/");
        runner.goGoGo();
        log.info("Application exit!");
        System.exit(0);

    }


    private static void configureLogging() {
        File logConfig = new File("logging/log4jConfig.xml");
        System.err.println("Log file: " + logConfig.getAbsolutePath());
        if (logConfig.exists()) {
            System.err.println("Log config found!");
            DOMConfigurator.configure("logging/log4jConfig.xml");
        } else {
            System.err.println("Opps ... log confing doesn't exists!");
            System.err
                    .println("Logging will be configured on DEFAULT SETTINGS!!!");
            BasicConfigurator.configure();
            Logger.getRootLogger().setLevel(Level.DEBUG);
            @SuppressWarnings("unchecked")
			Enumeration<Appender> appenders = Logger.getRootLogger()
                    .getAllAppenders();

            while (appenders.hasMoreElements()) {
                Appender currentAppender = appenders.nextElement();
                System.err.println("Changing conversion pattern for : "
                        + currentAppender.getName());
                currentAppender
                        .setLayout(new PatternLayout(
                                "[%d{MMM dd yyyy HH:mm:ss,SSS}] [%-5p] [%c{3} %x - %m]%n"));
            }

        }
    }
}
