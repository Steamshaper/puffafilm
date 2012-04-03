package org.steamshaper.ai.puffafilm.etl.entity;

/**
 * The Class EMovieTags.
 */
public class EMovieTag extends ADataAdapter {

	/** The movie id. */
	Long movieID;

	/** The tag id. */
	Long tagID;

	/** The tag weight. */
	Integer tagWeight;

	public Long getMovieID() {
		return movieID;
	}

	public void setMovieID(Long movieID) {
		this.movieID = movieID;
	}

	public Long getTagID() {
		return tagID;
	}

	public void setTagID(Long tagID) {
		this.tagID = tagID;
	}

	public Integer getTagWeight() {
		return tagWeight;
	}

	public void setTagWeight(Integer tagWeight) {
		this.tagWeight = tagWeight;
	}

	@Override
	public String toString() {
		return "EMovieTag [movieID=" + movieID + ", tagID=" + tagID
				+ ", tagWeight=" + tagWeight + "]";
	}
	
}
