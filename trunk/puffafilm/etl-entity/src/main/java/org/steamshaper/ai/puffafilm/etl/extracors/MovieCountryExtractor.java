package org.steamshaper.ai.puffafilm.etl.extracors;

import org.steamshaper.ai.puffafilm.etl.entity.EMovieCountry;

public class MovieCountryExtractor extends AExtractor<EMovieCountry> {

	private static final int MOVIE_ID = 0;
	private static final int COUNTRY = 1;

	@Override
	protected EMovieCountry createEntityBean(String... fields) {
		EMovieCountry movieCountry = new EMovieCountry();
		boolean integrity = true;
		for (int i = 0; i < fields.length; i++) {
			if (!integrity) {
				movieCountry = null;
				break;
			}
			String field = fields[i];
			if (field != null) {
				switch (i) {
				case MOVIE_ID:
					movieCountry.setMovieID(Long.valueOf(field));
					break;
				case COUNTRY:
					movieCountry.setCountry(field);
					break;
				default:
					log.error("Failing parsing String: " + field);
					integrity = false;
					break;
				}
			}
		}
		if (!checkIntegrity(movieCountry)) {
			movieCountry = null;
		}
		return movieCountry;
	}

	private boolean checkIntegrity(EMovieCountry movieCountry) {
		if (movieCountry.getMovieID() == null
				|| movieCountry.getCountry() == null) {
			return false;
		} else {
			return true;
		}
	}

}
