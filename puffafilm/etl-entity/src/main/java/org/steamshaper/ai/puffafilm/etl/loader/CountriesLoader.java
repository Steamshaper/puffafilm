package org.steamshaper.ai.puffafilm.etl.loader;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieCountry;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNCountry;

public class CountriesLoader extends ALoader<GNCountry,EMovieCountry>{

	private static final GraphRepository<GNCountry> coutriesRepo =  Help.me.getNeo4jTemplate().repositoryFor(GNCountry.class);

	@Override
	protected GNCountry convert(EMovieCountry source) {
		GNCountry country = coutriesRepo.findByPropertyValue("name", source.getCountry());
		if(country==null){
			country = new GNCountry();
			country.setName(source.getCountry());
			country.setDescription("[COU] "+source.getCountry());
		}
		return country;
	}

	@Override
	protected GraphRepository<GNCountry> getRepository() {
		return CountriesLoader.coutriesRepo;
	}

}
