package org.steamshaper.ai.puffafilm.etl.loader.relationship.simple;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.NotInTransactionException;
import org.neo4j.graphdb.Transaction;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.finder.CountryFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.finder.MovieFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.loader.relationship.EntityFinder;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNCountry;
import org.steamshaper.puffafilm.ai.node.GNMovie;

public class MadeInLoader extends
		ASimpleRelationLoader<GNMovie, Long, GNCountry, String> {

	Logger log = Logger.getLogger(MadeInLoader.class);
	public final GraphRepository<GNMovie> movieRepo = Help.me
			.getNeo4jTemplate().repositoryFor(GNMovie.class);

	private MadeInLoader(EntityFinder<GNMovie, Long> rhsFinder,
			EntityFinder<GNCountry, String> lhsFinder) {
		super(rhsFinder, lhsFinder);
	}

	public static MadeInLoader getInstance() {
		EntityFinder<GNMovie, Long> rhsFinder = new MovieFinderOnOid();
		EntityFinder<GNCountry, String> lhsFinder = new CountryFinderOnOid();
		return new MadeInLoader(rhsFinder, lhsFinder);

	}

	@Override
	protected void log(String string) {
		log.debug(string);
	}

	@Override
	protected void wireRightRelationShip(GNMovie rhsNode, GNCountry lhsNode) {
		Set<GNCountry> country = rhsNode.getCountries();

		if (rhsNode.getCountries().size() == 0) {
			country = new HashSet<GNCountry>();
			rhsNode.setCountries(country);
		}
		try {
			country.add(lhsNode);
		} catch (NotInTransactionException txEx) {
			Transaction tx = Help.me.toStartTransaction();
			country.add(lhsNode);
			tx.success();
			tx.finish();
		}
		Help.me.saveNode(rhsNode);

	}

}
