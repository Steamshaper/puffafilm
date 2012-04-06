package org.steamshaper.ai.runtime.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Transaction;
import org.steamshaper.ai.puffafilm.etl.KnowledgeExtractor;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieActor;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieCountry;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieDirector;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieGenre;
import org.steamshaper.ai.puffafilm.etl.entity.EMovies;
import org.steamshaper.ai.puffafilm.etl.entity.ETag;
import org.steamshaper.ai.puffafilm.etl.entity.EUserTaggedMovie;
import org.steamshaper.ai.puffafilm.etl.filter.DistinctActorFilter;
import org.steamshaper.ai.puffafilm.etl.filter.DistinctCountryFilter;
import org.steamshaper.ai.puffafilm.etl.filter.DistinctDirectorsFilter;
import org.steamshaper.ai.puffafilm.etl.filter.DistinctGenresFilter;
import org.steamshaper.ai.puffafilm.etl.filter.DistinctUserFilter;
import org.steamshaper.ai.puffafilm.etl.filter.entity.EUser;
import org.steamshaper.ai.puffafilm.etl.loader.ActorsLoader;
import org.steamshaper.ai.puffafilm.etl.loader.CountriesLoader;
import org.steamshaper.ai.puffafilm.etl.loader.DirectorsLoader;
import org.steamshaper.ai.puffafilm.etl.loader.GenresLoader;
import org.steamshaper.ai.puffafilm.etl.loader.MovieLoader;
import org.steamshaper.ai.puffafilm.etl.loader.TagsLoader;
import org.steamshaper.ai.puffafilm.etl.loader.UTMLoader;
import org.steamshaper.ai.puffafilm.etl.loader.UserLoader;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.ai.runtime.AService;

public class GraphEntityCreator extends AService {

	private String datasetPath;
	private final Logger log = Logger
			.getLogger("service.impl.GraphEntityCreator");

	private Condition shouldRun;

	public GraphEntityCreator() {
	}

	public void run() {
		if (this.shouldRun()) {
			log.info("Creating knowledge extractor ...");
			KnowledgeExtractor ke = KnowledgeExtractor.getInstance();
			log.info("Collecting data from dataset ...");
			ke.extractAll();
			log.info("Collect DONE!");
			long start = System.currentTimeMillis();
			long lastStepEnd = start;
//			loadTag(ke); // TAG entity LOAD
//			lastStepEnd = System.currentTimeMillis();
//			log.info("Tags loaded in [" + (lastStepEnd - start));

			loadActor(ke); // Actor entity LOAD
			log.info("Actors loaded in ["
					+ (System.currentTimeMillis() - lastStepEnd));
			lastStepEnd = System.currentTimeMillis();

//			loadCountries(ke);
//			log.info("Countries loaded in ["
//					+ (System.currentTimeMillis() - lastStepEnd));
//			lastStepEnd = System.currentTimeMillis();

			loadDirectors(ke);
			log.info("Directors loaded in ["
					+ (System.currentTimeMillis() - lastStepEnd));
			lastStepEnd = System.currentTimeMillis();

			loadGenres(ke);
			log.info("Genres loaded in ["
					+ (System.currentTimeMillis() - lastStepEnd));
			lastStepEnd = System.currentTimeMillis();

			loadMovies(ke);
			log.info("Movies loaded in ["
					+ (System.currentTimeMillis() - lastStepEnd));
			lastStepEnd = System.currentTimeMillis();

			loadUsers(ke);
			log.info("User loaded in ["
					+ (System.currentTimeMillis() - lastStepEnd));
			lastStepEnd = System.currentTimeMillis();

//			loadUTM(ke);
//			log.info("UserTagMovie loaded in ["
//					+ (System.currentTimeMillis() - lastStepEnd));
//			lastStepEnd = System.currentTimeMillis();

			log.info("DB LOAD in [" + (System.currentTimeMillis() - start));
		} else {
			log.debug("Db Load SKIPPED!");
		}
	}

	private void loadUsers(KnowledgeExtractor ke) {
		Transaction tx = Help.me.toStartTransaction();
		UserLoader loader = new UserLoader();
		try {
			List<EUser> eList = DistinctUserFilter.distinctUsers(
					ke.geteUserRatedMovieList(), ke.geteUserTaggedMovieList());
			log.info("Inserting [" + eList.size()
					+ "] entities  User into Graph ...");
			for (EUser user : eList) {
				loader.load(user);
			}
			log.info("Inserting entity User into Graph DONE");
			tx.success();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Insertion of entity User FAILED");
		} finally {
			tx.finish();
		}

	}

