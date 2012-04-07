package org.steamshaper.puffafilm.ai.mapresults;

import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@MapResult
public interface User2UserIntersection {

	@ResultColumn("ID(otherUser)")
	Long getUserID();

	@ResultColumn("count(ratedMovie)")
	Integer getIntersectionCount();

	@ResultColumn("r4m.rating")
	Float rating();

	@ResultColumn("avg(tr.rating)")
	Float userRating();
}
