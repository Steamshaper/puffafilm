package org.steamshaper.ai.runtime.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.steamshaper.ai.puffafilm.etl.KnowledgeExtractor;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieLocation;
import org.steamshaper.ai.puffafilm.etl.loader.relationship.simple.ShotInLoader;

public class GSRShotInLoader extends AGRelationLoader {

	private final Logger log = Logger.getLogger("service.impl.ActInRelation");

	private int errorCount = 0;

	@Override
	protected void loadRelation(KnowledgeExtractor ke) {
		if (shouldRun()) {
			List<EMovieLocation> datList = ke.geteMovieLocationList();
			// Transaction tx = Help.me.toStartTransaction();
			log.info("Ready to wire [" + datList.size() + "] relationship ...");
			long start = System.currentTimeMillis();

			for (EMovieLocation instance : datList) {
				Long movieId = instance.getMovieID();
				String[] locations = { instance.getLocation1(),
						instance.getLocation2(), instance.getLocation3(),
						instance.getLocation4() };
				loadLocations(movieId, locations);

			}
			if (this.errorCount > 0) {
				log.error("[" + this.errorCount + "] occurred");
			}
			// tx.success();
			// tx.finish();
			log.info("Wired [" + datList.size() + "] relationship in ["
					+ (System.currentTimeMillis() - start) + "] with error/s ["
					+ this.errorCount + "]");
		} else {
			log.debug("Db Load SKIPPED!");
		}
	}

	private void loadLocations(Long movieId, String[] locations) {
		ShotInLoader loader = ShotInLoader.getInstance();
		for (String locationName : locations) {
			if (locationName != null) {
				try {
					loader.findRelationShipMemberThenWireIt(movieId,
							locationName);
				} catch (InstantiationException e) {
					this.errorCount++;
					log.error("Ops... MovieId [" + movieId + "] LocationName ["
							+ locationName + "]");
				} catch (IllegalAccessException e) {
					this.errorCount++;
					log.error("Ops... MovieId [" + movieId + "] LocationName ["
							+ locationName + "]");
				}
			}
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
