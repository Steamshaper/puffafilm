package org.steamshaper.ai.prediction.struct;

import org.steamshaper.puffafilm.ai.node.GNUser;

public class UserSimilarityBean {

	private Integer commonsMovieCount;
	private Float avgRating;
	private Float rate;
	private Float distance;
	private GNUser user;

	public UserSimilarityBean(GNUser user){
		this.setUser(user);
		this.commonsMovieCount = 0;
		this.avgRating = 0F;
		this.rate = 0F;
		this.distance = 0F;
	}
	public Long getUserId() {
		return user.getId();
	}
	public Integer getCommonsMovieCount() {
		return commonsMovieCount;
	}
	public void setCommonsMovieCount(Integer commonsMovieCount) {
		this.commonsMovieCount = commonsMovieCount;
	}
	public Float getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(Float avgRating) {
		this.avgRating = avgRating;
	}
	public Float getRate() {
		return rate;
	}
	public void setRate(Float rate) {
		this.rate = rate;
	}
	public Float getDistance() {
		return distance;
	}
	public void setDistance(Float distance) {
		this.distance = distance;
	}
	public GNUser getUser() {
		return user;
	}
	public void setUser(GNUser user) {
		this.user = user;
	}



}
