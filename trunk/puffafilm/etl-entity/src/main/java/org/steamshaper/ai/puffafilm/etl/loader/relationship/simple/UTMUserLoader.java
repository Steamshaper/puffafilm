package org.steamshaper.ai.puffafilm.etl.loader.relationship.simple;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.NotInTransactionException;
import org.neo4j.graphdb.Transaction;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.finder.UTMFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.finder.UserFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.loader.relationship.EntityFinder;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNRUserTagMovie;
import org.steamshaper.puffafilm.ai.node.GNUser;

public class UTMUserLoader extends
		ASimpleRelationLoader<GNUser, Long, GNRUserTagMovie, String> {

	Logger log = Logger.getLogger(UTMUserLoader.class);

	public final GraphRepository<GNRUserTagMovie> utmRepo = Help.me
			.getNeo4jTemplate().repositoryFor(GNRUserTagMovie.class);


	public static UTMUserLoader getInstance() {
		EntityFinder<GNUser, Long> rhsFinder = new UserFinderOnOid();
		EntityFinder<GNRUserTagMovie, String> lhsFinder = new UTMFinderOnOid();
		return new UTMUserLoader(rhsFinder, lhsFinder);

	}

	private UTMUserLoader(EntityFinder<GNUser, Long> rhsFinder,
			EntityFinder<GNRUserTagMovie, String> lhsFinder) {
		super(rhsFinder, lhsFinder);

	}

	@Override
	protected void log(String string) {
		log.debug(string);

	}

	@Override
	protected void wireRightRelationShip(GNUser rhsNode, GNRUserTagMovie lhsNode) {
		try{
			lhsNode.setUser(rhsNode);
		}catch(NotInTransactionException txEx){
			Transaction tx = Help.me.toStartTransaction();
			lhsNode.setUser(rhsNode);
			tx.success();
			tx.finish();
		}
		Help.me.saveNode(lhsNode);

	}

}
