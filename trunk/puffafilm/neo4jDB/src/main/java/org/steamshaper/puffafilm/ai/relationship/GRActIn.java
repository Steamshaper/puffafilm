package org.steamshaper.puffafilm.ai.relationship;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;
import org.steamshaper.puffafilm.ai.node.GNActor;
import org.steamshaper.puffafilm.ai.node.GNMovie;

@RelationshipEntity(type = "ACT_IN")
public class GRActIn implements IGRelation {
	@GraphId
	Long id;
	Integer ranking;
	@StartNode
	GNActor actor;
	@EndNode
	GNMovie movie;
	@Fetch String description;

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public GNActor getActor() {
		return actor;
	}

	public void setActor(GNActor actor) {
		this.actor = actor;
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
