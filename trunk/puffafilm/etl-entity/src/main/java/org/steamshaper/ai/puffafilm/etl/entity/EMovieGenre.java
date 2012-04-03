package org.steamshaper.ai.puffafilm.etl.entity;

// TODO: Auto-generated Javadoc
/**
 * The Class EMovieGeneres.
 *
 * @author dfiungo
 */
public class EMovieGenre extends ADataAdapter {

	/** The movie id. */
	Long movieID;

	/** The genre. */
	String genre;

	public Long getMovieID() {
		return movieID;
	}

	public void setMovieID(Long movieID) {
		this.movieID = movieID;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	@Override
	public String toString() {
		return "EMovieGenre [movieID=" + movieID + ", genre=" + genre + "]";
	}
}
