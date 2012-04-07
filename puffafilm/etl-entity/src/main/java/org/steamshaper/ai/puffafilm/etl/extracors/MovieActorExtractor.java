package org.steamshaper.ai.puffafilm.etl.extracors;

import org.steamshaper.ai.puffafilm.etl.entity.EMovieActor;

public class MovieActorExtractor extends AExtractor<EMovieActor> {

	private static final int MOVIE_ID = 0;
	private static final int ACTOR_ID = 1;
	private static final int ACTOR_NAME = 2;
	private static final int RANKING = 3;

	@Override
	protected EMovieActor createEntityBean(String... fields) {
		EMovieActor movieActor = new EMovieActor();
		boolean integrity = true;
		for (int i = 0; i < fields.length; i++) {
			if (!integrity) {
				movieActor = null;
				break;
			}
			String field = fields[i];
			if (field != null) {
				switch (i) {
				case MOVIE_ID:
					movieActor.setMovieID(Long.valueOf(field));
					break;
				case ACTOR_ID:
					movieActor.setActorID(field);
					break;
				case ACTOR_NAME:
					movieActor.setActorName(field);
					break;
				case RANKING:
					movieActor.setRanking(Integer.valueOf(field));
					break;
				default:
					log.error("Failing parsing String: " + field);
					integrity = false;
					break;
				}
			}

		}
		if (!checkIntegrity(movieActor)) {
			movieActor = null;
		}
		return movieActor;
	}

	private boolean checkIntegrity(EMovieActor movieActor) {
		if (movieActor.getMovieID() == null || movieActor.getActorID() == null) {
			return false;
		} else {
			return true;
		}
	}

}
