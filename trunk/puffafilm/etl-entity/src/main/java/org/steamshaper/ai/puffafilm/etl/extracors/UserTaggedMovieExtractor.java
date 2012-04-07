package org.steamshaper.ai.puffafilm.etl.extracors;

import org.steamshaper.ai.puffafilm.etl.entity.EUserTaggedMovie;

public class UserTaggedMovieExtractor extends AExtractor<EUserTaggedMovie> {

	private static final int USER_ID = 0;
	private static final int MOVIE_ID = 1;
	private static final int TAG_ID = 2;
	private static final int TIMESTAMP = 3;

	@Override
	protected EUserTaggedMovie createEntityBean(String... fields) {
		EUserTaggedMovie userTaggedMovie = new EUserTaggedMovie();
		boolean integrity = true;
		for (int i = 0; i < fields.length; i++) {
			if (!integrity) {
				userTaggedMovie = new EUserTaggedMovie();
				break;
			}
			String field = fields[i];
			if (field != null) {
				switch (i) {
				case USER_ID:
					userTaggedMovie.setUserID(Long.valueOf(field));
					break;
				case MOVIE_ID:
					userTaggedMovie.setMovieID(Long.valueOf(field));
					break;
				case TAG_ID:
					userTaggedMovie.setTagID(Long.valueOf(field));
					break;
				case TIMESTAMP:
					userTaggedMovie.setTimestamp(Long.valueOf(field));
					break;

				default:
					log.error("Failing parsing String: " + field);
					integrity = false;
					break;
				}
			}
		}
		if (!checkIntegrity(userTaggedMovie)) {
			userTaggedMovie = null;
		}
		return userTaggedMovie;
	}

	private boolean checkIntegrity(EUserTaggedMovie userTaggedMovie) {
		if (userTaggedMovie.getMovieID() == null
				|| userTaggedMovie.getTagID() == null) {
			return false;
		} else {
			return true;
		}
	}

}
