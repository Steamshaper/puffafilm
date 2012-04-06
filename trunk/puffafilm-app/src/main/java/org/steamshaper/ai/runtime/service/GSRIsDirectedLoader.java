package org.steamshaper.ai.runtime.service;

import java.util.List;

import org.steamshaper.ai.puffafilm.etl.KnowledgeExtractor;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieDirector;
import org.steamshaper.ai.puffafilm.etl.loader.relationship.simple.IsDirectedLoader;

public class GSRIsDirectedLoader extends AGRelationLoader {

	@Override
	protected void loadRelation(KnowledgeExtractor ke) {
		if (shouldRun()) {
			IsDirectedLoader loader = IsDirectedLoader.getInstance();
			List<EMovieDirector> datList = ke.geteMovieDirectorList();
			// Transaction tx = Help.me.toStartTransaction();
			int errorCount = 0;
			log.info("Ready to wire [" + datList.size() + "] relationship ...");
			long start = System.currentTimeMillis();
			for (EMovieDirector eMD : datList) {
				try {
					loader.findRelationShipMemberThenWireIt(eMD.getMovieID(),
							eMD.getDirectorID());
				} catch (InstantiationException e) {
					errorCount++;
					log.error("Ops... directorID [" + eMD.getDirectorID()
							+ "] MovieId[" + eMD.getMovieID() + "]");
				} catch (IllegalAccessException e) {
					errorCount++;
					log.error("Ops... directorID [" + eMD.getDirectorID()
							+ "] MovieId[" + eMD.getMovieID() + "]");
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
