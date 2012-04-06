package org.steamshaper.ai.runtime;

import org.apache.log4j.Logger;

public abstract class AService implements Runnable {

	private String name ="Unknow Service Name ##!!";
	protected Logger log =  Logger.getLogger("service.impl.service");

	public AService(){
		log.trace("Building service...");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Service ["+name+"]" ;
	}




}
