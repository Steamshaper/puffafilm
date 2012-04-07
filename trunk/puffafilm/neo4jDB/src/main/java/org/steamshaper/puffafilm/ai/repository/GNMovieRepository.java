package org.steamshaper.puffafilm.ai.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.puffafilm.ai.mapresults.UserRating;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.node.GNUser;

public interface GNMovieRepository extends GraphRepository<GNMovie> {

	@Query("START movie=node:__types__(className =\"org.steamshaper.puffafilm.ai.node.GNMovie\") WHERE movie.oid ={0} RETURN movie")
	GNMovie findMovieByOid(long oid);

	@Query("START movie=node:movie4title(title={0}) MATCH user-[:HAS_RATED]->movie RETURN user")
	Iterable<GNUser> getAllRatingUserByTitle(String movieName);

	@Query("START movie=node:movie4title(title={0}) RETURN movie")
	GNMovie findMovieByTitle(String movieTitle);

	@Query("START movie=node({0}) MATCH user-[:HAS_RATED]->movie RETURN user")
	Iterable<GNUser> getAllRatingUsers(GNMovie movie);

	@Query("START movie=node({0}) MATCH user-[r:HAS_RATED]->movie RETURN avg(r.rating)")
	Float getAverageRateForMovie(GNMovie movie);

	@Query("START film=node({0}) MATCH user-[r:HAS_RATED]->film RETURN user, r.rating")
	List<UserRating> getUsersAndRateForMovie(GNMovie movie);

	@Query("START film=node:__types__(className = \"org.steamshaper.puffafilm.ai.node.GNMovie\") MATCH u-[r:HAS_RATED]->film WHERE film.oid = {0} RETURN avg(r.rating)")
	Float getAverageRatingForMovie(Long oid);
	
	@Query("START film=node({0}) RETURN film.year")
	Integer getMovieYear(GNMovie movie);

}
