package org.steamshaper.puffafilm.ai.node;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.steamshaper.puffafilm.ai.relationship.GRActIn;

@NodeEntity
public class GNActor implements IGNode{
	@Indexed String name;
	@Indexed String oid;
	@GraphId Long id;
	@Fetch String description;


	@RelatedToVia (elementClass = GRActIn.class, type="ACT_IN", direction =  Direction.OUTGOING)
	Set<GRActIn> moviesActed;

	public Set<GRActIn> getMoviesActed() {
		return moviesActed;
	}
	public void setMoviesActed(Set<GRActIn> moviesActed) {
		this.moviesActed = moviesActed;
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
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
