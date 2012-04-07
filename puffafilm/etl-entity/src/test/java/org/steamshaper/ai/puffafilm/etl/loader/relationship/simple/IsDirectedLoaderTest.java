package org.steamshaper.ai.puffafilm.etl.loader.relationship.simple;

import java.io.IOException;

import org.junit.Test;
import org.neo4j.graphdb.Transaction;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.loader.relationship.ARelationshipLoaderTest;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNDirector;
import org.steamshaper.puffafilm.ai.node.GNMovie;

public class IsDirectedLoaderTest extends ARelationshipLoaderTest {

	@Test
	public void testDirectorsLoader() throws InstantiationException,
			IllegalAccessException, IOException {
		IsDirectedLoader tested = IsDirectedLoader.getInstance();

		GNMovie movie = new GNMovie();
		movie.setOid(1L);
		movie.setTitle("Godfather");

		GNDirector director = new GNDirector();
		director.setOid("Coppola");
		director.setName("Coppola");

		// Persisting test nodes
		Transaction tx = Help.me.toStartTransaction();
		Help.me.getNeo4jTemplate().save(movie);
		Help.me.getNeo4jTemplate().save(director);
		tx.success();
		tx.finish();

		// Mock data source

		tx = Help.me.toStartTransaction();
		try {
			tested.findRelationShipMemberThenWireIt(1L, "Coppola");
			tx.success();
		} catch (Exception ex) {
			tx.failure();
		} finally {
			tx.finish();
		}
		GraphRepository<GNMovie> movieRepo = Help.me.getNeo4jTemplate()
				.repositoryFor(GNMovie.class);
		GNMovie stoCazzo = movieRepo.findByPropertyValue("oid", 1L);
		for(GNDirector dir : stoCazzo.getDirectors()){
			Help.me.fetch(dir);
			dir.getName();
		}
		System.out.println(stoCazzo.getDirectors());

	}
}
