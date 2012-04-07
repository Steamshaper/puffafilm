package org.steamshaper.ai.puffafilm.etl.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieActor;

public class DistinctActorFilter {
	final static Logger log = Logger.getLogger(DistinctActorFilter.class);

	public static List<EMovieActor> distinctActor(List<EMovieActor> actorList) {
		HashSet<String> set = new HashSet<String>(actorList.size()/2);
		List<EMovieActor> o =  new ArrayList<EMovieActor>(actorList.size()/2);
		for(EMovieActor actor :  actorList){
			if(!set.contains(actor.getActorID())){
				set.add(actor.getActorID());
				o.add(actor);
			}
		}
		log.info("Actor List filterd for distinct actorID IN ["+actorList.size()+"] duplicate found ["+(actorList.size()-o.size())+"] OUT ["+o.size()+"]");
		return o;
	}

}
