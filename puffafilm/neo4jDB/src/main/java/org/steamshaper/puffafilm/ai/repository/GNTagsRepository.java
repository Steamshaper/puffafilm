package org.steamshaper.puffafilm.ai.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.puffafilm.ai.node.GNTag;

public interface GNTagsRepository extends GraphRepository<GNTag> {
	GNTag findByOid(Long oid);
}
