package org.steamshaper.ai.puffafilm.etl.loader.relationship;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieActor;
import org.steamshaper.ai.puffafilm.etl.finder.ActorFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.finder.MovieFinderOnOid;
import org.steamshaper.puffafilm.ai.node.GNActor;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.relationship.GRActIn;

public class ActInLoader
		extends
		ARelationshipLoader<EMovieActor, GRActIn, GNActor, String, GNMovie, Long> {

	Logger log = Logger.getLogger(ActInLoader.class);

	public static ActInLoader getInstance() {
		EntityFinder<GNActor, String> rhsFinder = new ActorFinderOnOid();
		EntityFinder<GNMovie, Long> lhsFinder = new MovieFinderOnOid();
		return new ActInLoader(rhsFinder, lhsFinder);

	}

	private ActInLoader(EntityFinder<GNActor, String> rhsFinder,
			EntityFinder<GNMovie, Long> lhsFinder) {
		super(rhsFinder, lhsFinder);
	}

	@Override
	protected void log(String string) {
		log.debug(string);

	}

	@Override
	protected void wireRightRelationShip(GNActor rhsNode, GRActIn relation,
			GNMovie lhsNode) {
		Set<GRActIn> actInRoles = rhsNode.getMoviesActed();

		if (rhsNode.getMoviesActed().size() == 0) {
			actInRoles = new HashSet<GRActIn>();
			rhsNode.setMoviesActed(actInRoles);
		}


		saveProcedure(rhsNode,actInRoles,relation);


	}



	@Override
	public GRActIn createRelationship(GNActor actor, GNMovie movie , EMovieActor datSource) {
		GRActIn oRel = new GRActIn();
		oRel.setRanking(datSource.getRanking());
		oRel.setActor(actor);
		oRel.setMovie(movie);
		oRel.setDescription(actor.getDescription()+"->"+movie.getDescription());
		return oRel;
	}

}
