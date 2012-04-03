package org.steamshaper.ai.puffafilm.etl.loader.relationship;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.steamshaper.ai.puffafilm.etl.entity.EUserRatedMovie;
import org.steamshaper.ai.puffafilm.etl.finder.MovieFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.finder.UserFinderOnOid;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.node.GNUser;
import org.steamshaper.puffafilm.ai.relationship.GRHasRated;

public class HasRatedLoader
		extends
		ARelationshipLoader<EUserRatedMovie, GRHasRated, GNUser, Long, GNMovie, Long> {

	Logger log = Logger.getLogger(HasRatedLoader.class);

	public static HasRatedLoader getInstance() {
		EntityFinder<GNUser, Long> rhsFinder = new UserFinderOnOid();
		EntityFinder<GNMovie, Long> lhsFinder = new MovieFinderOnOid();
		return new HasRatedLoader(rhsFinder, lhsFinder);

	}

	private HasRatedLoader(EntityFinder<GNUser, Long> rhsFinder,
			EntityFinder<GNMovie, Long> lhsFinder) {
		super(rhsFinder, lhsFinder);
	}

	@Override
	protected void log(String string) {
		log.debug(string);

	}

	@Override
	protected void wireRightRelationShip(GNUser rhsNode, GRHasRated relation,
			GNMovie lhsNode) {
		Set<GRHasRated> ratedMovies = rhsNode.getRatedMovie();

		if (rhsNode.getRatedMovie().size() == 0) {
			ratedMovies = new HashSet<GRHasRated>();
			rhsNode.setRatedMovie(ratedMovies);
		}

		saveProcedure(rhsNode,ratedMovies,relation);

	}

	@Override
	public GRHasRated createRelationship(GNUser user, GNMovie movie , EUserRatedMovie datSource) {
		GRHasRated oRel = new GRHasRated();
		oRel.setTimestamp(datSource.getTimestamp());
		oRel.setRating(datSource.getRating());
		oRel.setMovie(movie);
		oRel.setUser(user);
		oRel.setDescription(user.getDescription()+"->"+movie.getDescription());
		return oRel;
	}

}
