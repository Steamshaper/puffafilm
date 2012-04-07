package org.steamshaper.ai.puffafilm.etl.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieDirector;

public class DistinctDirectorsFilter {
	final static Logger log = Logger.getLogger(DistinctDirectorsFilter.class);

	public static List<EMovieDirector> distinctDirectors(List<EMovieDirector> directorsList) {
		HashSet<String> set = new HashSet<String>(directorsList.size()/2);
		List<EMovieDirector> o =  new ArrayList<EMovieDirector>(directorsList.size()/2);
		for(EMovieDirector director :  directorsList){
			if(!set.contains(director.getDirectorID())){
				set.add(director.getDirectorID());
				o.add(director);
			}
		}
		log.info("Director List filterd for distinct director IN ["+directorsList.size()+"] duplicate found ["+(directorsList.size()-o.size())+"] OUT ["+o.size()+"]");
		return o;
	}

}
