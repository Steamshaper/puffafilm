package org.steamshaper.ai.prediction;

import org.apache.log4j.Logger;
import org.steamshaper.ai.exceptions.PredictionParameterException;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.node.GNUser;
import org.steamshaper.puffafilm.ai.repository.GNMovieRepository;
import org.steamshaper.puffafilm.ai.repository.GNUserRepository;

public abstract class APrediction {

	protected static final Float MAX_RATING = 5F;
	protected static final Float MIN_RATING = 0F;
	protected static final Float STEP = 0.5F;

	protected Logger log = Logger.getLogger(APrediction.class);

	public final Float getPrediction(Long userId, Long movieId,Long timestamp) {

		log.info("Start Prediction 4 User [oid:" + userId + "] - Movie [oid:" + movieId +"]");
		GNUserRepository gnuRepo = Help.me.getContext().getBean(
				GNUserRepository.class);
		GNMovieRepository gnmRepo = Help.me.getContext().getBean(
				GNMovieRepository.class);

		GNUser user = gnuRepo.findUserByOid(userId);
		GNMovie movie = gnmRepo.findMovieByOid(movieId);

		if (user!=null && movie!=null){
			Float prediction =  normalize(estimateRating(movie, user, timestamp));
			log.info("User [oid:" + userId + "] - Movie [oid:" + movieId +"] --> Prediction: " + prediction);
			System.err.println("User [oid:" + userId + "] - Movie [oid:" + movieId +"] --> Prediction: " + prediction);
			return prediction;
		}else{
			log.error("User or Movie unknown");
			throw new PredictionParameterException("Parameters are not correct [oid:" + userId + "] - Movie [oid:" + movieId +"]");
		}
	}

	protected final Float normalize(Float rate) {
		int integerPart = (int) rate.floatValue();
		int mantissa = (int) ((rate - integerPart) * 100);
		float validRate = 0F;
		if (mantissa > 25 && mantissa <= 75) {
			validRate = 0.5F;

		} else if (mantissa > 75) {
			validRate = 1F;
		}
		validRate += integerPart;
		if (validRate > 5F) {
			validRate = 5F;
		} else if (validRate < 0F) {
			validRate = 0F;
		}
		return validRate;
	}

	protected final Float discretize(Float continuousPrediction) {
		Integer intPart = (int)(continuousPrediction / STEP);
		Float discretePrediction = intPart*STEP;
		if(continuousPrediction-discretePrediction>STEP/2){
			discretePrediction += STEP;
		}
		return discretePrediction;
	}

	protected abstract Float estimateRating(GNMovie movie, GNUser user, Long timestamp);

}
