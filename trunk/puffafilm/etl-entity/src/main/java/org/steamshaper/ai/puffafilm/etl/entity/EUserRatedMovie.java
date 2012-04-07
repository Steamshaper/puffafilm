package org.steamshaper.ai.puffafilm.etl.entity;

public class EUserRatedMovie extends ADataAdapter {
	Long userID;
	Long movieID;
	Float rating;
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

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "EUserRatedMovie [userID=" + userID + ", movieID=" + movieID
				+ ", rating=" + rating + ", timestamp=" + timestamp + "]";
	}

}
