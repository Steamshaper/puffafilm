package org.steamshaper.ai.puffafilm.etl.loader.relationship.simple;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.NotInTransactionException;
import org.neo4j.graphdb.Transaction;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.finder.TagFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.finder.UTMFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.loader.relationship.EntityFinder;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNRUserTagMovie;
import org.steamshaper.puffafilm.ai.node.GNTag;

public class UTMTagLoader extends
		ASimpleRelationLoader<GNTag, Long, GNRUserTagMovie, String> {

	Logger log = Logger.getLogger(UTMTagLoader.class);

	public final GraphRepository<GNRUserTagMovie> utmRepo = Help.me
			.getNeo4jTemplate().repositoryFor(GNRUserTagMovie.class);


	public static UTMTagLoader getInstance() {
		EntityFinder<GNTag, Long> rhsFinder = new TagFinderOnOid();
		EntityFinder<GNRUserTagMovie, String> lhsFinder = new UTMFinderOnOid();
		return new UTMTagLoader(rhsFinder, lhsFinder);

	}

	private UTMTagLoader(EntityFinder<GNTag, Long> rhsFinder,
			EntityFinder<GNRUserTagMovie, String> lhsFinder) {
		super(rhsFinder, lhsFinder);

	}

	@Override
	protected void log(String string) {
		log.debug(string);

	}

	@Override
	protected void wireRightRelationShip(GNTag rhsNode, GNRUserTagMovie lhsNode) {
		try{
			lhsNode.setTag(rhsNode);
		}catch(NotInTransactionException txEx){
			Transaction tx = Help.me.toStartTransaction();
			lhsNode.setTag(rhsNode);
			tx.success();
			tx.finish();
		}
		Help.me.saveNode(lhsNode);

	}

}
