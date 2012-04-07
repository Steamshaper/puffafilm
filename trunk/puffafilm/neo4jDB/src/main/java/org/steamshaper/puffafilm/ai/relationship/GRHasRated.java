package org.steamshaper.puffafilm.ai.relationship;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.node.GNUser;

@RelationshipEntity(type="HAS_RATED")
public class GRHasRated implements IGRelation{
	@GraphId Long id;
	Long timestamp;
	Float rating;
	@Fetch String description;
	@StartNode GNUser user;
	@EndNode GNMovie movie;
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
	}
	public GNUser getUser() {
		return user;
	}
	public void setUser(GNUser user) {
		this.user = user;
	}
	public GNMovie getMovie() {
		return movie;
	}
	public void setMovie(GNMovie movie) {
		this.movie = movie;
	}
	public Long getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


}
