package org.steamshaper.ai.puffafilm.etl.extracors;

import org.steamshaper.ai.puffafilm.etl.entity.EMovieLocation;

public class MovieLocationExtractor extends AExtractor<EMovieLocation> {

	private static final int MOVIE_ID = 0;
	private static final int LOCATION_1 = 1;
	private static final int LOCATION_2 = 2;
	private static final int LOCATION_3 = 3;
	private static final int LOCATION_4 = 4;

	@Override
	protected EMovieLocation createEntityBean(String... fields) {
		EMovieLocation movieLocation = new EMovieLocation();
		boolean integrity = true;
		for (int i = 0; i < fields.length; i++) {
			if (!integrity) {
				movieLocation = new EMovieLocation();
				break;
			}
			String field = fields[i];
			if (field != null) {
				switch (i) {
				case MOVIE_ID:
					movieLocation.setMovieID(Long.valueOf(field));
					break;
				case LOCATION_1:
					movieLocation.setLocation1(field);
					break;
				case LOCATION_2:
					movieLocation.setLocation2(field);
					break;
				case LOCATION_3:
					movieLocation.setLocation3(field);
					break;
				case LOCATION_4:
					movieLocation.setLocation4(field);
					break;
				default:
					log.error("Failing parsing String: " + field);
					integrity = false;
					break;
				}
			}
		}
		if (!checkIntegrity(movieLocation)) {
			movieLocation = null;
		}
		return movieLocation;
	}

	private boolean checkIntegrity(EMovieLocation movieLocation) {
		if (movieLocation.getMovieID() == null) {
			return false;
		} else {
			if (movieLocation.getLocation1() == null
					&& movieLocation.getLocation2() == null
					&& movieLocation.getLocation3() == null
					&& movieLocation.getLocation4() == null) {
				return false;
			} else {
				return true;
			}
		}
	}

}
