package org.steamshaper.puffafilm.ai.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.puffafilm.ai.mapresults.DirectorStat;
import org.steamshaper.puffafilm.ai.node.GNDirector;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.node.GNUser;

public interface GNDirectorRepository extends GraphRepository<GNDirector> {

	@Query("START user=node({0}) MATCH user-[rate:HAS_RATED]->movie-[:DIRECTED]->director WHERE rate.rating<={1} RETURN director,avg(rate.rating), count(movie) ORDER BY count(movie) DESC")
	List<DirectorStat> findBadDirectorsForUser(GNUser user,Float badThreshold);

	@Query("START user=node({0}) MATCH user-[rate:HAS_RATED]->movie-[:DIRECTED]->director WHERE rate.rating>= {1} RETURN director,avg(rate.rating), count(movie) ORDER BY count(movie) DESC")
	List<DirectorStat> findGoodDirectorsForUser(GNUser user,Float goodThreshold);

	@Query("START user=node({0}) , director = node({1}) MATCH user-[rate:HAS_RATED]->movie-[:DIRECTED]->director RETURN avg(rate.rating)")
	Float getAvgForDirector(GNUser thisUser,GNDirector director);

	@Query("START movie=node({0}) MATCH movie-[:DIRECTED]->director RETURN director")
	GNDirector getDirectorForMovie(GNMovie movie);

	@Query("START director=node({0}) MATCH director<-[:DIRECTED]-movie<-[rate:HAS_RATED]-users RETURN avg(rate.rating)")
	Float getGlobalAvgForDirector(GNDirector director);

//	@Query("START directors=node({1}) MATCH")
//	Long getBuddyAvgForDirector(List<DirectorStat> goodDirectorStat);

}