	private void loadTag(KnowledgeExtractor ke) {
		// TAG entity LOAD
		Transaction tx = Help.me.toStartTransaction();
		TagsLoader tloader = new TagsLoader();
		try {
			List<ETag> eList = ke.geteTagList();
			log.info("Inserting [" + eList.size()
					+ "] entities  Tags into Graph ...");
			for (ETag tag : eList) {
				tloader.load(tag);
			}
			log.info("Inserting entity Tags into Graph DONE");
			tx.success();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Insertion of entity Tags FAILED");
		} finally {
			tx.finish();
		}
	}

	private void loadActor(KnowledgeExtractor ke) {
		// TAG entity LOAD
		Transaction tx = Help.me.toStartTransaction();
		ActorsLoader loader = new ActorsLoader();
		try {
			List<EMovieActor> eList = DistinctActorFilter.distinctActor(ke
					.geteMovieActorList());
			log.info("Inserting entities [" + eList.size()
					+ "] Actors into Graph ...");
			for (EMovieActor tag : eList) {
				loader.load(tag);
			}
			log.info("Inserting entity Actors into Graph DONE");
			tx.success();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Insertion of entity Actors FAILED");
		} finally {
			tx.finish();
		}
	}
	private void loadUTM(KnowledgeExtractor ke) {
		// TAG entity LOAD
				Transaction tx = Help.me.toStartTransaction();
				UTMLoader loader = new UTMLoader();
				try {
					List<EUserTaggedMovie> eList = ke.geteUserTaggedMovieList();
					log.info("Inserting entities [" + eList.size()
							+ "] Actors into Graph ...");
					for (EUserTaggedMovie utm : eList) {
						loader.load(utm);
					}
					log.info("Inserting entity USER TAG MOVIE NODE into Graph DONE");
					tx.success();
				} catch (Exception e) {
					e.printStackTrace();
					log.error("Insertion of entity Actors FAILED");
				} finally {
					tx.finish();
				}
	}

	public String getDatasetPath() {
		return datasetPath;
	}

	public void setDatasetPath(String datasetPath) {
		this.datasetPath = datasetPath;
	}

	private void loadCountries(KnowledgeExtractor ke) {
		// TAG entity LOAD
		Transaction tx = Help.me.toStartTransaction();
		CountriesLoader loader = new CountriesLoader();
		try {
			List<EMovieCountry> eList = DistinctCountryFilter
					.distinctCountries(ke.geteMovieCounryList());
			log.info("Inserting entities [" + eList.size()
					+ "] Country into Graph ...");
			for (EMovieCountry country : eList) {
				loader.load(country);
			}
			log.info("Inserting entity Country into Graph DONE");
			tx.success();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Insertion of entity Country FAILED");
		} finally {
			tx.finish();
		}
	}

	private void loadDirectors(KnowledgeExtractor ke) {
		Transaction tx = Help.me.toStartTransaction();
		DirectorsLoader loader = new DirectorsLoader();
		try {
			List<EMovieDirector> eList = DistinctDirectorsFilter
					.distinctDirectors(ke.geteMovieDirectorList());
			log.info("Inserting entities [" + eList.size()
					+ "] Director into Graph ...");
			for (EMovieDirector director : eList) {
				loader.load(director);
			}
			log.info("Inserting entity Director into Graph DONE");
			tx.success();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Insertion of entity Director FAILED");
		} finally {
			tx.finish();
		}
	}

	private void loadGenres(KnowledgeExtractor ke) {
		Transaction tx = Help.me.toStartTransaction();
		GenresLoader loader = new GenresLoader();
		try {
			List<EMovieGenre> eList = DistinctGenresFilter.distinctGenres(ke
					.geteMovieGenreList());
			log.info("Inserting entities [" + eList.size()
					+ "] Genre into Graph ...");
			for (EMovieGenre genre : eList) {
				loader.load(genre);
			}
			log.info("Inserting entity Genre into Graph DONE");
			tx.success();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Insertion of entity Genre FAILED");
		} finally {
			tx.finish();
		}
	}

	private void loadMovies(KnowledgeExtractor ke) {
		Transaction tx = Help.me.toStartTransaction();
		MovieLoader loader = new MovieLoader();
		try {
			List<EMovies> eList = ke.geteMoviesList();
			log.info("Inserting entities [" + eList.size()
					+ "] Movie into Graph ...");
			for (EMovies movie : eList) {
				loader.load(movie);
			}
			log.info("Inserting entity Movie into Graph DONE");
			tx.success();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Insertion of Movie Genre FAILED");
		} finally {
			tx.finish();
		}
	}

	public boolean shouldRun() {
		if (shouldRun != null) {
			return shouldRun.isConditionTrue();
		}
		return true;
	}

	public void setShouldRun(Condition shouldRun) {
		this.shouldRun = shouldRun;
	}

}
