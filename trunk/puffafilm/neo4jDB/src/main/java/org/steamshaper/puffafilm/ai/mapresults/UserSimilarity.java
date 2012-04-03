package org.steamshaper.puffafilm.ai.mapresults;

import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.ResultColumn;
import org.steamshaper.puffafilm.ai.node.GNUser;

@MapResult
public interface UserSimilarity {

	@ResultColumn("USERS")
	GNUser user();

	@ResultColumn("count(ur)")
	int commonFilmCount();

	@ResultColumn("avg(ur.rating)")
	float avgOnCommons();
}
