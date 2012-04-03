package org.steamshaper.ai.runtime.service;

import java.util.List;

import org.steamshaper.ai.prediction.UserBasedPrediction;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.ai.runtime.AService;
import org.steamshaper.puffafilm.ai.mapresults.UserRating;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.node.GNUser;
import org.steamshaper.puffafilm.ai.repository.GNMovieRepository;
import org.steamshaper.puffafilm.ai.repository.GNUserRepository;

public class MainService extends AService {

	private DataLoader dataInput = null;

	@Override
	public void run() {
		if(getDataInput()!=null){
			for(String s : getDataInput().getData()){
				System.err.println("Input ["+s+"]");
			}

		}
		GNUserRepository gnuRepo = Help.me.getContext().getBean(
				GNUserRepository.class);
		System.err.println("Test getAverageRatingForUser(190L)");
		Float value = gnuRepo.getAverageRatingForUser(190L);
		System.err.println("Test return :" + value);

		System.err.println("Test getAllRatingUserByTitle(The Wrestler);");
		GNMovieRepository gnmRepo = Help.me.getContext().getBean(
				GNMovieRepository.class);
		Iterable<GNUser> users = gnmRepo
				.getAllRatingUserByTitle("The Wrestler");
		for (GNUser user : users) {
			System.err.println(user.getDescription());
		}
		System.err.println("Test findMovieByTitle(The Matrix);");
		GNMovie matrix = gnmRepo.findMovieByTitle("The Matrix");
		System.err.println(matrix.getDescription());

		System.err.println("Test getAverageRateForMovie(matrix);");
		Float averageForMatrix = gnmRepo.getAverageRateForMovie(matrix);
		System.err.println("Average 4 matrix : " + averageForMatrix);


		GNMovie movie = gnmRepo.findMovieByOid(64839L);
		GNUser thisUser = gnuRepo.findUserByOid(30886L);
		List<UserRating> userRatings = gnmRepo.getUsersAndRateForMovie(
				movie);

		System.err.println("User rate the same film :");
		for (UserRating usrRate : userRatings) {
			System.err.println("User OID : " + usrRate.getUser().getOid()
					+" RATE ["+usrRate.getRating()+"]");
		}

		System.err.println("Average Rate form Movie");
		System.err.println("Average rate for movie 64839 : "+gnmRepo.getAverageRatingForMovie(64839L));

		System.err.println("Test Prediction 4 movie[64839L] and user[30886L]");
		UserBasedPrediction prediction = new UserBasedPrediction();
		Float rating = prediction.getPrediction(30886L, 64839L);
		System.err.println("Prediction 4 movie[64839L] and user[30886L] is : "+ rating);


	}
	public DataLoader getDataInput() {
		return dataInput;
	}
	public void setDataInput(DataLoader dataInput) {
		this.dataInput = dataInput;
	}

}
