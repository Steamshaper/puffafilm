package org.steamshaper.ai.prediction;

import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.steamshaper.ai.prediction.ratestatistics.UserRateFilter;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.ai.puffafilm.util.TimeTracer;
import org.steamshaper.puffafilm.ai.mapresults.ActorJoinUser;
import org.steamshaper.puffafilm.ai.mapresults.GenreStats;
import org.steamshaper.puffafilm.ai.mapresults.UserRating;
import org.steamshaper.puffafilm.ai.mapresults.UserSimilarity;
import org.steamshaper.puffafilm.ai.mapresults.YearStatistic;
import org.steamshaper.puffafilm.ai.node.GNDirector;
import org.steamshaper.puffafilm.ai.node.GNGenre;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.node.GNUser;
import org.steamshaper.puffafilm.ai.node.IGNode;
import org.steamshaper.puffafilm.ai.repository.GNActorRepository;
import org.steamshaper.puffafilm.ai.repository.GNDirectorRepository;
import org.steamshaper.puffafilm.ai.repository.GNGenreRepository;
import org.steamshaper.puffafilm.ai.repository.GNMovieRepository;
import org.steamshaper.puffafilm.ai.repository.GNUserRepository;

/**
 * The Class MultipleApproximationPrediction.
 */
public class MultipleApproximationPrediction extends APrediction {

	/** The log. */
	Logger log = Logger.getLogger("RateBasePrediction");

