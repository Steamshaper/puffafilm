package org.steamshaper.puffafilm.ai;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.node.Neo4jHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.steamshaper.puffafilm.ai.node.GNActor;
import org.steamshaper.puffafilm.ai.repository.GNActorRepository;

@ContextConfiguration(locations = "classpath:spring/neo4j.test.cfg.ctx.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class Neo4JTest extends ATestLoader {
	@Autowired
	public GNActorRepository actorRepository;
	@Autowired
	private Neo4jTemplate template;

	@Test
	public void testCreateActor() {
		Neo4jHelper.cleanDb(template);
		GNActor newActor = new GNActor();
		newActor.setName("Marlon Brando");

		actorRepository.save(newActor);
		template.toString();

		GNActor marlonBrando = actorRepository.findByPropertyValue("name",
				"Marlon Brando");
		Assert.assertEquals("Marlon Brando", marlonBrando.getName());
	}

}
