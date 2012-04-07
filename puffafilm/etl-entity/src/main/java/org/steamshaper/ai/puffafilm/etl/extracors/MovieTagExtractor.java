package org.steamshaper.ai.puffafilm.etl.extracors;

import org.steamshaper.ai.puffafilm.etl.entity.EMovieTag;

public class MovieTagExtractor extends AExtractor<EMovieTag> {

	private static final int MOVIE_ID = 0;
	private static final int TAG_ID = 1;
	private static final int TAG_WEIGHT = 2;

	@Override
	protected EMovieTag createEntityBean(String... fields) {
		EMovieTag movieTag = new EMovieTag();
		boolean integrity = true;
		for (int i = 0; i < fields.length; i++) {
			if (!integrity) {
				movieTag = new EMovieTag();
				break;
			}
			String field = fields[i];
			if (field != null) {
				switch (i) {
				case MOVIE_ID:
					movieTag.setMovieID(Long.valueOf(field));
					break;
				case TAG_ID:
					movieTag.setTagID(Long.valueOf(field));
					break;
				case TAG_WEIGHT:
					movieTag.setTagWeight(Integer.valueOf(field));
					break;
				default:
					log.error("Failing parsing String: " + field);
					integrity = false;
					break;
				}
			}
		}
		if (!checkIntegrity(movieTag)) {
			movieTag = null;
		}
		return movieTag;
	}

	private boolean checkIntegrity(EMovieTag movieTag) {
		if (movieTag.getMovieID() == null || movieTag.getTagID() == null) {
			return false;
		} else {
			return true;
		}
	}

}
