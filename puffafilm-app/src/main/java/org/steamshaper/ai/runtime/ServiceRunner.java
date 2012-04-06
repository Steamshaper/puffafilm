package org.steamshaper.ai.runtime;

import org.steamshaper.ai.puffafilm.util.Help;

public class ServiceRunner {
	private ServiceRegister srvReg;

	public ServiceRunner() {
		srvReg = Help.me.getContext().getBean(ServiceRegister.class);
	}

	public void goGoGo(){
		srvReg.runOnStartService();
		srvReg.runMainService();
		srvReg.runOnExitService();
	}

}
