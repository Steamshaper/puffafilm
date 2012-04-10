package org.steamshaper.ai.prediction;

import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.steamshaper.ai.prediction.ratestatistics.UserRateFilter;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.ai.puffafilm.util.TimeTracer;
import org.steamshaper.ai.runtime.service.MaeTest;
import org.steamshaper.puffafilm.ai.mapresults.ActorJoinUser;
import org.steamshaper.puffafilm.ai.mapresults.DirectorStat;
import org.steamshaper.puffafilm.ai.mapresults.GenreStats;
import org.steamshaper.puffafilm.ai.mapresults.User2UserIntersection;
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

public class RatesBasedPrediction extends APrediction {

	Logger log = Logger.getLogger("RateBasePrediction");
	Logger logError = Logger.getLogger("bigError");
	Logger errTrendLog  = Logger.getLogger("errorTrend");

	@Override
	protected Float estimateRating(GNMovie thisMovie, GNUser thisUser, Long timestamp) {
		errTrendLog.debug("------------------------------\t"+MaeTest.expected);
		// Recupero i repository dal contesto
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

		// DATI UTENTE
		tt.timerStart("TOTALE QUERY");
		tt.timerStart("TOT - DATI UTENTE");
		tt.timerStart("gnuRepo.countRatedMovie(thisUser)");
		Integer ratedMovie = gnuRepo.countRatedMovie(thisUser);
		log.debug(tt.timerStop());

		tt.timerStart("gnuRepo.getAverageRatingForUser(thisUser)");
		// Voto medio dell'utente
		Float thisUserAvg = gnuRepo.getAverageRatingForUser(thisUser);
		log.debug(tt.timerStop());

		tt.timerStart("gnuRepo.similarUser(thisUser)");
		// Voto medio dell'utente
		List<UserSimilarity> buddyUsers = gnuRepo.similarUsers(thisUser);
		log.debug(tt.timerStop());




		log.debug(tt.timerStop());

		// DATI MOVIE
		tt.timerStart("TOT - DATI MOVIE");
		tt.timerStart("gnmRepo.getAverageRateForMovie");
		// Calcolo il voto medio del film e quindi anche del regista per il film
		Float thisMovieAvg = gnmRepo.getAverageRateForMovie(thisMovie);
		log.debug(tt.timerStop());
		tt.timerStart("gnmRepo.getUsersAndRateForMovie(thisMovie)");
		// Trovo per ogni utente il voto che ha dato al film
		List<UserRating> allRatings4Movie = gnmRepo
				.getUsersAndRateForMovie(thisMovie);
		log.debug(tt.timerStop());
		log.debug(tt.timerStop());

		// DATI DIRECTOR
		tt.timerStart("TOT - DATI DIRECTOR");
		//

		// Di tutti i film che l'utente a votato calcolo la media e il numero di
		// film per ogni regista e lo considero buono se eccedo una soglia
		// maggiorata rispetto al suo valor medio
		Float goodThreshold = getGoodTreshold(thisUserAvg);
		tt.timerStart("gndRepo.findGoodDirectorsForUser(thisUser, goodThreshold)");
		List<DirectorStat> goodDirectorStat = gndRepo.findGoodDirectorsForUser(
				thisUser, goodThreshold);
		log.debug(tt.timerStop());

		// Di tutti i film che l'utente a votato calcolo la media e il numero di
		// film per ogni regista e lo considero buono se eccedo una soglia
		// minorata rispetto al suo valor medio

		Float badThreshold = getBadTreshold(thisUserAvg);
		tt.timerStart("gndRepo.findBadDirectorsForUser(thisUser, goodThreshold)");
		List<DirectorStat> badDirectorStat = gndRepo.findBadDirectorsForUser(
				thisUser, badThreshold);
		log.debug(tt.timerStop());

		tt.timerStart("gndRepo.getDirectorForMovie(thisMovie)");
		// Recupero il regista del fiml
		GNDirector director = gndRepo.getDirectorForMovie(thisMovie);
		log.debug(tt.timerStop());
		// Calcolo il voto medio per il regista
		Float globalAvgForDirector = gndRepo.getGlobalAvgForDirector(director);

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
		tt.timerStart("gndRepo.getGenreForMovie(thisMovie)");
		List<GNGenre> thisMovieGenre = gngRepo.getGenreForMovie(thisMovie);
		log.debug(tt.timerStop());

		// Dato un utente calcolo il numero di film che sono di un determinato
		// GENRE che ha votato, e per ogni genere calcolo il rating medio
		tt.timerStart("gngRepo.genreStatistics(thisUser)");
		List<GenreStats> genreStats = gngRepo.genreStatistics(thisUser);
		log.debug(tt.timerStop());
		log.debug(tt.timerStop());

		// DATI USER-MOVIE
		// tt.timerStart("TOT - DATI USER-MOVIE");
		// tt.timerStart("gnuRepo.ratingAndIntersectCount(thisUser, thisMovie)");
		// // Conto la cardinalitˆ dell'intersezione tra i film del utente con
		// gli
		// // altri utenti, il numero di voti comuni a condizione che l'utente
		// // abbia votato il film in argomento
		// List<User2UserIntersection> count4user = gnuRepo
		// .ratingAndIntersectCount(thisUser, thisMovie);
		//
		// log.debug(tt.timerStop());
		// log.debug(tt.timerStop());

		// DATI USER-MOVIE-ACTOR
		tt.timerStart("TOT - DATI USER-MOVIE");
		tt.timerStart("gnuRepo.ratingAndIntersectCount(thisUser, thisMovie)");
		List<ActorJoinUser> actJoinUser = gnaRepo.favoriteActorInMovieForUser(
				thisMovie, thisUser,4);
		Float maxRank = 0F;
		for (ActorJoinUser aju : actJoinUser) {
			if (aju.actorRank() > maxRank) {
				maxRank = aju.actorRank();
			}
		}
		log.debug(tt.timerStop());

		tt.timerStart("gnmRepo.getMovieYaer(thisMovie)");
		Integer year = gnmRepo.getMovieYear(thisMovie);
		log.debug(tt.timerStop());

//		// Voto medio dell'utente
//		Float avgRating4Year = null;
//		if (year != null) {
//			tt.timerStart("gnuRepo.getYearStatistic(thisUser, year).getAvgRating4Year()");
//			avgRating4Year = gnuRepo.getYearStatistic(thisUser, year);
//			log.debug(tt.timerStop());
//		}

		tt.timerStart("gnuRepo.getMoviesYearStatistics(thisUser)");
		List<YearStatistic> yearStatistics = gnuRepo.getMoviesYearStatistics(thisUser);
		log.debug(tt.timerStop());

		log.debug(tt.timerStop());

		log.debug(tt.timerStop());

		// STAMPA DEI RISULTATI
		// DATI UTENTE
		log.debug("User ID \t" + thisUser.getId());
		log.debug("User OID \t" + thisUser.getOid());
		log.debug("Average for user:\t " + thisUserAvg);
		log.debug("rated for user:\t " + ratedMovie);

		// DATI MOVIE
		log.debug("Movie ID \t" + thisMovie.getId());
		log.debug("Movie OID \t" + thisMovie.getOid());
		log.debug("Voto medio film: \t" + thisMovieAvg);

		// printAllUserRating4Movie(allRatings4Movie);

		// DATI DIRECTOR
		// log.debug("Director ID\t" + director.getId());
		// log.debug("Director ID\t" + director.getOid());
		// log.debug("Director of \t" + director.getDescription());
		//
		// log.debug("GOOD Director");
		// log.debug("Good from \t " + goodThreshold);
		// printDirectorStat(goodDirectorStat);
		// log.debug("BAD Director");
		// log.debug("Bad from \t" + badThreshold);
		// printDirectorStat(badDirectorStat);

		log.debug("USER Avg for director: \t" + avgForDirector);
		log.debug("GLOBAL Avg for director: \t" + globalAvgForDirector);

		// DATI GENERI

		for (GNGenre gen : thisMovieGenre) {
			log.debug(gen.getDescription() + "\t" + gen.getGenre());
		}

		for (GenreStats gs : genreStats) {
			log.debug(gs.genre().getDescription() + " \t" + gs.avgRating()
					+ " \t" + gs.count());
		}
		// DATI USER-MOVIE
		log.debug("Stampo intersezione utente,film->utenti");
		// printRatingIntersect(count4user);

		double prediction = (double) thisUserAvg;
		// Parto dal voto medio dell'utente
		double startPrediction = (double) thisUserAvg;
		errTrendLog.info("START["+startPrediction+"]:\t"+( MaeTest.expected - prediction));
		// Metto in relazione il voto medio del film con il voto medio
		// dell'utente
		log.debug("Start predition:\t" + prediction);
		double temp = 0D;
		// Correggo la previsione con il voto medio del film
		temp = reviewPrevisionCosineWeighted(startPrediction,
				(double) thisMovieAvg);
		temp = Math.abs(temp)>0.51?temp:0;
//		temp = Math.abs(temp)<=1.0?temp:Math.signum(temp)*1.0;//Math.signum(temp)*1.1;

		prediction += temp;
		errTrendLog.info("Voto medio Film:\t"+( MaeTest.expected - prediction));

		log.debug("Prediction update to \t" + prediction + "\t" + temp);
		// Correggo la previsione con la media dell'utente per il regista del
		// film
		temp = reviewPrevisionCosineWeighted(startPrediction,
				(double) avgForDirector);
		prediction += temp;
		errTrendLog.info("DIRECTOR:\t"+( MaeTest.expected - prediction));
		log.debug("Prediction update to \t" + prediction + "\t" + temp);

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
						(1-(gs.count() / (double) ratedMovie)))*(1+(gs.count() / (double) ratedMovie));
				prediction += temp;
				log.debug("Prediction update to \t" + prediction + "\t" + temp);

			}
		}
		errTrendLog.info("GENERE:\t"+( MaeTest.expected - prediction));
		double rankFactor = 0D;
		double actPrediction = getActorBasedPrediction(actJoinUser,startPrediction);
		if(actPrediction!=0){
			prediction += reviewPrevisionCosineWeighted(startPrediction,
					actPrediction);
		}
		errTrendLog.info("Actor:\t"+( MaeTest.expected - prediction));