	/* (non-Javadoc)
	 * @see org.steamshaper.ai.prediction.APrediction#estimateRating(org.steamshaper.puffafilm.ai.node.GNMovie, org.steamshaper.puffafilm.ai.node.GNUser)
	 */
	@Override
	protected Float estimateRating(GNMovie thisMovie, GNUser thisUser) {
		GNUserRepository gnuRepo = Help.me.getContext().getBean(
				GNUserRepository.class);
		GNMovieRepository gnmRepo = Help.me.getContext().getBean(
				GNMovieRepository.class);
		GNDirectorRepository gndRepo = Help.me.getContext().getBean(
				GNDirectorRepository.class);
		GNGenreRepository gngRepo = Help.me.getContext().getBean(
				GNGenreRepository.class);
		GNActorRepository gnaRepo = Help.me.getContext().getBean(
				GNActorRepository.class);
		TimeTracer tt = new TimeTracer();

		tt.timerStart("TOTALE QUERY");

		// DATI UTENTE
		tt.timerStart("TOT - DATI UTENTE");
		tt.timerStart("Conteggio dei film votati dall'utente");
		Integer ratedMovie = gnuRepo.countRatedMovie(thisUser);
		log.debug(tt.timerStop(ratedMovie));

		tt.timerStart("Voto medio dell'utente");
		// Voto medio dell'utente
		Float thisUserAvg = gnuRepo.getAverageRatingForUser(thisUser);
		log.debug(tt.timerStop(thisUserAvg));

		tt.timerStart("Similaritˆ utente");
		List<UserSimilarity> buddyUsers = gnuRepo.similarUsers(thisUser);
		log.debug(tt.timerStop("Trovati " + buddyUsers.size()
				+ " utenti simili"));

		log.debug(tt.timerStop());

		// DATI MOVIE
		tt.timerStart("TOT - DATI MOVIE");
		tt.timerStart("Voto medio utente");
		// Calcolo il voto medio del film e quindi anche del regista per il film
		Float thisMovieAvg = gnmRepo.getAverageRateForMovie(thisMovie);
		log.debug(tt.timerStop(thisMovieAvg));
		tt.timerStart("Recupero lista dei film votati dall'utente");
		// Trovo per ogni utente il voto che ha dato al film
		List<UserRating> allRatings4Movie = gnmRepo
				.getUsersAndRateForMovie(thisMovie);
		log.debug(tt.timerStop(allRatings4Movie.size()));
		log.debug(tt.timerStop());

		// DATI PER I REGISTI
		tt.timerStart("TOT - DATI DIRECTOR");
		tt.timerStart("Ricerco regista del film");
		// Recupero il regista del fiml
		GNDirector director = gndRepo.getDirectorForMovie(thisMovie);
		log.debug(tt.timerStop(director.getDescription()));

		// Se l'utente ha votato qualche film del regista ne calcolo la media
		// altrimenti lo valorizzo con il valor medio dell'utente
		tt.timerStart("gndRepo.getAvgForDirector(thisUser, director);");
		Float avgForDirector = gndRepo.getAvgForDirector(thisUser, director);
		avgForDirector = avgForDirector == null ? thisUserAvg : avgForDirector;
		log.debug(tt.timerStop());
		log.debug(tt.timerStop());

		// DATI GENERI
		// Il genere del film
		tt.timerStart("TOT - DATI GENERI");
		tt.timerStart("Recupero i generi associati al film");
		List<GNGenre> thisMovieGenre = gngRepo.getGenreForMovie(thisMovie);
		log.debug(tt.timerStop(thisMovieGenre.size()));

		// Dato un utente calcolo il numero di film che sono di un determinato
		// GENRE che ha votato, e per ogni genere calcolo il rating medio
		tt.timerStart("Calcolo dei film per ogni genere, votato dall'utente");
		List<GenreStats> genreStats = gngRepo.genreStatistics(thisUser);
		log.debug(tt.timerStop());
		log.debug(tt.timerStop());

		// DATI USER-MOVIE-ACTOR
		tt.timerStart("TOT - DATI USER-MOVIE");
		tt.timerStart("Attori preferiti nel film");
		List<ActorJoinUser> actJoinUser = gnaRepo.favoriteActorInMovieForUser(
				thisMovie, thisUser, 4);
		Float maxRank = 0F;
		for (ActorJoinUser aju : actJoinUser) {
			if (aju.actorRank() > maxRank) {
				maxRank = aju.actorRank();
			}
		}
		log.debug(tt.timerStop());

		tt.timerStart("Anno del film");
		Integer year = gnmRepo.getMovieYear(thisMovie);
		log.debug(tt.timerStop(year));

		tt.timerStart("gnuRepo.getMoviesYearStatistics(thisUser)");
		List<YearStatistic> yearStatistics = gnuRepo
				.getMoviesYearStatistics(thisUser);
		log.debug(tt.timerStop());

		log.debug(tt.timerStop());

		log.debug(tt.timerStop());



		//Inizializzo la predizione con il voto medio
		double prediction = (double) thisUserAvg;
		// Parto dal voto medio dell'utente
		double startPrediction = (double) thisUserAvg;

		// Metto in relazione il voto medio del film con il voto medio
		// dell'utente
		log.debug("Start predition:\t" + prediction);
		double temp = 0D;
		// Correggo la previsione con il voto medio del film
		temp = reviewPrevisionCosineWeighted(startPrediction,
				(double) thisMovieAvg);
		temp = Math.abs(temp) > 0.51 ? temp : 0;

		prediction += temp;

		log.debug("Prediction update to \t" + prediction + "\t" + temp);
		// Correggo la previsione con la media dell'utente per il regista del
		// film
		temp = reviewPrevisionCosineWeighted(startPrediction,
				(double) avgForDirector);
		prediction += temp;

		// Prendo i generi associati al film e verifico sulle statistiche del
		// film il voto medio
		HashSet<String> genreHS = genHSFromList(thisMovieGenre);
		for (GenreStats gs : genreStats) {
			if (genreHS.contains(gs.genre().getOidAsString())) {

				// TO DO Tenere in considerazione la frequanza di apparizione

				log.debug("Match with GENRE [" + gs.genre().getGenre()
						+ "] avg [" + gs.avgRating() + "]");
				temp = reviewPrevisionCosineWeighted(startPrediction,
						gs.avgRating());
				temp *= reviewPrevisionCosineWeighted(1D,
						(1 - (gs.count() / (double) ratedMovie)))
						* (1 + (gs.count() / (double) ratedMovie));
				prediction += temp;
				log.debug("Prediction update to \t" + prediction + "\t" + temp);

			}
		}
		double actPrediction = getActorBasedPrediction(actJoinUser,
				startPrediction);
		if (actPrediction != 0) {
			prediction += reviewPrevisionCosineWeighted(startPrediction,
					actPrediction);
		}

		// if (avgRating4Year != null) {
		// prediction += reviewPrevisionCosineWeighted(startPrediction,
		// (double) avgRating4Year);
		// }

		// similar user prediction
		UserSimilarity userSim = null;
		int buddyCounted = 0;
		int maxBuddy = 7;
		for (int i = 0; i < buddyUsers.size() && buddyCounted <= maxBuddy; i++) {
			userSim = buddyUsers.remove(0);
			Float rateByBuddy = gnuRepo.userRate4Movie(userSim.user(),
					thisMovie);
			if (rateByBuddy != null && rateByBuddy <= 5 && rateByBuddy >= 0) {
				if (userSim.commonFilmCount() >= 50) {
					buddyCounted++;
				} else {
					break;
				}
				temp = reviewPrevisionCosineWeighted(prediction, rateByBuddy
						- userSim.avgOnCommons() + thisUserAvg);
				temp = temp / (2 + buddyCounted);
				prediction += temp;
			}

		}

		if (yearStatistics != null) {
			double yearAvg = getYearBasedPrediction(yearStatistics, year, 5);
			if (yearAvg >= 0) {
				temp = reviewPrevisionCosineWeighted(startPrediction, yearAvg);
				temp = Math.abs(temp) > 0.20 ? temp : 0;
				prediction += temp;
			}
		}
		UserRateFilter urf = new UserRateFilter(thisUser);
		return urf.rateOnProbability((float) prediction, 1.5F, ratedMovie);
	}

