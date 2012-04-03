package org.steamshaper.ai.puffafilm.etl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieActor;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieCountry;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieDirector;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieGenre;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieLocation;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieTag;
import org.steamshaper.ai.puffafilm.etl.entity.EMovies;
import org.steamshaper.ai.puffafilm.etl.entity.ETag;
import org.steamshaper.ai.puffafilm.etl.entity.EUserRatedMovie;
import org.steamshaper.ai.puffafilm.etl.entity.EUserTaggedMovie;
import org.steamshaper.ai.puffafilm.etl.extracors.AExtractor;
import org.steamshaper.ai.puffafilm.etl.extracors.MovieActorExtractor;
import org.steamshaper.ai.puffafilm.etl.extracors.MovieCountryExtractor;
import org.steamshaper.ai.puffafilm.etl.extracors.MovieDirectorExtractor;
import org.steamshaper.ai.puffafilm.etl.extracors.MovieExtractor;
import org.steamshaper.ai.puffafilm.etl.extracors.MovieGenreExtractor;
import org.steamshaper.ai.puffafilm.etl.extracors.MovieLocationExtractor;
import org.steamshaper.ai.puffafilm.etl.extracors.MovieTagExtractor;
import org.steamshaper.ai.puffafilm.etl.extracors.TagExtractor;
import org.steamshaper.ai.puffafilm.etl.extracors.UserRatedMovieExtractor;
import org.steamshaper.ai.puffafilm.etl.extracors.UserTaggedMovieExtractor;
import org.steamshaper.ai.puffafilm.util.FileManager;

/**
 * The Class KnowledgeExtractor.
 */
public class KnowledgeExtractor {

	private String datFileHomePath;
	public String getDatFileHomePath() {
		return datFileHomePath;
	}

	public void setDatFileHomePath(String datFileHomePath) {
		this.datFileHomePath = datFileHomePath;
	}

	private static final String DAT_EXT = "dat";
	private boolean shouldBeExtracted = true;
	
	private static final String DEFAULT_HOME_PATH = System.getProperty("user.dir")+"/dataset/";
	
	private static KnowledgeExtractor knowledgeExtractor;

	/** The log. */
	private Logger log = Logger.getLogger(KnowledgeExtractor.class);

	private static final Map<String, Integer> ENTITY_MAP = new HashMap<String, Integer>(
			10);
	private static final int MOVIE_ACTOR = 0;
	private static final int MOVIE_COUNTRIES = 1;
	private static final int MOVIE_DIRECTORS = 2;
	private static final int MOVIE_GENRES = 3;
	private static final int MOVIE_LOCATIONS = 4;
	private static final int MOVIE_TAGS = 5;
	private static final int MOVIES = 6;
	private static final int TAGS = 7;
	private static final int USER_RATEDMOVIES = 8;
	private static final int USER_TAGGEDMOVIES = 9;

	private static final String MOVIE_ACTOR_FILE= "movie_actors";
	private static final String MOVIE_COUNTRIES_FILE =  "movie_countries";
	private static final String MOVIE_DIRECTORS_FILE = "movie_directors";
	private static final String MOVIE_GENRES_FILE = "movie_genres";
	private static final String MOVIE_LOCATIONS_FILE = "movie_locations";
	private static final String MOVIE_TAGS_FILE = "movie_tags";
	private static final String MOVIES_FILE = "movies";
	private static final String TAGS_FILE = "tags";
	private static final String USER_RATEDMOVIES_FILE = "user_ratedmovies";
	private static final String USER_TAGGEDMOVIES_FILE = "user_taggedmovies";

	private List<EMovieActor> eMovieActorList;
	private List<EMovieCountry> eMovieCounryList;
	private List<EMovieDirector> eMovieDirectorList;
	private List<EMovieGenre> eMovieGenreList;
	private List<EMovieLocation> eMovieLocationList;
	private List<EMovies> eMoviesList;
	private List<EMovieTag> eMovieTagList;
	private List<ETag> eTagList;
	private List<EUserRatedMovie> eUserRatedMovieList;
	private List<EUserTaggedMovie> eUserTaggedMovieList;


	public static KnowledgeExtractor getInstance(){
		if(knowledgeExtractor==null){
			knowledgeExtractor = new KnowledgeExtractor();
		}
		return knowledgeExtractor;
	}
	
	private KnowledgeExtractor() {
		this.datFileHomePath = DEFAULT_HOME_PATH;
		initMap();
	}

