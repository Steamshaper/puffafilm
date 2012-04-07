package org.steamshaper.ai.puffafilm.etl.loader.relationship.simple;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.NotInTransactionException;
import org.neo4j.graphdb.Transaction;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.finder.LocationFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.finder.MovieFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.loader.relationship.EntityFinder;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNLocation;
import org.steamshaper.puffafilm.ai.node.GNMovie;

public class ShotInLoader extends
		ASimpleRelationLoader<GNMovie, Long, GNLocation, String> {

	Logger log = Logger.getLogger(ShotInLoader.class);
	public final GraphRepository<GNMovie> movieRepo = Help.me
			.getNeo4jTemplate().repositoryFor(GNMovie.class);

	private ShotInLoader(EntityFinder<GNMovie, Long> rhsFinder,
			EntityFinder<GNLocation, String> lhsFinder) {
		super(rhsFinder, lhsFinder);
	}

	public static ShotInLoader getInstance() {
		EntityFinder<GNMovie, Long> rhsFinder = new MovieFinderOnOid();
		EntityFinder<GNLocation, String> lhsFinder = new LocationFinderOnOid();
		return new ShotInLoader(rhsFinder, lhsFinder);

	}

	@Override
	protected void log(String string) {
		log.debug(string);
	}

	@Override
	protected void wireRightRelationShip(GNMovie rhsNode, GNLocation lhsNode) {
		Set<GNLocation> location = rhsNode.getLocations();

		if (rhsNode.getLocations().size() == 0) {
			location = new HashSet<GNLocation>();
			rhsNode.setLocations(location);
		}
		try {
			location.add(lhsNode);
		} catch (NotInTransactionException txEx) {
			Transaction tx = Help.me.toStartTransaction();
			location.add(lhsNode);
			tx.success();
			tx.finish();
		}
		Help.me.saveNode(rhsNode);
	}

}
