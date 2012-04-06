package org.steamshaper.ai.runtime.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.steamshaper.ai.puffafilm.etl.KnowledgeExtractor;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieActor;
import org.steamshaper.ai.puffafilm.etl.loader.relationship.ActInLoader;

public class GRelationActInLoader extends AGRelationLoader {

	private final Logger log = Logger.getLogger("service.impl.ActInRelation");

	@Override
	protected void loadRelation(KnowledgeExtractor ke) {
		if (shouldRun()) {
			ActInLoader loader = ActInLoader.getInstance();
			List<EMovieActor> datList = ke.geteMovieActorList();
			// Transaction tx = Help.me.toStartTransaction();
			int errorCount = 0;
			log.info("Ready to wire [" + datList.size() + "] relationship ...");
			long start = System.currentTimeMillis();
			for (EMovieActor eMA : datList) {
				try {
					loader.findRelationShipMemberThenWireIt(eMA.getActorID(),
							eMA.getMovieID(), eMA);
				} catch (InstantiationException e) {
					errorCount++;
					log.error("Ops... actorId [" + eMA.getActorID()
							+ "] MovieId[" + eMA.getMovieID() + "] dat[" + eMA
							+ "]");
				} catch (IllegalAccessException e) {
					errorCount++;
					log.error("Ops... actorId [" + eMA.getActorID()
							+ "] MovieId[" + eMA.getMovieID() + "] dat[" + eMA
							+ "]");
				}
			}
			if (errorCount > 0) {
				log.error("[" + errorCount + "] occurred");
			}
			// tx.success();
			// tx.finish();
			log.info("Wired [" + datList.size() + "] relationship in ["
					+ (System.currentTimeMillis() - start) + "] with error/s ["
					+ errorCount + "]");
		} else {
			log.debug("Db Load SKIPPED!");
		}
	}

	private Condition shouldRun;

	public boolean shouldRun() {
		if (shouldRun != null) {
			return shouldRun.isConditionTrue();
		}
		return true;
	}

	public void setShouldRun(Condition shouldRun) {
		this.shouldRun = shouldRun;
	}

}
