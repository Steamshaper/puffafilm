package org.steamshaper.ai.runtime.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.steamshaper.ai.puffafilm.etl.KnowledgeExtractor;
import org.steamshaper.ai.puffafilm.etl.entity.EUserTaggedMovie;
import org.steamshaper.ai.puffafilm.etl.loader.relationship.simple.UTMUserLoader;

public class GSRUTMUserLoader extends AGRelationLoader {

	private final Logger log = Logger.getLogger("service.impl.UserUTMRelation");

	private int errorCount = 0;

	@Override
	protected void loadRelation(KnowledgeExtractor ke) {
		if (shouldRun()) {
			List<EUserTaggedMovie> datList = ke.geteUserTaggedMovieList();
			// Transaction tx = Help.me.toStartTransaction();
			log.info("Ready to wire [" + datList.size() + "] relationship ...");
			long start = System.currentTimeMillis();
			UTMUserLoader utmUser = UTMUserLoader.getInstance();
			for (EUserTaggedMovie instance : datList) {
				try {
					utmUser.findRelationShipMemberThenWireIt(
							instance.getUserID(), getUTMid(instance));
				} catch (InstantiationException e) {
					this.errorCount++;
					log.error("Ops... UTMid [" + getUTMid(instance)
							+ "] UserId[" + instance.getUserID()+ "]");
				} catch (IllegalAccessException e) {
					this.errorCount++;
					log.error("Ops... UTMid [" + getUTMid(instance)
							+ "] UserId[" + instance.getUserID()+ "]");
				}
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

	private String getUTMid(EUserTaggedMovie source) {
		return "u" + source.getUserID() + "t" + source.getTagID() + "m"
				+ source.getMovieID();
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
