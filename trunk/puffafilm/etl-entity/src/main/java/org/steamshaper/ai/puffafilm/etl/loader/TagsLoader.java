package org.steamshaper.ai.puffafilm.etl.loader;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.entity.ETag;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNTag;

public class TagsLoader extends ALoader<GNTag,ETag> {

	private static final GraphRepository<GNTag> tagsRepo =  Help.me.getNeo4jTemplate().repositoryFor(GNTag.class);

	@Override
	protected GNTag convert(ETag source) {
		GNTag o = new GNTag();
		o.setOid(source.getId());
		o.setName(source.getValue());
		o.setDescription("[TAG] "+source.getValue()+" oid:"+source.getId());
		return o;
	}

	@Override
	protected GraphRepository<GNTag> getRepository() {
		return TagsLoader.tagsRepo ;
	}

}
