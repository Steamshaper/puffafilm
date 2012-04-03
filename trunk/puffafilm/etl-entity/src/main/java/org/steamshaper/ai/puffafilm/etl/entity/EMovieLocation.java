package org.steamshaper.ai.puffafilm.etl.entity;

// TODO: Auto-generated Javadoc
/**
 * The Class EMovieLocations.
 */
public class EMovieLocation extends ADataAdapter {

	/** The movie id. */
	Long movieID;

	/** The location1. */
	String location1;

	/** The location2. */
	String location2;

	/** The location3. */
	String location3;

	/** The location4. */
	String location4;

	public Long getMovieID() {
		return movieID;
	}

	public void setMovieID(Long movieID) {
		this.movieID = movieID;
	}

	public String getLocation1() {
		return location1;
	}

	public void setLocation1(String location1) {
		this.location1 = location1;
	}

	public String getLocation2() {
		return location2;
	}

	public void setLocation2(String location2) {
		this.location2 = location2;
	}

	public String getLocation3() {
		return location3;
	}

	public void setLocation3(String location3) {
		this.location3 = location3;
	}

	public String getLocation4() {
		return location4;
	}

	public void setLocation4(String location4) {
		this.location4 = location4;
	}

	@Override
	public String toString() {
		return "EMovieLocation [movieID=" + movieID + ", location1="
				+ location1 + ", location2=" + location2 + ", location3="
				+ location3 + ", location4=" + location4 + "]";
	}

}
