package org.steamshaper.ai.prediction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.steamshaper.ai.prediction.struct.UserSimilarityBean;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.ai.puffafilm.util.TimeTracer;
import org.steamshaper.puffafilm.ai.mapresults.CompareUsersRating;
import org.steamshaper.puffafilm.ai.mapresults.UserRating;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.node.GNUser;
import org.steamshaper.puffafilm.ai.repository.GNMovieRepository;
import org.steamshaper.puffafilm.ai.repository.GNUserRepository;

public class UserBasedPrediction extends APrediction {

	protected Float estimateRating(GNMovie movie, GNUser user, Long timestamp) {
		GNUserRepository gnuRepo = Help.me.getContext().getBean(
				GNUserRepository.class);
		GNMovieRepository gnmRepo = Help.me.getContext().getBean(
				GNMovieRepository.class);

		TimeTracer tt = new TimeTracer();
		tt.timerStart("-START PREDICTION");
		tt.timerStart("--start data gathering");

		tt.timerStart("---gnmRepo.getUsersAndRateForMovie(movie)");
		List<UserRating> userRatings4Film = gnmRepo
				.getUsersAndRateForMovie(movie);
		System.err.println(tt.timerStop());

		tt.timerStart("---gnuRepo.getAverageRatingForUser(user)");
		Float userAvgRating = gnuRepo.getAverageRatingForUser(user);
		System.err.println(tt.timerStop());

//		tt.timerStart("---gnuRepo.IntersectCount(user, movie)");
//		List<User2UserIntersection> usersIntersectList = gnuRepo
//				.IntersectCount(user, movie);
//		System.err.println(tt.timerStop());

		Integer threshold = 0;
		Map<Long, Integer> usersCount = new HashMap<Long, Integer>();
		for (UserRating userRating : userRatings4Film) {
			Integer count = gnuRepo.countRatedMovie(userRating.getUser());
			usersCount.put(userRating.getUser().getId(), count);

			if(count>threshold){
				threshold=count;
			}
		}

		Set<Long> userSet = new HashSet<Long>();

		for (UserRating userRating : userRatings4Film) {
			if(usersCount.get(userRating.getUser().getId()) > threshold - (threshold / 30)){
				userSet.add(userRating.getUser().getId());
			}
		}
//		for (User2UserIntersection thisUser : usersCountList) {
//
//				if (thisUser.getIntersectionCount() > threshold - (threshold / 10)) {
//					userSet.add(thisUser.getUserID());
//				}
//
////			userSet.add(thisUser.getUserID());
//		}

		List<UserSimilarityBean> similarUsersList = new ArrayList<UserSimilarityBean>(
				userRatings4Film.size());
		System.err.println("number of potential similar users: "
				+ userRatings4Film.size());

		tt.timerStart("---compute statistic 4 user");
		for (UserRating userRating : userRatings4Film) {
			GNUser currentUser = userRating.getUser();
			if (userSet.contains(currentUser.getId())) {
				Float rate4Movie = userRating.getRating();
				Float avgRating = gnuRepo.getAverageRatingForUser(currentUser);

				// tt.timerStart("---gnuRepo.getSimilarRatings(movie, user, bean.getUser())");
				List<CompareUsersRating> commonsRating = gnuRepo
						.getSimilarRatings(user, currentUser);
				// System.err.println(tt.timerStop());

				Float distance = 0F;
				Integer commonsMovieCount = 0;
				for (CompareUsersRating rating : commonsRating) {
					Float movieDistance = rating.getUserRating() - avgRating
							- (rating.getMyRating() - userAvgRating);
					if (movieDistance > 0) {
						distance += movieDistance;
					} else {
						distance -= movieDistance;
					}
					commonsMovieCount++;
				}

				if (commonsMovieCount > 0) {
					UserSimilarityBean bean = new UserSimilarityBean(
							currentUser);
					bean.setRate(rate4Movie);
					bean.setAvgRating(avgRating);
					bean.setDistance(distance);
					bean.setCommonsMovieCount(commonsMovieCount);

					similarUsersList.add(bean);
				}
			}
		}
		System.err.println(tt.timerStop());

		System.err.println(tt.timerStop());

		tt.timerStart("--compute prediction");
		Float totalSimilarity = 0F;
		Float weightedSimilarity = 0F;
		for (UserSimilarityBean currentBean : similarUsersList) {
			Float similarity = 1F - (currentBean.getDistance() / 10F / currentBean
					.getCommonsMovieCount());
			weightedSimilarity += similarity
					* (currentBean.getRate() - currentBean.getAvgRating());
			totalSimilarity += similarity;
		}

		Float prediction = (weightedSimilarity / totalSimilarity)
				+ userAvgRating;

		System.err.println(tt.timerStop());
		System.err.println(tt.timerStop());

		return prediction;
	}

}
