package org.steamshaper.puffafilm.ai.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.puffafilm.ai.mapresults.GenreStats;
import org.steamshaper.puffafilm.ai.node.GNGenre;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.node.GNUser;

public interface GNGenreRepository extends GraphRepository<GNGenre> {

	@Query("START movie=node({0}) MATCH movie-[:KIND_OF]->genre RETURN genre")
	List<GNGenre> getGenreForMovie(GNMovie thisMovie);

	@Query("START user=node({0}) MATCH user-[rate:HAS_RATED]->movie-[ko:KIND_OF]->genre RETURN genre , avg(rate.rating), count(movie) ORDER BY count(movie) DESC")
	List<GenreStats> genreStatistics(GNUser user);

}
