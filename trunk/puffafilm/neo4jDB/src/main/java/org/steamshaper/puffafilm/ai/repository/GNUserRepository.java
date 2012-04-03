package org.steamshaper.puffafilm.ai.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.puffafilm.ai.mapresults.CompareUsersRating;
import org.steamshaper.puffafilm.ai.mapresults.User2UserIntersection;
import org.steamshaper.puffafilm.ai.mapresults.UserSimilarity;
import org.steamshaper.puffafilm.ai.mapresults.YearStatistic;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.node.GNUser;

public interface GNUserRepository extends GraphRepository<GNUser> {

	@Query("START user=node:__types__(className = \"org.steamshaper.puffafilm.ai.node.GNUser\") WHERE user.oid = {0}  RETURN user")
	GNUser findUserByOid(long oid);

	@Query("START n=node:__types__(className = \"org.steamshaper.puffafilm.ai.node.GNUser\") MATCH n-[r:HAS_RATED]->f WHERE n.oid = {0}  RETURN avg(r.rating)")
	Float getAverageRatingForUser(Long oid);

	@Query("START n=node({0}) MATCH n-[r:HAS_RATED]->f  RETURN avg(r.rating)")
	Float getAverageRatingForUser(GNUser user);

	@Query("START n=node:__types__(className = \"org.steamshaper.puffafilm.ai.node.GNUser\") MATCH n-[r:HAS_RATED]->f WHERE n.oid = {0} return count(f)")
	Integer countRatedMovie(Long oid);

	@Query("start u=node({0}) match u-[:HAS_RATED]->f RETURN count(f)")
	Integer countRatedMovie(GNUser user);

	@Query("START film=node({0}), user=node({1}) match user-[r:HAS_RATED]-> film RETURN r.rating")
	Float getUserRateForMovie(GNMovie movie, GNUser user);

	@Query("START thisUser=node({0}), user=node({1}) MATCH thisUser-[cr:HAS_RATED]->movie<-[r:HAS_RATED]-user RETURN user, movie, r.rating, cr.rating")
	List<CompareUsersRating> getSimilarRatings(GNUser thisUser, GNUser otherUser);

	@Query("START thisUser=node({0}), thisMovie=node({1}) MATCH thisUser-[tr:HAS_RATED]->ratedMovie<-[:HAS_RATED]-otherUser-[r4m:HAS_RATED]->thisMovie RETURN ID(otherUser) ,ratedMovie,avg(r4m.rating),avg(tr.rating) ORDER BY count(ratedMovie) DESC")
	List<User2UserIntersection> ratingAndIntersectCount(GNUser thisUser,
			GNMovie thisMovie);

	@Query("START thisUser=node({0}), thisMovie=node({1}) MATCH thisUser-[:HAS_RATED]->ratedMovie<-[:HAS_RATED]-otherUser-[r4m:HAS_RATED]->thisMovie RETURN ID(otherUser), count(ratedMovie) ORDER BY count(ratedMovie) DESC")
	List<User2UserIntersection> IntersectCount(GNUser thisUser,
			GNMovie thisMovie);

	@Query("START thisUser=node({0}), otherUser=node({1}) MATCH thisUser-[:HAS_RATED]->ratedMovie<-[:HAS_RATED]-otherUser RETURN ID(otherUser), count(ratedMovie)")
	User2UserIntersection IntersectCount(GNUser thisUser, GNUser otherUser);

	@Query("start n=node({0}) MATCH n-[rate:HAS_RATED]->movie WHERE rate.rating = {1} return count(movie)")
	Integer howManyForRate(GNUser user, Float forRate);

	@Query("START thisUser=node({0}) MATCH thisUser-[rate:HAS_RATED]->movie RETURN movie.year, avg(rate.rating), count(movie.year) ORDER BY movie.year")
	List<YearStatistic> getMoviesYearStatistics(GNUser thisuser);

	@Query("START thisUser=node({0}) MATCH thisUser-[rate:HAS_RATED]->movie WHERE movie.year={1} RETURN avg(rate.rating)")
	Float getYearStatistic(GNUser thisUser, Integer year);

	@Query("start user=node({0}) MATCH user-[ur:HAS_RATED]->movies<-[allr:HAS_RATED]-USERS WHERE ur.rating=allr.rating RETURN  count(ur), USERS, avg(ur.rating) ORDER BY count(ur) DESC")
	List<UserSimilarity>  similarUsers(GNUser user);

	@Query("START user=node({0}),movie=node({1}) MATCH user-[r:HAS_RATED]->movie RETURN r.rating")
	Float userRate4Movie(GNUser user,GNMovie movie);

}
