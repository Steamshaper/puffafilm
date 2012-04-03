package org.steamshaper.ai.puffafilm.etl.extracors;

import org.steamshaper.ai.puffafilm.etl.entity.EMovies;

public class MovieExtractor extends AExtractor<EMovies> {

	private static final int ID = 0;
	private static final int TITLE = 1;
	private static final int IMDB_ID = 2;
	private static final int SPANISH_TITLE = 3;
	private static final int IMDB_PICTURE_URL = 4;
	private static final int YEAR = 5;
	private static final int RT_ID = 6;
	private static final int RT_ALLCRITICS_RATING = 7;
	private static final int RT_ALLCRITICS_NUMREVIEWS = 8;
	private static final int RT_ALLCRITICS_NUMFRESH = 9;
	private static final int RT_ALLCRITICS_NUMROTTEN = 10;
	private static final int RT_ALLCRITICS_SCORE = 11;
	private static final int RT_TOPCRITICS_RATING = 12;
	private static final int RT_TOPCRITICS_NUMREVIEWS = 13;
	private static final int RT_TOPCRITICS_NUMFRESH = 14;
	private static final int RT_TOPCRITICS_NUMROTTEN = 15;
	private static final int RT_TOPCRITICS_SCORE = 16;
	private static final int RT_AUDIENCE_RATING = 17;
	private static final int RT_AUDIENCE_NUMRATINGS = 18;
	private static final int RT_AUDIENCE_SCORE = 19;
	private static final int RT_PICTURE_URL = 20;

	private static final String BANNED = "\\N";

	@Override
	protected EMovies createEntityBean(String... fields) {
		EMovies movie = new EMovies();
		boolean integrity = true;
		for (int i = 0; i < fields.length; i++) {
			if (!integrity) {
				movie = null;
				break;
			}
			String field = fields[i];
			if (field != null && !BANNED.equals(field)) {
				switch (i) {
				case ID:
					movie.setId(Long.valueOf(field));
					break;
				case TITLE:
					movie.setTitle(field);
					break;
				case IMDB_ID:
					movie.setImdbID(field);
					break;
				case SPANISH_TITLE:
					movie.setSpanishTitle(field);
					break;
				case IMDB_PICTURE_URL:
					movie.setImdbPictureURL(field);
					break;
				case YEAR:
					movie.setYear(Integer.valueOf(field));
					break;
				case RT_ID:
					movie.setRtID(field);
					break;
				case RT_ALLCRITICS_RATING:
					movie.setRtAllCriticsRating(Float.valueOf(field));
					break;
				case RT_ALLCRITICS_NUMREVIEWS:
					movie.setRtAllCriticsNumReviews(Integer.valueOf(field));
					break;
				case RT_ALLCRITICS_NUMFRESH:
					movie.setRtAllCriticsNumFresh(Integer.valueOf(field));
					break;
				case RT_ALLCRITICS_NUMROTTEN:
					movie.setRtAllCriticsNumRotten(Integer.valueOf(field));
					break;
				case RT_ALLCRITICS_SCORE:
					movie.setRtAllCriticsScore(Integer.valueOf(field));
					break;
				case RT_TOPCRITICS_RATING:
					movie.setRtTopCriticsRating(Float.valueOf(field));
					break;
				case RT_TOPCRITICS_NUMREVIEWS:
					movie.setRtTopCriticsNumReviews(Integer.valueOf(field));
					break;
				case RT_TOPCRITICS_NUMFRESH:
					movie.setRtTopCriticsNumFresh(Integer.valueOf(field));
					break;
				case RT_TOPCRITICS_NUMROTTEN:
					movie.setRtTopCriticsNumRotten(Integer.valueOf(field));
					break;
				case RT_TOPCRITICS_SCORE:
					movie.setRtTopCriticsScore(Integer.valueOf(field));
					break;
				case RT_AUDIENCE_RATING:
					movie.setRtAudienceRating(Float.valueOf(field));
					break;
				case RT_AUDIENCE_NUMRATINGS:
					movie.setRtAudienceNumRatings(Integer.valueOf(field));
					break;
				case RT_AUDIENCE_SCORE:
					movie.setRtAudienceScore(Integer.valueOf(field));
					break;
				case RT_PICTURE_URL:
					movie.setRtPictureURL(field);
					break;

				default:
					log.error("Failing parsing String: " + field);
					integrity = false;
					break;
				}
			}
		}
		if (!checkIntegrity(movie)) {
			movie = null;
		}
		return movie;
	}

	private boolean checkIntegrity(EMovies movie) {
		if (movie.getId() == null) {
			return false;
		} else {
			return true;
		}

	}

}
