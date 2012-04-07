package org.steamshaper.ai.puffafilm.etl.loader.relationship;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Iterator;

import org.junit.Test;
import org.neo4j.graphdb.Transaction;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieActor;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieTag;
import org.steamshaper.ai.puffafilm.etl.test.ALoaderTester;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNActor;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.node.GNTag;
import org.steamshaper.puffafilm.ai.relationship.GRActIn;
import org.steamshaper.puffafilm.ai.relationship.GRIsTagged;

public class ARelationshipLoaderTest extends ALoaderTester {

	@Test
	public void testActInLoader() throws InstantiationException, IllegalAccessException, IOException {
		ActInLoader tested =  ActInLoader.getInstance();

		GNActor actor = new GNActor();
		actor.setOid("keanu_reeves");
		actor.setName("keanu reeves");

		GNMovie movie = new GNMovie();
		movie.setOid(1L);
		movie.setTitle("Matrix");

		//Persisting test nodes
		Transaction tx = Help.me.toStartTransaction();
		Help.me.getNeo4jTemplate().save(movie);
		Help.me.getNeo4jTemplate().save(actor);
		tx.success();
		tx.finish();

		//Mock data source
		EMovieActor datSource = new EMovieActor();
		datSource.setRanking(5);
		tx = Help.me.toStartTransaction();
		try{
		tested.findRelationShipMemberThenWireIt("keanu_reeves", 1L, datSource);
		tx.success();
		} catch (Exception ex) {
			tx.failure();
		} finally {
			tx.finish();
		}
		GraphRepository<GNActor> actRepo = Help.me.getNeo4jTemplate().repositoryFor(GNActor.class);
		GNActor stoCazzo = actRepo.findByPropertyValue("oid", "keanu_reeves");
		Iterator<GRActIn> iterator =  stoCazzo.getMoviesActed().iterator();
		while (iterator.hasNext()){
			GRActIn rela = iterator.next();
			assertEquals("Matrix",Help.me.fetch(rela.getMovie()).getTitle());
			assertEquals("keanu reeves",rela.getActor().getName());
			assertEquals(new Integer(5), rela.getRanking());

		}
	}
	@Test
	public void testIsTaggedLoader() throws InstantiationException, IllegalAccessException, IOException {
		IsTaggedLoader tested =  IsTaggedLoader.getInstance();

		GNMovie movie = new GNMovie();
		movie.setOid(2L);
		movie.setTitle("Cicciolina e il cane cavallo");

		GNTag tag = new GNTag();
		tag.setOid(2L);
		tag.setName("pompelmi");

		//Persisting test nodes
		Transaction tx = Help.me.toStartTransaction();
		Help.me.getNeo4jTemplate().save(movie);
		Help.me.getNeo4jTemplate().save(tag);
		tx.success();
		tx.finish();

		//Mock data source
		EMovieTag datSource = new EMovieTag();
		datSource.setTagWeight(5);

		tx = Help.me.toStartTransaction();
		tested.findRelationShipMemberThenWireIt(2L, 2L, datSource);
		tx.success();
		tx.finish();

		GraphRepository<GNMovie> movieRepo = Help.me.getNeo4jTemplate().repositoryFor(GNMovie.class);
		GNMovie stoCazzo = movieRepo.findByPropertyValue("oid", 2L);
		Iterator<GRIsTagged> iterator =  stoCazzo.getTags().iterator();
		while (iterator.hasNext()){
			GRIsTagged rela = iterator.next();
			assertEquals("Cicciolina e il cane cavallo",rela.getMovie().getTitle());
			assertEquals("pompelmi",Help.me.fetch(rela.getTag()).getName());
			assertEquals(new Integer(5), rela.getTagWeight());

		}
	}
}
