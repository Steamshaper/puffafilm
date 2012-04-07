package org.steamshaper.puffafilm.ai.mapresults;

import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.ResultColumn;
import org.steamshaper.puffafilm.ai.node.GNMovie;

@MapResult
public interface MovieRating {

	@ResultColumn("movie")
	GNMovie getMovie();

	@ResultColumn("r.rating")
	float getRating();
}
