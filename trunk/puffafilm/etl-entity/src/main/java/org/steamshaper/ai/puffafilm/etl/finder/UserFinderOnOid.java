package org.steamshaper.ai.puffafilm.etl.finder;

import org.steamshaper.ai.puffafilm.etl.loader.relationship.EntityFinder;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNUser;
import org.steamshaper.puffafilm.ai.repository.GNUserRepository;

public class UserFinderOnOid extends EntityFinder<GNUser, Long> {

	private final static GNUserRepository repoUser = Help.me.getContext().getBean(
			GNUserRepository.class);

	@Override
	public GNUser doFindNodeWithIDValue(Long value) {
		GNUser o = repoUser.findByPropertyValue("oid", value);
		return o;
	}

}
