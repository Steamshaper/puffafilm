package org.steamshaper.ai.puffafilm.etl.extracors;

import org.steamshaper.ai.puffafilm.etl.entity.EMovieDirector;

public class MovieDirectorExtractor extends AExtractor<EMovieDirector> {

	private static final int MOVIE_ID = 0;
	private static final int DIRECTOR_ID = 1;
	private static final int DIRECTOR_NAME = 2;

	@Override
	protected EMovieDirector createEntityBean(String... fields) {
		EMovieDirector movieDirector = new EMovieDirector();
		boolean integrity = true;
		for (int i = 0; i < fields.length; i++) {
			if (!integrity) {
				movieDirector = null;
				break;
			}
			String field = fields[i];
			if (field != null) {
				switch (i) {
				case MOVIE_ID:
					movieDirector.setMovieID(Long.valueOf(field));
					break;
				case DIRECTOR_ID:
					movieDirector.setDirectorID(field);
					break;
				case DIRECTOR_NAME:
					movieDirector.setDirectorName(field);
					break;
				default:
					log.error("Failing parsing String: " + field);
					integrity = false;
					break;
				}
			}

		}
		if (!checkIntegrity(movieDirector)) {
			movieDirector = null;
		}
		return movieDirector;
	}

	private boolean checkIntegrity(EMovieDirector movieDirector) {
		if (movieDirector.getMovieID() == null
				|| movieDirector.getDirectorID() == null) {
			return false;
		} else {
			return true;
		}

	}

}