//		if (avgRating4Year != null) {
//			prediction += reviewPrevisionCosineWeighted(startPrediction,
//					(double) avgRating4Year);
//		}




		//similar user prediction
		UserSimilarity userSim  =null;
		int buddyCounted = 0;
		int maxBuddy = 7;
		for(int i=0; i < buddyUsers.size()&& buddyCounted<=maxBuddy;i++){
			userSim= buddyUsers.remove(0);
			Float rateByBuddy = gnuRepo.userRate4Movie(userSim.user(),thisMovie);
			if(rateByBuddy!=null&&rateByBuddy<=5&&rateByBuddy>=0){
				if(userSim.commonFilmCount()>=50){
					buddyCounted++;
				}else{
					break;
				}
				 temp= reviewPrevisionCosineWeighted(prediction, rateByBuddy-userSim.avgOnCommons()+thisUserAvg);
				 temp= temp/(2+buddyCounted);
				 prediction+=temp;
				 errTrendLog.info("BUDDY["+userSim.commonFilmCount()+" , "+rateByBuddy+" , "+userSim.avgOnCommons()+" , "+(rateByBuddy-userSim.avgOnCommons()+thisUserAvg)+"]:\t"+( MaeTest.expected - prediction));
			}

		}


		if(yearStatistics != null){
			double yearAvg = getYearBasedPrediction(yearStatistics, year, 5);
			if(yearAvg>=0){
				 temp= reviewPrevisionCosineWeighted(startPrediction, yearAvg);
				 temp= Math.abs(temp)>0.20?temp:0;
				 prediction+=temp;
			}
		}
		errTrendLog.info("YEAR:\t"+( MaeTest.expected - prediction));
		UserRateFilter urf = new  UserRateFilter(thisUser);
			return urf.rateOnProbability((float)prediction, 1.5F, ratedMovie);
	}

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

	private <K extends IGNode> HashSet<String> genHSFromList(List<K> thisList) {
		HashSet<String> hs = new HashSet<String>();
		for (K obj : thisList) {
			if (!hs.contains(obj.getOidAsString())) {
				hs.add(obj.getOidAsString());

			}
		}
		return hs;
	}

	private double getActorBasedPrediction(List<ActorJoinUser> actJoinUser,double prediction) {

		int choseTop =4;
		// calcolo una media pesata del rate in base all'importanza dell'attore
		// nel film e del voto dato a quell'attore in un altro film considerato
		double roleWeight;
		double delta =prediction;
		for (ActorJoinUser aju : actJoinUser) {
			if (aju.actorRank() < choseTop) {
				log.debug("Match with  [" + aju.actor().getDescription()
						+ "] ranking in prediction movie [" + aju.actorRank()
						+ "] rate [" + aju.movieRate() + "] ranking ["
						+ aju.movieActorRank() + "]");
				// peso del ruolo interpretato nel film su cui effettuare la
				// predizione
				roleWeight = 1/aju.actorRank()*1/aju.movieActorRank();

				//Ora prendo il film dove ha recitato l'attore che l'utente ha votato e lo comparo con quello che sto cercando di votare
				delta += reviewPrevisionCosineWeighted(delta, aju.movieRate())*Math.sin(roleWeight);
			}
		}
		return delta;
	}

	private double reviewPrevisionCosineWeighted(double prediction,
			double reviewfactor) {
		// Delta tra valori
		double delta = reviewfactor - prediction;
		// Ho bisogno di un valore che vada da [0,1] perci˜ normalizzo
//		delta = delta / MAX_RATING;
		// peso con la funzione coseno
		delta = delta
				* ((1 + Math.pow(
						Math.sin(Math.abs(delta/5)*Math.PI), 2D)) / 2);
		// Riscalo per MAX_RATE
//		delta *= MAX_RATING;

		return delta;
	}

	private void printAllUserRating4Movie(List<UserRating> allRatings4Movie) {
		log.debug("Stampo tutti i voti del film");
		for (UserRating ur : allRatings4Movie) {
			log.debug(ur.getUser().getDescription() + " " + ur.getRating());
		}
	}

	// private List<GNDirector> dirList(List<DirectorStat> directorStat) {
	// List<GNDirector> dirList = new
	// ArrayList<GNDirector>(directorStat.size());
	// for(DirectorStat sd : directorStat){
	// dirList.add(sd.);
	// }
	// return null;
	// }

	private void printDirectorStat(List<DirectorStat> goodDirectorStat) {
		for (DirectorStat sd : goodDirectorStat) {
			log.debug(sd.getDirectorID().getDescription() + "\t"
					+ sd.getAverage() + "\t" + sd.movieWatched());
		}
	}

	private Float getGoodTreshold(Float thisUserAvg) {

		return thisUserAvg + (thisUserAvg * 0.2F);
	}

	private Float getBadTreshold(Float thisUserAvg) {

		return thisUserAvg - (thisUserAvg * 0.2F);
	}

	private void printRatingIntersect(List<User2UserIntersection> count4user) {
		HashSet<Long> hs = new HashSet<Long>();

		for (User2UserIntersection u2u : count4user) {
			int i = 0;
			if (!hs.contains(u2u.getUserID())) {
				hs.add(u2u.getUserID());
				log.debug(u2u.getUserID() + "\t" + u2u.getIntersectionCount()
						+ "\t" + u2u.rating() + "\t" + u2u.userRating());
			} else {
				log.debug("List is not distinct! " + (++i));
			}

		}
	}

}
