package org.steamshaper.puffafilm.ai.mapresults;

import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.ResultColumn;
import org.steamshaper.puffafilm.ai.node.GNDirector;

@MapResult
public interface DirectorStat {

	@ResultColumn("director")
	GNDirector getDirectorID();

	@ResultColumn("avg(rate.rating)")
	Float getAverage();

	@ResultColumn("count(movie)")
	Integer movieWatched();

}
