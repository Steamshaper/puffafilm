package org.steamshaper.ai.puffafilm.etl.loader.relationship.simple;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.NotInTransactionException;
import org.neo4j.graphdb.Transaction;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.finder.GenreFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.finder.MovieFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.loader.relationship.EntityFinder;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNGenre;
import org.steamshaper.puffafilm.ai.node.GNMovie;

public class KindOfLoader extends
		ASimpleRelationLoader<GNMovie, Long, GNGenre, String> {
	Logger log = Logger.getLogger(KindOfLoader.class);
	public final GraphRepository<GNMovie> movieRepo = Help.me
			.getNeo4jTemplate().repositoryFor(GNMovie.class);


	public static KindOfLoader getInstance() {
		EntityFinder<GNMovie, Long> rhsFinder = new MovieFinderOnOid();
		EntityFinder<GNGenre, String> lhsFinder = new GenreFinderOnOid();
		return new KindOfLoader(rhsFinder, lhsFinder);

	}

	private KindOfLoader(EntityFinder<GNMovie, Long> rhsFinder,
			EntityFinder<GNGenre, String> lhsFinder) {
		super(rhsFinder, lhsFinder);

	}

	@Override
	protected void log(String string) {
		log.debug(string);

	}

	@Override
	protected void wireRightRelationShip(GNMovie rhsNode, GNGenre lhsNode) {
		Set<GNGenre> genres = rhsNode.getGenres();

		if (rhsNode.getGenres().size() == 0) {
			genres = new HashSet<GNGenre>();
			rhsNode.setGenres(genres);
		}
		try{
			genres.add(lhsNode);

		}catch(NotInTransactionException txEx){
			Transaction tx = Help.me.toStartTransaction();
			genres.add(lhsNode);
			tx.success();
			tx.finish();
		}
		Help.me.saveNode(rhsNode);

	}

}
