package org.steamshaper.puffafilm.ai.node;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.steamshaper.puffafilm.ai.relationship.GRHasRated;
import org.steamshaper.puffafilm.ai.relationship.GRHaveTagged;

@NodeEntity
public class GNUser implements IGNode {
	@GraphId
	Long id;
	@Indexed
	Long oid;
	@Fetch String description;
	@RelatedToVia(elementClass = GRHaveTagged.class, type = "HAVE_TAGGED", direction = Direction.OUTGOING)
	@Fetch Set<GRHaveTagged> taggedMovie;

	@RelatedToVia(elementClass = GRHasRated.class, type = "HAS_RATED", direction = Direction.OUTGOING)
	@Fetch Set<GRHasRated> ratedMovie;

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public Set<GRHaveTagged> getTaggedMovie() {
		return taggedMovie;
	}

	public void setTaggedMovie(Set<GRHaveTagged> taggedMovie) {
		this.taggedMovie = taggedMovie;
	}

	public Set<GRHasRated> getRatedMovie() {
		return ratedMovie;
	}

	public void setRatedMovie(Set<GRHasRated> ratedMovie) {
		this.ratedMovie = ratedMovie;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String getOidAsString() {
		return oid.toString();
	}
	@Override
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
