package org.steamshaper.ai.puffafilm.etl.loader;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.filter.entity.EUser;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNUser;

public class UserLoader extends ALoader<GNUser,EUser>{

	private static final GraphRepository<GNUser> usersRepo =  Help.me.getNeo4jTemplate().repositoryFor(GNUser.class);

	@Override
	protected GNUser convert(EUser source) {
		GNUser user = usersRepo.findByPropertyValue("oid", source.getId());
		if(user==null){
			user = new GNUser();
			user.setOid(source.getId());
			user.setDescription("[USR] oid:"+source.getId());
		}
		return user;
	}

	@Override
	protected GraphRepository<GNUser> getRepository() {
		return UserLoader.usersRepo;
	}

}
