package org.steamshaper.ai.puffafilm.etl.loader;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieGenre;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNGenre;

public class GenresLoader extends ALoader<GNGenre,EMovieGenre>{

	private static final GraphRepository<GNGenre> genresRepo =  Help.me.getNeo4jTemplate().repositoryFor(GNGenre.class);

	@Override
	protected GNGenre convert(EMovieGenre source) {
		GNGenre genre = genresRepo.findByPropertyValue("genre", source.getGenre());
		if(genre==null){
			genre = new GNGenre();
			genre.setGenre(source.getGenre());
			genre.setDescription("[GEN] "+source.getGenre());
		}
		return genre;
	}

	@Override
	protected GraphRepository<GNGenre> getRepository() {
		return GenresLoader.genresRepo;
	}

}
