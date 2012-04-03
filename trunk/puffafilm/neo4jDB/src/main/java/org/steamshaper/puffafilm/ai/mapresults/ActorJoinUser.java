package org.steamshaper.puffafilm.ai.mapresults;

import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.ResultColumn;
import org.steamshaper.puffafilm.ai.node.GNActor;

@MapResult
public interface ActorJoinUser {

	@ResultColumn("actors")
	GNActor actor();

	@ResultColumn("mr.ranking")
	Float actorRank();
	
	@ResultColumn("ai.ranking")
	Float movieActorRank();

	@ResultColumn("rate.rating")
	Float movieRate();
}
