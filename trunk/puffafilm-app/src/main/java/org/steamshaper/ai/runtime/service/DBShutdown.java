package org.steamshaper.ai.runtime.service;

import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.ai.runtime.AService;

public class DBShutdown extends AService {

	@Override
	public void run() {
		Help.me.toShutdownNeo4jDB();

	}

}
