package org.steamshaper.ai.puffafilm.etl.loader;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.entity.EMovies;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNMovie;

public class MovieLoader extends ALoader<GNMovie, EMovies>{

	private static final GraphRepository<GNMovie> moviesRepo =  Help.me.getNeo4jTemplate().repositoryFor(GNMovie.class);

	@Override
	protected GNMovie convert(EMovies source) {
		GNMovie movie = new GNMovie();

		movie.setOid(source.getId());
		movie.setTitle(source.getTitle());
		movie.setImdbID(source.getImdbID());
		movie.setSpanishTitle(source.getSpanishTitle());
		movie.setImdbPictureURL(source.getImdbPictureURL());
		movie.setYear(source.getYear());
		movie.setRtID(source.getRtID());
		movie.setRtAllCriticsRating(source.getRtAllCriticsRating());
		movie.setRtAllCriticsNumReviews(source.getRtAllCriticsNumReviews());
		movie.setRtAllCriticsNumFresh(source.getRtAllCriticsNumFresh());
		movie.setRtAllCriticsNumRotten(source.getRtAllCriticsNumRotten());
		movie.setRtAllCriticsScore(source.getRtAllCriticsScore());
		movie.setRtTopCriticsRating(source.getRtTopCriticsRating());
		movie.setRtTopCriticsNumReviews(source.getRtAllCriticsNumReviews());
		movie.setRtTopCriticsNumFresh(source.getRtTopCriticsNumFresh());
		movie.setRtTopCriticsNumRotten(source.getRtTopCriticsNumRotten());
		movie.setRtTopCriticsScore(source.getRtTopCriticsScore());
		movie.setRtAudienceRating(source.getRtAudienceRating());
		movie.setRtAudienceNumRatings(source.getRtAudienceNumRatings());
		movie.setRtAudienceScore(source.getRtAudienceScore());
		movie.setRtPictureURL(source.getRtPictureURL());
		movie.setDescription("[MOV] "+source.getTitle()+" oid:"+source.getId());
		return movie;
	}

	@Override
	protected GraphRepository<GNMovie> getRepository() {
		return moviesRepo;
	}

}
