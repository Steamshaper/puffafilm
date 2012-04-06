package org.steamshaper.ai.runtime.service;

import java.util.List;

import org.steamshaper.ai.puffafilm.etl.KnowledgeExtractor;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieGenre;
import org.steamshaper.ai.puffafilm.etl.loader.relationship.simple.KindOfLoader;

public class GSRKindOfLoader extends AGRelationLoader {

	@Override
	protected void loadRelation(KnowledgeExtractor ke) {
		if (shouldRun()) {
			KindOfLoader loader = KindOfLoader.getInstance();
			List<EMovieGenre> datList = ke.geteMovieGenreList();
			// Transaction tx = Help.me.toStartTransaction();
			int errorCount = 0;
			log.info("Ready to wire [" + datList.size() + "] relationship ...");
			long start = System.currentTimeMillis();
			for (EMovieGenre eMG : datList) {
				try {
					loader.findRelationShipMemberThenWireIt(eMG.getMovieID(),
							eMG.getGenre());
				} catch (InstantiationException e) {
					errorCount++;
					log.error("Ops... genre [" + eMG.getGenre() + "] MovieId["
							+ eMG.getMovieID() + "]");
				} catch (IllegalAccessException e) {
					errorCount++;
					log.error("Ops... genre [" + eMG.getGenre() + "] MovieId["
							+ eMG.getMovieID() + "]");
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
