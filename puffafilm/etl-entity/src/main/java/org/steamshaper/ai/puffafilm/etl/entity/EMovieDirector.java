package org.steamshaper.ai.puffafilm.etl.entity;

/**
 * The Class EMovieDirectors.
 */
public class EMovieDirector extends ADataAdapter {
	Long movieID;
	String directorID;

	/** The director name. */
	String directorName;

	public Long getMovieID() {
		return movieID;
	}

	public void setMovieID(Long movieID) {
		this.movieID = movieID;
	}

	public String getDirectorID() {
		return directorID;
	}

	public void setDirectorID(String directorID) {
		this.directorID = directorID;
	}

	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	@Override
	public String toString() {
		return "EMovieDirector [movieID=" + movieID + ", directorID="
				+ directorID + ", directorName=" + directorName + "]";
	}
}
