package org.steamshaper.ai.puffafilm.etl.finder;

import org.steamshaper.ai.puffafilm.etl.loader.relationship.EntityFinder;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNActor;
import org.steamshaper.puffafilm.ai.repository.GNActorRepository;

public class ActorFinderOnOid extends EntityFinder<GNActor, String> {

	private final static GNActorRepository repo = Help.me.getContext().getBean(
			GNActorRepository.class);

	@Override
	public GNActor doFindNodeWithIDValue(String value) {
		GNActor o = repo.findByPropertyValue("oid", value);
		return o;
	}

}
