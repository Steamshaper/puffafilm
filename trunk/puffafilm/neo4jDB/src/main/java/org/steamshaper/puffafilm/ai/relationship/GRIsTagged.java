package org.steamshaper.puffafilm.ai.relationship;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.node.GNTag;

@RelationshipEntity(type = "IS_TAGGED")
public class GRIsTagged implements IGRelation {
	@GraphId Long id;
	Integer tagWeight;

	@StartNode GNMovie movie;
	@EndNode GNTag tag;
	@Fetch String description;

	public Integer getTagWeight() {
		return tagWeight;
	}
	public void setTagWeight(Integer tagWeight) {
		this.tagWeight = tagWeight;
	}
	public GNMovie getMovie() {
		return movie;
	}
	public void setMovie(GNMovie movie) {
		this.movie = movie;
	}
	public GNTag getTag() {
		return tag;
	}
	public void setTag(GNTag tag) {
		this.tag = tag;
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
