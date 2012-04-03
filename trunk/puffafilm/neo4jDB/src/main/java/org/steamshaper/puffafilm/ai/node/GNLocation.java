package org.steamshaper.puffafilm.ai.node;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class GNLocation implements IGNode {
	@GraphId Long id;
	@Indexed String location;
	@Fetch String description;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public String getOidAsString() {
		return getLocation();
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
