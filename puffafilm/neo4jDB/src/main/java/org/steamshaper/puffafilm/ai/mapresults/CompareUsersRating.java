package org.steamshaper.puffafilm.ai.mapresults;

import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.ResultColumn;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.node.GNUser;

@MapResult
public interface CompareUsersRating {
	
	@ResultColumn("user")
	GNUser getUser();
	
	@ResultColumn("movie")
	GNMovie getMovie();
	
	@ResultColumn("r.rating")
	Float getUserRating();
	
	@ResultColumn("cr.rating")
	Float getMyRating();
	

}