	private void initMap() {
		ENTITY_MAP.put(MOVIE_ACTOR_FILE, MOVIE_ACTOR);
		ENTITY_MAP.put(MOVIE_COUNTRIES_FILE, MOVIE_COUNTRIES);
		ENTITY_MAP.put(MOVIE_DIRECTORS_FILE, MOVIE_DIRECTORS);
		ENTITY_MAP.put(MOVIE_GENRES_FILE, MOVIE_GENRES);
		ENTITY_MAP.put(MOVIE_LOCATIONS_FILE, MOVIE_LOCATIONS);
		ENTITY_MAP.put(MOVIE_TAGS_FILE, MOVIE_TAGS);
		ENTITY_MAP.put(MOVIES_FILE, MOVIES);
		ENTITY_MAP.put(TAGS_FILE, TAGS);
		ENTITY_MAP.put(USER_RATEDMOVIES_FILE, USER_RATEDMOVIES);
		ENTITY_MAP.put(USER_TAGGEDMOVIES_FILE, USER_TAGGEDMOVIES);
	}


	public void extractAll() {
		if(shouldBeExtracted){
		try {
			log.info("Start ETL Extraction");
			File[] dataFiles = FileManager.getFilesFromDirectory(datFileHomePath,
					DAT_EXT);
			for (File file : dataFiles) {
				int entityPattern = ENTITY_MAP.get(file.getName().substring(0,
						file.getName().indexOf(".")));
				switch (entityPattern) {
				case MOVIE_ACTOR:
					extractMovieActorFrom(file);
					break;
				case MOVIE_COUNTRIES:
					extractMovieCountriesForm(file);
					break;
				case MOVIE_DIRECTORS:
					extractMovieDirectorFrom(file);
					break;
				case MOVIE_GENRES:
					extractMovieGenreFrom(file);
					break;
				case MOVIE_LOCATIONS:
					extractMovieLocationFrom(file);
					break;
				case MOVIES:
					extractMovieFrom(file);
					break;
				case MOVIE_TAGS:
					extractMovieTagsFrom(file);
					break;
				case TAGS:
					extractTagsFrom(file);
					break;
				case USER_RATEDMOVIES:
					extractUserRatedMoviesFrom(file);
					break;
				case USER_TAGGEDMOVIES:
					extractUserTaggedMoviesFrom(file);
					break;

				default:
					log.error("File " + file + "dropped");
					break;
				}
				log.info("End ETL Extraction");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		shouldBeExtracted = false;
		}else{
			log.info("Extraction skipped, all dat is already processed!");
		}
	}


	@SuppressWarnings("unchecked")
	public void extractMovieActorFrom(File file) {
		try{
			List<String> rowList = FileManager.readFile(file);
			log.info("Start Extraction MOVIE_ACTOR");
			AExtractor<?> extractor = new MovieActorExtractor();
			this.eMovieActorList = (List<EMovieActor>) extractor.extract(rowList);
			log.info("End Extraction MOVIE_ACTOR");
		}catch (Exception e) {
			log.error("Error During Ecxtraction of MOVIE_ACTOR from file " + file.getAbsolutePath());
		}

	}

	public void extractMovieActor() {
		try{
			log.info("Seek the directory for " + MOVIE_ACTOR_FILE + " file");
			extractMovieActorFrom(new File(generateFilePath(MOVIE_ACTOR_FILE)));
		}catch (Exception e) {
			log.error("Dataset " + MOVIE_ACTOR_FILE +" not Found");
		}
	}

	@SuppressWarnings("unchecked")
	public void extractUserTaggedMoviesFrom(File file) {
		try{
			List<String> rowList = FileManager.readFile(file);
		log.info("Start Extraction USER_TAGGEDMOVIES");
		AExtractor<?> extractor = new UserTaggedMovieExtractor();
		this.eUserTaggedMovieList = (List<EUserTaggedMovie>) extractor.extract(rowList);
		log.info("End Extraction USER_TAGGEDMOVIES");
		}catch (Exception e) {
			log.error("Error During Ecxtraction of USER_TAGGEDMOVIES from file " + file.getAbsolutePath());
		}

	}
	public void extractUserTaggedMovies() {
		try{
			log.info("Seek the directory for " + USER_TAGGEDMOVIES_FILE + " file");
			extractUserTaggedMoviesFrom(new File(generateFilePath(USER_TAGGEDMOVIES_FILE)));
		}catch (Exception e) {
			log.error("Dataset " + USER_TAGGEDMOVIES_FILE +" not Found");
		}
	}

	@SuppressWarnings("unchecked")
	public void extractUserRatedMoviesFrom(File file) {
		try{
			List<String> rowList = FileManager.readFile(file);
		log.info("Start Extraction USER_RATEDMOVIES");
		AExtractor<?> extractor = new UserRatedMovieExtractor();
		this.eUserRatedMovieList = (List<EUserRatedMovie>) extractor.extract(rowList);
		log.info("End Extraction USER_RATEDMOVIES");
		}catch (Exception e) {
			log.error("Error During Ecxtraction of USER_RATEDMOVIES from file " + file.getAbsolutePath());
		}

	}
	public void extractUserRatedMovies() {
		try{
			log.info("Seek the directory for " + USER_RATEDMOVIES_FILE + " file");
			extractUserRatedMoviesFrom(new File(generateFilePath(USER_RATEDMOVIES_FILE)));
		}catch (Exception e) {
			log.error("Dataset " + USER_RATEDMOVIES_FILE +" not Found");
		}
	}

	@SuppressWarnings("unchecked")
	public void extractTagsFrom(File file) {
		try{
			List<String> rowList = FileManager.readFile(file);
		log.info("Start Extraction TAGS");
		AExtractor<?> extractor = new TagExtractor();
		this.eTagList = (List<ETag>) extractor.extract(rowList);
		log.info("End Extraction TAGS");
		}catch (Exception e) {
			log.error("Error During Ecxtraction of TAGS from file " + file.getAbsolutePath());
		}

	}
	public void extractTags() {
		try{
			log.info("Seek the directory for " + TAGS_FILE + " file");
			extractTagsFrom(new File(generateFilePath(TAGS_FILE)));
		}catch (Exception e) {
			log.error("Dataset " + TAGS_FILE +" not Found");
		}
	}

	@SuppressWarnings("unchecked")
	public void extractMovieTagsFrom(File file) {
		try{
			List<String> rowList = FileManager.readFile(file);
		log.info("Start Extraction MOVIE_TAGS");
		AExtractor<?> extractor = new MovieTagExtractor();
		this.eMovieTagList = (List<EMovieTag>) extractor.extract(rowList);
		log.info("End Extraction MOVIE_TAGS");
		}catch (Exception e) {
			log.error("Error During Ecxtraction of MOVIE_TAGS from file " + file.getAbsolutePath());
		}

	}
	public void extractMovieTags() {
		try{
			log.info("Seek the directory for " + MOVIE_TAGS_FILE + " file");
			extractMovieTagsFrom(new File(generateFilePath(MOVIE_TAGS_FILE)));
		}catch (Exception e) {
			log.error("Dataset " + MOVIE_TAGS_FILE +" not Found");
		}
	}
	@SuppressWarnings("unchecked")
	public void extractMovieFrom(File file) {
		try{
			List<String> rowList = FileManager.readFile(file);
		log.info("Start Extraction EMOVIES");
		AExtractor<?> extractor = new MovieExtractor();
		this.eMoviesList = (List<EMovies>) extractor.extract(rowList);
		log.info("End Extraction EMOVIES");
		}catch (Exception e) {
			log.error("Error During Ecxtraction of EMOVIES from file " + file.getAbsolutePath());
		}

	}
	public void extractMovie() {
		try{
			log.info("Seek the directory for " + MOVIES_FILE + " file");
			extractMovieFrom(new File(generateFilePath(MOVIES_FILE)));
		}catch (Exception e) {
			log.error("Dataset " + MOVIES_FILE +" not Found");
		}
	}
	@SuppressWarnings("unchecked")
	public void extractMovieLocationFrom(File file) {
		try{
			List<String> rowList = FileManager.readFile(file);
		log.info("Start Extraction MOVIE_LOCATIONS");
		AExtractor<?> extractor = new MovieLocationExtractor();
		this.eMovieLocationList = (List<EMovieLocation>) extractor.extract(rowList);
		log.info("End Extraction MOVIE_LOCATIONS");
		}catch (Exception e) {
			log.error("Error During Ecxtraction of MOVIE_LOCATIONS from file " + file.getAbsolutePath());
		}

	}
	public void extractMovieLocation() {
		try{
			log.info("Seek the directory for " + MOVIE_LOCATIONS_FILE + " file");
			extractMovieLocationFrom(new File(generateFilePath(MOVIE_LOCATIONS_FILE)));
		}catch (Exception e) {
			log.error("Dataset " + MOVIE_LOCATIONS_FILE +" not Found");
		}
	}
	@SuppressWarnings("unchecked")
	public void extractMovieGenreFrom(File file) {
		try{
			List<String> rowList = FileManager.readFile(file);
		log.info("Start Extraction MOVIE_GENRES");
		AExtractor<?> extractor = new MovieGenreExtractor();
		this.eMovieGenreList = (List<EMovieGenre>) extractor.extract(rowList);
		log.info("End Extraction MOVIE_GENRES");
		}catch (Exception e) {
			log.error("Error During Ecxtraction of MOVIE_GENRES from file " + file.getAbsolutePath());
		}

	}
	public void extractMovieGenre() {
		try{
			log.info("Seek the directory for " + MOVIE_GENRES_FILE + " file");
			extractMovieGenreFrom(new File(generateFilePath(MOVIE_GENRES_FILE)));
		}catch (Exception e) {
			log.error("Dataset " + MOVIE_GENRES_FILE +" not Found");
		}
	}
	@SuppressWarnings("unchecked")
	public void extractMovieDirectorFrom(File file) {
		try{
			List<String> rowList = FileManager.readFile(file);
		log.info("Start Extraction MOVIE_DIRECTORS");
		AExtractor<?> extractor = new MovieDirectorExtractor();
		this.eMovieDirectorList = (List<EMovieDirector>) extractor.extract(rowList);
		log.info("End Extraction MOVIE_DIRECTORS");
		}catch (Exception e) {
			log.error("Error During Ecxtraction of MOVIE_DIRECTORS from file " + file.getAbsolutePath());
		}

	}
	public void extractMovieDirector() {
		try{
			log.info("Seek the directory for " + MOVIE_DIRECTORS_FILE + " file");
			extractMovieDirectorFrom(new File(generateFilePath(MOVIE_DIRECTORS_FILE)));
		}catch (Exception e) {
			log.error("Dataset " + MOVIE_DIRECTORS_FILE +" not Found");
		}
	}
	@SuppressWarnings("unchecked")
	public void extractMovieCountriesForm(File file) {
		try{
			List<String> rowList = FileManager.readFile(file);
		log.info("Start Extraction MOVIE_COUNTRIES");
		AExtractor<?> extractor = new MovieCountryExtractor();
		this.eMovieCounryList = (List<EMovieCountry>) extractor.extract(rowList);
		log.info("End Extraction MOVIE_COUNTRIES");
		}catch (Exception e) {
			log.error("Error During Ecxtraction of MOVIE_COUNTRIES from file " + file.getAbsolutePath());
		}

	}
	public void extractMovieCountries() {
		try{
			log.info("Seek the directory for " + MOVIE_COUNTRIES_FILE + " file");
			extractMovieCountriesForm(new File(generateFilePath(MOVIE_COUNTRIES_FILE)));
		}catch (Exception e) {
			log.error("Dataset " + MOVIE_COUNTRIES_FILE +" not Found");
		}
	}

	private String generateFilePath(String fileName){
		return datFileHomePath + "/" + fileName + "." + DAT_EXT;
	}

	public List<EMovieActor> geteMovieActorList() {
		return eMovieActorList;
	}

	public List<EMovieCountry> geteMovieCounryList() {
		return eMovieCounryList;
	}

	public List<EMovieDirector> geteMovieDirectorList() {
		return eMovieDirectorList;
	}

	public List<EMovieGenre> geteMovieGenreList() {
		return eMovieGenreList;
	}

	public List<EMovieLocation> geteMovieLocationList() {
		return eMovieLocationList;
	}

	public List<EMovies> geteMoviesList() {
		return eMoviesList;
	}

	public List<EMovieTag> geteMovieTagList() {
		return eMovieTagList;
	}

	public List<ETag> geteTagList() {
		return eTagList;
	}

	public List<EUserRatedMovie> geteUserRatedMovieList() {
		return eUserRatedMovieList;
	}

	public List<EUserTaggedMovie> geteUserTaggedMovieList() {
		return eUserTaggedMovieList;
	}

	public void flusheMovieActorList() {
		this.eMovieActorList.clear();
		this.eMovieActorList = null;
	}

	public void flusheMovieCounryList() {
		this.eMovieCounryList.clear();
		this.eMovieCounryList = null;
	}

	public void flusheMovieDirectorList() {
		this.eMovieDirectorList.clear();
		this.eMovieDirectorList = null;
	}

	public void flusheMovieGenreList() {
		this.eMovieGenreList.clear();
		this.eMovieGenreList = null;
	}

	public void flusheMovieLocationList() {
		this.eMovieLocationList.clear();
		this.eMovieLocationList = null;
	}

	public void flusheMoviesList() {
		this.eMoviesList.clear();
		this.eMoviesList = null;
	}

	public void flusheMovieTagList() {
		this.eMovieTagList.clear();
		this.eMovieTagList = null;
	}

	public void flusheTagList() {
		this.eTagList.clear();
		this.eTagList = null;
	}

	public void flusheUserRatedMovieList() {
		this.eUserRatedMovieList.clear();
		this.eUserRatedMovieList = null;
	}

	public void flusheUserTaggedMovieList() {
		this.eUserTaggedMovieList.clear();
		this.eUserTaggedMovieList = null;
	}

	public void flushAllList(){
		flusheMovieActorList();
		flusheMovieCounryList();
		flusheMovieDirectorList();
		flusheMovieGenreList();
		flusheMovieLocationList();
		flusheMoviesList();
		flusheMovieTagList();
		flusheTagList();
		flusheUserRatedMovieList();
		flusheUserTaggedMovieList();
	}
}
