package org.steamshaper.ai.puffafilm.etl.finder;

import org.steamshaper.ai.puffafilm.etl.loader.relationship.EntityFinder;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNDirector;
import org.steamshaper.puffafilm.ai.repository.GNDirectorRepository;

public class DirectorFinderOnOid extends EntityFinder<GNDirector,String>{
	private final static GNDirectorRepository repo = Help.me.getContext().getBean(
			GNDirectorRepository.class);

	@Override
	public GNDirector doFindNodeWithIDValue(String value) {
		GNDirector director = repo.findByPropertyValue("oid", value);
		return director;
	}


}
