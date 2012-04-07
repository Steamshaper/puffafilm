package org.steamshaper.ai.puffafilm.etl.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieCountry;

public class DistinctCountryFilter {
	final static Logger log = Logger.getLogger(DistinctCountryFilter.class);

	public static List<EMovieCountry> distinctCountries(List<EMovieCountry> countryList) {
		HashSet<String> set = new HashSet<String>(countryList.size()/2);
		List<EMovieCountry> o =  new ArrayList<EMovieCountry>(countryList.size()/2);
		for(EMovieCountry country :  countryList){
			if(!set.contains(country.getCountry())){
				set.add(country.getCountry());
				o.add(country);
			}
		}
		log.info("Country List filterd for distinct country IN ["+countryList.size()+"] duplicate found ["+(countryList.size()-o.size())+"] OUT ["+o.size()+"]");
		return o;
	}

}
