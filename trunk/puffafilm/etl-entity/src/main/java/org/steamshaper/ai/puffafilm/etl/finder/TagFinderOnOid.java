package org.steamshaper.ai.puffafilm.etl.finder;

import org.steamshaper.ai.puffafilm.etl.loader.relationship.EntityFinder;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNTag;
import org.steamshaper.puffafilm.ai.repository.GNTagsRepository;

public class TagFinderOnOid extends EntityFinder<GNTag, Long> {
	private final static GNTagsRepository tagRepo =  Help.me.getContext().getBean(GNTagsRepository.class);
	
	
	@Override
	public GNTag doFindNodeWithIDValue(Long value) {
		GNTag o = tagRepo.findByPropertyValue("oid", value);
		return o;
	}
	

}
