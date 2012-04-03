package org.steamshaper.ai.puffafilm.etl.entity;

/**
 * The Class EUserTaggedMovies.
 */
public class EUserTaggedMovie extends ADataAdapter {

	/** The user id. */
	Long userID;

	/** The movie id. */
	Long movieID;

	/** The tag id. */
	Long tagID;

	/** The timestamp. */
	Long timestamp;

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

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

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "EUserTaggedMovie [userID=" + userID + ", movieID=" + movieID
				+ ", tagID=" + tagID + ", timestamp=" + timestamp + "]";
	}

}
