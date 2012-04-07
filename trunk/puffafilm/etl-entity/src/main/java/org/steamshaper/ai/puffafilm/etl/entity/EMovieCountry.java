package org.steamshaper.ai.puffafilm.etl.entity;

// TODO: Auto-generated Javadoc
/**
 * The Class EMovieCountries.
 *
 * @author dfiungo
 */
public class EMovieCountry extends ADataAdapter {

	/** The movie id. */
	Long movieID;

	/** The country. */
	String country;

	public Long getMovieID() {
		return movieID;
	}

	public void setMovieID(Long movieID) {
		this.movieID = movieID;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public String toString() {
		return "EMovieCountries [movieID=" + movieID + ", country=" + country
				+ "]";
	}
}
