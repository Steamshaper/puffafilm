package org.steamshaper.ai.puffafilm.etl.loader;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.neo4j.graphdb.Transaction;
import org.steamshaper.ai.puffafilm.etl.test.ALoaderTester;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNActor;
import org.steamshaper.puffafilm.ai.repository.GNActorRepository;

public class ActorFinderOnOidTest extends ALoaderTester {

	@Test
	public void testFindNodeWithIDValueString() {
		Help.me.toCleanN4JDatabase();
		GNActorRepository repo = Help.me.getContext().getBean(
				GNActorRepository.class);
		Transaction tx = Help.me.getNeo4jTemplate().beginTx();
		try {
			GNActor actor = new GNActor();
			actor.setOid("big_puffo");
			actor.setName("Grande Puffo");

			repo.save(actor);

			tx.success();
		} catch (Exception e) {

		} finally {
			tx.finish();
		}
		// Provo a recuperare quello che ho appena messo
		GNActor retrived = repo.findByPropertyValue("oid", "big_puffo");

		assertEquals(retrived.getName(), "Grande Puffo");
	}

}
