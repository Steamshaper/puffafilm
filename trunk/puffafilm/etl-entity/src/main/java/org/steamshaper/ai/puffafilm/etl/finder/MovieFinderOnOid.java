package org.steamshaper.ai.puffafilm.etl.finder;

import org.steamshaper.ai.puffafilm.etl.loader.relationship.EntityFinder;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.repository.GNMovieRepository;

public class MovieFinderOnOid extends EntityFinder<GNMovie, Long> {

	private final static GNMovieRepository movieRepo =  Help.me.getContext().getBean(GNMovieRepository.class);
	@Override
	public GNMovie doFindNodeWithIDValue(Long value) {
		GNMovie o = movieRepo.findByPropertyValue("oid", value);
		return o;
	}

}
