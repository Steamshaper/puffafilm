package org.steamshaper.ai.puffafilm.etl.loader.relationship.simple;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.NotInTransactionException;
import org.neo4j.graphdb.Transaction;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.finder.MovieFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.finder.UTMFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.loader.relationship.EntityFinder;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.node.GNRUserTagMovie;

public class UTMMovieLoader extends
		ASimpleRelationLoader<GNMovie, Long, GNRUserTagMovie, String> {

	Logger log = Logger.getLogger(UTMMovieLoader.class);

	public final GraphRepository<GNRUserTagMovie> utmRepo = Help.me
			.getNeo4jTemplate().repositoryFor(GNRUserTagMovie.class);


	public static UTMMovieLoader getInstance() {
		EntityFinder<GNMovie, Long> rhsFinder = new MovieFinderOnOid();
		EntityFinder<GNRUserTagMovie, String> lhsFinder = new UTMFinderOnOid();
		return new UTMMovieLoader(rhsFinder, lhsFinder);

	}

	private UTMMovieLoader(EntityFinder<GNMovie, Long> rhsFinder,
			EntityFinder<GNRUserTagMovie, String> lhsFinder) {
		super(rhsFinder, lhsFinder);

	}

	@Override
	protected void log(String string) {
		log.debug(string);

	}

	@Override
	protected void wireRightRelationShip(GNMovie rhsNode, GNRUserTagMovie lhsNode) {
		try{
			lhsNode.setMovie(rhsNode);
		}catch(NotInTransactionException txEx){
			Transaction tx = Help.me.toStartTransaction();
			lhsNode.setMovie(rhsNode);
			tx.success();
			tx.finish();
		}
		Help.me.saveNode(lhsNode);

	}

}
