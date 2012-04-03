package org.steamshaper.puffafilm.ai.node;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.steamshaper.puffafilm.ai.relationship.GRIsTagged;

@NodeEntity
public class GNMovie implements IGNode {
	@GraphId
	Long id;
	@Indexed
	Long oid;
	@Indexed (indexName="movie4title",indexType=org.springframework.data.neo4j.support.index.IndexType.FULLTEXT,fieldName="title")
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

	@RelatedTo(type = "SHOT_IN", direction = Direction.OUTGOING)
	Set<GNLocation> locations;
	@RelatedTo(type = "DIRECTED", direction = Direction.OUTGOING)
	Set<GNDirector> directors;
	@RelatedTo(type = "MADE_IN", direction = Direction.OUTGOING)
	Set<GNCountry> countries;
	@RelatedTo(type = "KIND_OF", direction = Direction.OUTGOING)
	Set<GNGenre> genres;
	@RelatedToVia(elementClass = GRIsTagged.class, type = "IS_TAGGED", direction = Direction.OUTGOING)
	Set<GRIsTagged> tags;

	@Fetch String description;
	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
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

	public Long getId() {
		return id;
	}

	public Set<GNLocation> getLocations() {
		return locations;
	}

	public void setLocations(Set<GNLocation> locations) {
		this.locations = locations;
	}

	public Set<GNDirector> getDirectors() {
		return directors;
	}

	public void setDirectors(Set<GNDirector> directors) {
		this.directors = directors;
	}

	public Set<GNCountry> getCountries() {
		return countries;
	}

	public void setCountries(Set<GNCountry> countries) {
		this.countries = countries;
	}

	public Set<GNGenre> getGenres() {
		return genres;
	}

	public void setGenres(Set<GNGenre> genres) {
		this.genres = genres;
	}

	public Set<GRIsTagged> getTags() {
		return tags;
	}

	public void setTags(Set<GRIsTagged> tags) {
		this.tags = tags;
	}

	public void addDirector(GNDirector director) {
		directors.add(director);
	}

	public void addLocation(GNLocation location){
		locations.add(location);
	}

	public void addCountry(GNCountry country){
		countries.add(country);
	}

	public void addGenere(GNGenre genere){
		this.genres.add(genere);
	}

	@Override
	public String getOidAsString() {
		return oid.toString();
	}
	@Override
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
