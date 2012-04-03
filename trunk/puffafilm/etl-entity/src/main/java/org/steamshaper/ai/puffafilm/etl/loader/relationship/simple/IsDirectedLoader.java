package org.steamshaper.ai.puffafilm.etl.loader.relationship.simple;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.NotInTransactionException;
import org.neo4j.graphdb.Transaction;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.finder.DirectorFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.finder.MovieFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.loader.relationship.EntityFinder;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNDirector;
import org.steamshaper.puffafilm.ai.node.GNMovie;

public class IsDirectedLoader
		extends
		ASimpleRelationLoader< GNMovie, Long, GNDirector, String> {

	Logger log = Logger.getLogger(IsDirectedLoader.class);
	public final GraphRepository<GNMovie> movieRepo = Help.me.getNeo4jTemplate().repositoryFor(GNMovie.class);





	public static IsDirectedLoader getInstance() {
		EntityFinder<GNMovie, Long> rhsFinder = new MovieFinderOnOid();
		EntityFinder<GNDirector, String> lhsFinder = new DirectorFinderOnOid();
		return new IsDirectedLoader(rhsFinder, lhsFinder);

	}


	public IsDirectedLoader(EntityFinder<GNMovie, Long> rhsFinder,
			EntityFinder<GNDirector, String> lhsFinder) {
		super(rhsFinder,lhsFinder);
	}



	@Override
	protected void log(String string) {
		log.debug(string);
	}

	@Override
	protected void wireRightRelationShip(GNMovie rhsNode, GNDirector lhsNode) {
		Set<GNDirector> directors = rhsNode.getDirectors();

		if (rhsNode.getDirectors().size() == 0) {
			directors = new HashSet<GNDirector>();
			rhsNode.setDirectors(directors);
		}
		try{
			directors.add(lhsNode);

		}catch(NotInTransactionException txEx){
			Transaction tx = Help.me.toStartTransaction();
			directors.add(lhsNode);
			tx.success();
			tx.finish();
		}
		Help.me.saveNode(rhsNode);

	}

}