	/**
	 * Review prevision cosine weighted.
	 *
	 * @param prediction the prediction
	 * @param reviewfactor the reviewfactor
	 * @return the double
	 */
	private double reviewPrevisionCosineWeighted(double prediction,
			double reviewfactor) {
		// Delta tra valori
		double delta = reviewfactor - prediction;
		delta = delta
				* ((1 + Math.pow(Math.sin(Math.abs(delta / 5) * Math.PI), 2D)) / 2);
		// Riscalo per MAX_RATE
		// delta *= MAX_RATING;

		return delta;
	}

	/**
	 * Gen hs from list.
	 *
	 * @param <K> the key type
	 * @param thisList the this list
	 * @return the hash set
	 */
	private <K extends IGNode> HashSet<String> genHSFromList(List<K> thisList) {
		HashSet<String> hs = new HashSet<String>();
		for (K obj : thisList) {
			if (!hs.contains(obj.getOidAsString())) {
				hs.add(obj.getOidAsString());

			}
		}
		return hs;
	}

	/**
	 * Gets the actor based prediction.
	 *
	 * @param actJoinUser the act join user
	 * @param prediction the prediction
	 * @return the actor based prediction
	 */
	private double getActorBasedPrediction(List<ActorJoinUser> actJoinUser,
			double prediction) {

		int choseTop = 4;
		// calcolo una media pesata del rate in base all'importanza dell'attore
		// nel film e del voto dato a quell'attore in un altro film considerato
		double roleWeight;
		double delta = prediction;
		for (ActorJoinUser aju : actJoinUser) {
			if (aju.actorRank() < choseTop) {
				log.debug("Match with  [" + aju.actor().getDescription()
						+ "] ranking in prediction movie [" + aju.actorRank()
						+ "] rate [" + aju.movieRate() + "] ranking ["
						+ aju.movieActorRank() + "]");
				// peso del ruolo interpretato nel film su cui effettuare la
				// predizione
				roleWeight = 1 / aju.actorRank() * 1 / aju.movieActorRank();

				// Ora prendo il film dove ha recitato l'attore che l'utente ha
				// votato e lo comparo con quello che sto cercando di votare
				delta += reviewPrevisionCosineWeighted(delta, aju.movieRate())
						* Math.sin(roleWeight);
			}
		}
		return delta;
	}

	/**
	 * Gets the year based prediction.
	 *
	 * @param yearStatistics the year statistics
	 * @param year the year
	 * @param epsilon the epsilon
	 * @return the year based prediction
	 */
	private double getYearBasedPrediction(List<YearStatistic> yearStatistics,
			Integer year, int epsilon) {
		double yearPrediction=-1;
		double totalCount=0;
		double weightedRate=0;
		for(YearStatistic stats: yearStatistics){
			double distance =Math.abs(year - stats.getYear());
			if(distance<= epsilon){
				double weight = 1 / distance+1;
				totalCount += weight;
				weightedRate += weight*stats.getAvgRating4Year();
			}
		}
		if(totalCount>0){
			yearPrediction = weightedRate/totalCount;
		}
		return yearPrediction;
	}
}
