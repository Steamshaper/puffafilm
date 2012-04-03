package org.steamshaper.ai.puffafilm.etl.extracors;

import org.steamshaper.ai.puffafilm.etl.entity.EUserRatedMovie;

public class UserRatedMovieExtractor extends AExtractor<EUserRatedMovie> {

	private static final int USER_ID = 0;
	private static final int MOVIE_ID = 1;
	private static final int RATING = 2;
	private static final int TIMESTAMP= 3;

	@Override
	protected EUserRatedMovie createEntityBean(String... fields) {
		EUserRatedMovie userRatedMovie = new EUserRatedMovie();
		boolean integrity = true;
		for (int i = 0; i < fields.length; i++) {
			if (!integrity) {
				userRatedMovie = new EUserRatedMovie();
				break;
			}
			String field = fields[i];
			if (field != null) {
				switch (i) {
				case USER_ID:
					userRatedMovie.setUserID(Long.valueOf(field));
					break;
				case MOVIE_ID:
					userRatedMovie.setMovieID(Long.valueOf(field));
					break;
				case RATING:
					userRatedMovie.setRating(Float.valueOf(field));
					break;
				case TIMESTAMP:
					userRatedMovie.setTimestamp(Long.valueOf(field));
					break;
				default:
					log.error("Failing parsing String: " + field);
					integrity = false;
					break;
				}
			}
		}
		if (!checkIntegrity(userRatedMovie)) {
			userRatedMovie = null;
		}
		return userRatedMovie;
	}

	private boolean checkIntegrity(EUserRatedMovie userRatedMovie) {
		if(userRatedMovie.getUserID()==null || userRatedMovie.getMovieID()==null){
			return false;
		}else{
			return true;
		}
	}

}
