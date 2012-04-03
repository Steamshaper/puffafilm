package org.steamshaper.ai.puffafilm.etl.finder;

import org.steamshaper.ai.puffafilm.etl.loader.relationship.EntityFinder;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNLocation;
import org.steamshaper.puffafilm.ai.repository.GNLocationRepository;

public class LocationFinderOnOid extends EntityFinder<GNLocation, String> {

	private final static GNLocationRepository localtionRepo =  Help.me.getContext().getBean(GNLocationRepository.class);
	@Override
	public GNLocation doFindNodeWithIDValue(String value) {
		GNLocation o = localtionRepo.findByPropertyValue("location", value);
		return o;
	}

}
