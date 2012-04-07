package org.steamshaper.puffafilm.ai.mapresults;

import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.ResultColumn;
import org.steamshaper.puffafilm.ai.node.GNUser;

@MapResult
public interface UserRating {

	@ResultColumn("user")
	GNUser getUser();

	@ResultColumn("r.rating")
	float getRating();
}
