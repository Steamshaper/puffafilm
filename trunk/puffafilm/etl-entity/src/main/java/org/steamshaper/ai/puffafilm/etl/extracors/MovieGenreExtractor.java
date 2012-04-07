package org.steamshaper.ai.puffafilm.etl.extracors;

import org.steamshaper.ai.puffafilm.etl.entity.EMovieGenre;

public class MovieGenreExtractor extends AExtractor<EMovieGenre> {

	private static final int MOVIE_ID = 0;
	private static final int GENRE = 1;

	@Override
	protected EMovieGenre createEntityBean(String... fields) {
		EMovieGenre movieGenre = new EMovieGenre();
		boolean integrity = true;
		for (int i = 0; i < fields.length; i++) {
			if (!integrity) {
				movieGenre = null;
				break;
			}
			String field = fields[i];
			if (field != null) {
				switch (i) {
				case MOVIE_ID:
					movieGenre.setMovieID(Long.valueOf(field));
					break;
				case GENRE:
					movieGenre.setGenre(field);
					break;
				default:
					log.error("Failing parsing String: " + field);
					integrity = false;
					break;
				}
			}

		}
		if (!checkIntegrity(movieGenre)) {
			movieGenre = null;
		}
		return movieGenre;
	}

	private boolean checkIntegrity(EMovieGenre movieGenre) {
		if (movieGenre.getMovieID() == null || movieGenre.getGenre() == null) {
			return false;
		} else {
			return true;
		}
	}

}
