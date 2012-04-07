package org.steamshaper.puffafilm.ai.node;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class GNDirector implements IGNode{
	@GraphId Long id;
	@Indexed String oid;
	@Indexed String name;
	@Fetch String description;
	public Long getId() {
		return id;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getOidAsString() {
		if(oid!=null){
			return oid.toString();
		}
		return Long.toString(Long.MIN_VALUE);
	}
	@Override
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
