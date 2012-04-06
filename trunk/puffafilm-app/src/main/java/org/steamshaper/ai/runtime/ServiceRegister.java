package org.steamshaper.ai.runtime;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.steamshaper.ai.puffafilm.util.Help;

public class ServiceRegister {

	private final Logger log =  Logger.getLogger("service.register");
	private List<AService> shouldRunOnStart = new ArrayList<AService>();

	private List<AService> shouldRunOnExit = new ArrayList<AService>();

	private AService mainThread = null;

	public void runOnStartService() {
		log.info("Attempt to run ON Start Services... ["+shouldRunOnStart.size()+"]");
		int i=1;
		int sSize = shouldRunOnStart.size();
		for (AService srv : shouldRunOnStart) {
			log.debug("Starting " + srv);
			srv.run();
			log.info("["+i+" of "+sSize+"] Start of "+srv+" Completed");
			i++;
		}
		log.info("Attempt to run ON Start Service DONE !");
	}

	public void runOnExitService() {
		log.info("Attempt to run ON Exit Services... ["+shouldRunOnExit.size()+"]");
		int i=1;
		int sSize = shouldRunOnExit.size();
		for (AService srv : shouldRunOnExit) {
			log.debug("Starting " + srv);
			srv.run();
			log.info("["+i+" of "+sSize+"] Start of "+srv+" Completed");
			i++;
		}
		log.info("Attempt to run ON Exit Service DONE !");
	}

	public void runMainService() {
		Help.me.flushSaveBuffer();
		if (mainThread != null) {
			log.info("Attempt to run MAIN service ..."+mainThread);
			mainThread.run();
		}else{
			log.info("No MAIN service setted!");
		}
		
	}

	public List<AService> getShouldRunOnStart() {
		return shouldRunOnStart;
	}

	public void setShouldRunOnStart(List<AService> shouldRunOnStart) {
		this.shouldRunOnStart = shouldRunOnStart;
	}

	public List<AService> getShouldRunOnExit() {
		return shouldRunOnExit;
	}

	public void setShouldRunOnExit(List<AService> shouldRunOnExit) {
		this.shouldRunOnExit = shouldRunOnExit;
	}

	public AService getMainThread() {
		return mainThread;
	}

	public void setMainThread(AService mainThread) {
		this.mainThread = mainThread;
	}

}
