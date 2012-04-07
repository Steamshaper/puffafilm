package org.steamshaper.ai.puffafilm.etl.loader;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieActor;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNActor;

public class ActorsLoader extends ALoader<GNActor,EMovieActor>{

	private static final GraphRepository<GNActor> actorsRepo =  Help.me.getNeo4jTemplate().repositoryFor(GNActor.class);

	@Override
	protected GNActor convert(EMovieActor source) {
		GNActor actor = new GNActor();
			actor.setOid(source.getActorID());
			actor.setName(source.getActorName());
			actor.setDescription("[ACT] "+source.getActorName()+" oid:"+source.getActorID());
		return actor;
	}

	@Override
	protected GraphRepository<GNActor> getRepository() {
		return ActorsLoader.actorsRepo;
	}

}
