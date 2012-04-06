package org.steamshaper.ai.runtime.service;

import org.steamshaper.ai.puffafilm.etl.KnowledgeExtractor;
import org.steamshaper.ai.runtime.AService;

public abstract class AGRelationLoader extends AService {


	@Override
	public void run() {
		log.info("Creating knowledge extractor ...");
		KnowledgeExtractor ke = KnowledgeExtractor.getInstance();
		log.info("Collecting data from dataset ...");
		ke.extractAll();

		loadRelation(ke);
	}

	protected abstract void loadRelation(KnowledgeExtractor ke);

	
}
