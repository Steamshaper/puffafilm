package org.steamshaper.ai.runtime.service;

import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.ai.runtime.AService;

public class DBWebAdmin extends AService {

	private boolean webAdminStarted = false;

	public void run() {
		if (this.shouldRun()) {
		if (!webAdminStarted) {
			Help.me.toStartN4jWebAdmin();
			webAdminStarted = true;
		} else {
			Help.me.toStopN4jWebAdmin();
			webAdminStarted = false;
		}
		} else {
			log.debug("NEO4J WEB ADMIN - IGNORED!");
		}

	}

	private Condition shouldRun;

	public boolean shouldRun() {
		if (shouldRun != null) {
			return shouldRun.isConditionTrue();
		}
		return true;
	}

	public void setShouldRun(Condition shouldRun) {
		this.shouldRun = shouldRun;
	}


}
