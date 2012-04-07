package org.steamshaper.ai.puffafilm.etl.loader;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.entity.EUserTaggedMovie;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNRUserTagMovie;

public class UTMLoader extends ALoader<GNRUserTagMovie,EUserTaggedMovie>{

	private static final GraphRepository<GNRUserTagMovie> utmRepo =  Help.me.getNeo4jTemplate().repositoryFor(GNRUserTagMovie.class);

	@Override
	protected GNRUserTagMovie convert(EUserTaggedMovie source) {
		GNRUserTagMovie utm = new GNRUserTagMovie();
			utm.setOid("u"+source.getUserID()+"t"+source.getTagID()+"m"+source.getMovieID());
			utm.setTimastamp(source.getTimestamp());
			utm.setDescription("[UTM] u"+source.getUserID()+"t"+source.getTagID()+"m"+source.getMovieID());
		return utm;
	}

	@Override
	protected GraphRepository<GNRUserTagMovie> getRepository() {
		return UTMLoader.utmRepo;
	}

}
