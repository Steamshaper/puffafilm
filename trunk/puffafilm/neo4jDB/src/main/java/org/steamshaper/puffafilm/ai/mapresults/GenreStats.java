package org.steamshaper.puffafilm.ai.mapresults;

import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.ResultColumn;
import org.steamshaper.puffafilm.ai.node.GNGenre;

@MapResult
public interface GenreStats {

	@ResultColumn("genre")
	GNGenre genre();

	@ResultColumn("count(movie)")
	Integer count();

	@ResultColumn("avg(rate.rating)")
	Float avgRating();
}
