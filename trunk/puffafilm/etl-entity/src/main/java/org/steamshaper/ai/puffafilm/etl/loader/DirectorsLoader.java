package org.steamshaper.ai.puffafilm.etl.loader;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieDirector;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNDirector;

public class DirectorsLoader extends ALoader<GNDirector,EMovieDirector>{

	private static final GraphRepository<GNDirector> directorsRepo =  Help.me.getNeo4jTemplate().repositoryFor(GNDirector.class);

	@Override
	protected GNDirector convert(EMovieDirector source) {
		GNDirector director = directorsRepo.findByPropertyValue("oid", source.getDirectorID());
		if(director==null){
			director = new GNDirector();
			director.setOid(source.getDirectorID());
			director.setName(source.getDirectorName());
			director.setDescription("[DIR] "+source.getDirectorName()+" oid:"+source.getDirectorID());
		}
		return director;
	}

	@Override
	protected GraphRepository<GNDirector> getRepository() {
		return DirectorsLoader.directorsRepo;
	}

}
