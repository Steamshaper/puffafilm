package org.steamshaper.puffafilm.ai.mapresults;

import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@MapResult
public interface YearStatistic {
	
	@ResultColumn("movie.year")
	Integer getYear();
	
	@ResultColumn("avg(rate.rating)")
	Float getAvgRating4Year();

	@ResultColumn("count(movie.year)")
	Integer getMovie4YearCount();
}
