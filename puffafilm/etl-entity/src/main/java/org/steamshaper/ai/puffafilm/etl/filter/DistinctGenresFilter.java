package org.steamshaper.ai.puffafilm.etl.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieGenre;

public class DistinctGenresFilter {
	final static Logger log = Logger.getLogger(DistinctGenresFilter.class);

	public static List<EMovieGenre> distinctGenres(List<EMovieGenre> genresList) {
		HashSet<String> set = new HashSet<String>(genresList.size()/2);
		List<EMovieGenre> o =  new ArrayList<EMovieGenre>(genresList.size()/2);
		for(EMovieGenre genre :  genresList){
			if(!set.contains(genre.getGenre())){
				set.add(genre.getGenre());
				o.add(genre);
			}
		}
		log.info("Genres List filterd for distinct genre IN ["+genresList.size()+"] duplicate found ["+(genresList.size()-o.size())+"] OUT ["+o.size()+"]");
		return o;
	}

}
