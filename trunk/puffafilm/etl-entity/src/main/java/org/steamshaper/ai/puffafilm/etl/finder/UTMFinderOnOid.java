package org.steamshaper.ai.puffafilm.etl.finder;

import org.steamshaper.ai.puffafilm.etl.loader.relationship.EntityFinder;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNRUserTagMovie;
import org.steamshaper.puffafilm.ai.repository.GNRUserTagMovieRepository;

public class UTMFinderOnOid extends EntityFinder<GNRUserTagMovie, String> {

	private final static GNRUserTagMovieRepository utmRepo = Help.me.getContext().getBean(
			GNRUserTagMovieRepository.class);

	public UTMFinderOnOid(){
		this.disableCache();
	}
	@Override
	public GNRUserTagMovie doFindNodeWithIDValue(String value) {
		GNRUserTagMovie o = utmRepo.findByPropertyValue("oid", value);
		return o;
	}

}
