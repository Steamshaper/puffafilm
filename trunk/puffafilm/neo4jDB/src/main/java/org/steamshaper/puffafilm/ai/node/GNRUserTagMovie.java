package org.steamshaper.puffafilm.ai.node;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class GNRUserTagMovie implements IGNode{
	@GraphId Long id;
	@Fetch String description;
	@Indexed String oid;

	@RelatedTo(type = "MOVIE_UTM", direction = Direction.INCOMING)
	GNMovie movie;
	@RelatedTo(type = "TAG_UTM", direction = Direction.INCOMING)
	GNTag tag;
	@RelatedTo(type = "USER_UTM", direction = Direction.INCOMING)
	GNUser user;

	Long timastamp;

	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
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
	public GNUser getUser() {
		return user;
	}
	public void setUser(GNUser user) {
		this.user = user;
	}
	public Long getTimastamp() {
		return timastamp;
	}
	public void setTimastamp(Long timastamp) {
		this.timastamp = timastamp;
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
	@Override
	public String getOidAsString() {
		return oid;
	}
}
