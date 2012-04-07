package org.steamshaper.ai.puffafilm.etl.finder;

import org.steamshaper.ai.puffafilm.etl.loader.relationship.EntityFinder;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNGenre;
import org.steamshaper.puffafilm.ai.repository.GNGenreRepository;

public class GenreFinderOnOid extends EntityFinder<GNGenre, String> {

	private final static GNGenreRepository repo = Help.me.getContext().getBean(
			GNGenreRepository.class);
	@Override
	public GNGenre doFindNodeWithIDValue(String value) {
		GNGenre o = repo.findByPropertyValue("genre", value);
		return o;
	}

}
