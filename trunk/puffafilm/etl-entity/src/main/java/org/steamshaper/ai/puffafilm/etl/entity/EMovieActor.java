package org.steamshaper.ai.puffafilm.etl.entity;


// TODO: Auto-generated Javadoc
/**
 * The Class EMovieActors.
 */
public class EMovieActor extends ADataAdapter{

	/** The movie id. */
	Long movieID;

	/** The actor id. */
	String actorID;

	/** The actor name. */
	String actorName;

	/** The ranking. */
	Integer ranking;

	public Long getMovieID() {
		return movieID;
	}

	public void setMovieID(Long movieID) {
		this.movieID = movieID;
	}

	public String getActorID() {
		return actorID;
	}

	public void setActorID(String actorID) {
		this.actorID = actorID;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	@Override
	public String toString() {
		return "EMovieActors [movieID=" + movieID + ", actorID=" + actorID
				+ ", actorName=" + actorName + ", ranking=" + ranking + "]";
	}

}
