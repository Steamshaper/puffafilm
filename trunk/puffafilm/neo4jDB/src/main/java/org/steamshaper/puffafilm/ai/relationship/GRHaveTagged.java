package org.steamshaper.puffafilm.ai.relationship;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;
import org.steamshaper.puffafilm.ai.node.GNTag;
import org.steamshaper.puffafilm.ai.node.GNUser;

@RelationshipEntity(type="HAVE_TAGGED")
public class GRHaveTagged implements IGRelation{
	@GraphId Long id;
	Long timestamp;

	@StartNode GNUser user;
	@EndNode GNTag tag;
	@Fetch String description;

	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public GNUser getUser() {
		return user;
	}
	public GNTag getTag() {
		return tag;
	}
	public Long getId() {
		return id;
	}
	public void setUser(GNUser user) {
		this.user = user;
	}
	public void setTag(GNTag tag) {
		this.tag = tag;
	}
	@Override
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
