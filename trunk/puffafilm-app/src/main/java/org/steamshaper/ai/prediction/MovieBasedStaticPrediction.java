package org.steamshaper.ai.prediction;

import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.node.GNUser;

public abstract class MovieBasedStaticPrediction extends APrediction{

	protected static final Float DEFAULT_GENRE_SIM_WEIGHT = 0.5F;
	protected static final Float DEFAULT_ACTOR_SIM_WEIGHT = 0.1F;
	protected static final Float DEFAULT_DIRECTOR_SIM_WEIGHT = 0.4F;
	
	protected Float genreSimWeight;
	protected Float actorSimWeight;
	protected Float directorSimWeight;
	
	
	
	@Override
	protected final Float estimateRating(GNMovie movie, GNUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	
	protected Float getGenreSimWeight(){
		return DEFAULT_GENRE_SIM_WEIGHT;
	}
	

	protected Float getDirectorSimWeight(){
		return DEFAULT_DIRECTOR_SIM_WEIGHT;
	}
	
	protected Float getActorSimWeight(){
		return DEFAULT_ACTOR_SIM_WEIGHT;
	}
	
	protected final boolean checkTotalWeigths(){
		if(getActorSimWeight()+getDirectorSimWeight()+getGenreSimWeight()==1F){
			return true;
		}else{
			return false;
		}
	}
}
