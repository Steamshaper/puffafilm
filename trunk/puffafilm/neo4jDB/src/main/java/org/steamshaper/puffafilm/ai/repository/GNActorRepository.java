package org.steamshaper.puffafilm.ai.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.puffafilm.ai.mapresults.ActorJoinUser;
import org.steamshaper.puffafilm.ai.node.GNActor;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.node.GNUser;

public interface GNActorRepository extends GraphRepository<GNActor> {

	@Query("START movie=node({0}) , user=node({1}) MATCH movie<-[mr:ACT_IN]-actors , user-[rate:HAS_RATED]->movies<-[ai:ACT_IN]-actors WHERE mr.ranking<={2} RETURN actors, mr.ranking, ai.ranking, rate.rating")
	List<ActorJoinUser> favoriteActorInMovieForUser(GNMovie movie, GNUser user,Integer minRank);
}
