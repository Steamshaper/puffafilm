package org.steamshaper.ai.puffafilm.etl.entity;

public class EMovies extends ADataAdapter {
	Long id;
	String title;
	String imdbID;
	String spanishTitle;
	String imdbPictureURL;
	Integer year;
	String rtID;
	Float rtAllCriticsRating;
	Integer rtAllCriticsNumReviews;
	Integer rtAllCriticsNumFresh;
	Integer rtAllCriticsNumRotten;
	Integer rtAllCriticsScore;
	Float rtTopCriticsRating;
	Integer rtTopCriticsNumReviews;
	Integer rtTopCriticsNumFresh;
	Integer rtTopCriticsNumRotten;
	Integer rtTopCriticsScore;
	Float rtAudienceRating;
	Integer rtAudienceNumRatings;
	Integer rtAudienceScore;
	String rtPictureURL;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImdbID() {
		return imdbID;
	}

	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}

	public String getSpanishTitle() {
		return spanishTitle;
	}

	public void setSpanishTitle(String spanishTitle) {
		this.spanishTitle = spanishTitle;
	}

	public String getImdbPictureURL() {
		return imdbPictureURL;
	}

	public void setImdbPictureURL(String imdbPictureURL) {
		this.imdbPictureURL = imdbPictureURL;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getRtID() {
		return rtID;
	}

	public void setRtID(String rtID) {
		this.rtID = rtID;
	}

	public Float getRtAllCriticsRating() {
		return rtAllCriticsRating;
	}

	public void setRtAllCriticsRating(Float rtAllCriticsRating) {
		this.rtAllCriticsRating = rtAllCriticsRating;
	}

	public Integer getRtAllCriticsNumReviews() {
		return rtAllCriticsNumReviews;
	}

	public void setRtAllCriticsNumReviews(Integer rtAllCriticsNumReviews) {
		this.rtAllCriticsNumReviews = rtAllCriticsNumReviews;
	}

	public Integer getRtAllCriticsNumFresh() {
		return rtAllCriticsNumFresh;
	}

	public void setRtAllCriticsNumFresh(Integer rtAllCriticsNumFresh) {
		this.rtAllCriticsNumFresh = rtAllCriticsNumFresh;
	}

	public Integer getRtAllCriticsNumRotten() {
		return rtAllCriticsNumRotten;
	}

	public void setRtAllCriticsNumRotten(Integer rtAllCriticsNumRotten) {
		this.rtAllCriticsNumRotten = rtAllCriticsNumRotten;
	}

	public Integer getRtAllCriticsScore() {
		return rtAllCriticsScore;
	}

	public void setRtAllCriticsScore(Integer rtAllCriticsScore) {
		this.rtAllCriticsScore = rtAllCriticsScore;
	}

	public Float getRtTopCriticsRating() {
		return rtTopCriticsRating;
	}

	public void setRtTopCriticsRating(Float rtTopCriticsRating) {
		this.rtTopCriticsRating = rtTopCriticsRating;
	}

	public Integer getRtTopCriticsNumReviews() {
		return rtTopCriticsNumReviews;
	}

	public void setRtTopCriticsNumReviews(Integer rtTopCriticsNumReviews) {
		this.rtTopCriticsNumReviews = rtTopCriticsNumReviews;
	}

	public Integer getRtTopCriticsNumFresh() {
		return rtTopCriticsNumFresh;
	}

	public void setRtTopCriticsNumFresh(Integer rtTopCriticsNumFresh) {
		this.rtTopCriticsNumFresh = rtTopCriticsNumFresh;
	}

	public Integer getRtTopCriticsNumRotten() {
		return rtTopCriticsNumRotten;
	}

	public void setRtTopCriticsNumRotten(Integer rtTopCriticsNumRotten) {
		this.rtTopCriticsNumRotten = rtTopCriticsNumRotten;
	}

	public Integer getRtTopCriticsScore() {
		return rtTopCriticsScore;
	}

	public void setRtTopCriticsScore(Integer rtTopCriticsScore) {
		this.rtTopCriticsScore = rtTopCriticsScore;
	}

	public Float getRtAudienceRating() {
		return rtAudienceRating;
	}

	public void setRtAudienceRating(Float rtAudienceRating) {
		this.rtAudienceRating = rtAudienceRating;
	}

	public Integer getRtAudienceNumRatings() {
		return rtAudienceNumRatings;
	}

	public void setRtAudienceNumRatings(Integer rtAudienceNumRatings) {
		this.rtAudienceNumRatings = rtAudienceNumRatings;
	}

	public Integer getRtAudienceScore() {
		return rtAudienceScore;
	}

	public void setRtAudienceScore(Integer rtAudienceScore) {
		this.rtAudienceScore = rtAudienceScore;
	}

	public String getRtPictureURL() {
		return rtPictureURL;
	}

	public void setRtPictureURL(String rtPictureURL) {
		this.rtPictureURL = rtPictureURL;
	}

	@Override
	public String toString() {
		return "EMovies [id=" + id + ", title=" + title + ", imdbID=" + imdbID
				+ ", spanishTitle=" + spanishTitle + ", imdbPictureURL="
				+ imdbPictureURL + ", year=" + year + ", rtID=" + rtID
				+ ", rtAllCriticsRating=" + rtAllCriticsRating
				+ ", rtAllCriticsNumReviews=" + rtAllCriticsNumReviews
				+ ", rtAllCriticsNumFresh=" + rtAllCriticsNumFresh
				+ ", rtAllCriticsNumRotten=" + rtAllCriticsNumRotten
				+ ", rtAllCriticsScore=" + rtAllCriticsScore
				+ ", rtTopCriticsRating=" + rtTopCriticsRating
				+ ", rtTopCriticsNumReviews=" + rtTopCriticsNumReviews
				+ ", rtTopCriticsNumFresh=" + rtTopCriticsNumFresh
				+ ", rtTopCriticsNumRotten=" + rtTopCriticsNumRotten
				+ ", rtTopCriticsScore=" + rtTopCriticsScore
				+ ", rtAudienceRating=" + rtAudienceRating
				+ ", rtAudienceNumRatings=" + rtAudienceNumRatings
				+ ", rtAudienceScore=" + rtAudienceScore + ", rtPictureURL="
				+ rtPictureURL + "]";
	}

}
