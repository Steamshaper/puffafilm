package org.steamshaper.ai.runtime.service;

import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.ai.runtime.AService;

public class DBDeleter extends AService {

	public void run() {
		if (shouldRun()) {
			Help.me.toDeleteGraphDBFiles();
		} else {
			log.debug("DB DATA FILE NOT ERASED !");
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
