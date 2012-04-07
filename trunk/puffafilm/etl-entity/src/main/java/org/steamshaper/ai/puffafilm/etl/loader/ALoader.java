package org.steamshaper.ai.puffafilm.etl.loader;

import org.springframework.data.neo4j.repository.GraphRepository;


public abstract class ALoader<D,S> {

    public ALoader(){
    }

    protected abstract D convert(S source);

    protected abstract GraphRepository<D> getRepository();


    public void load(S onLoad){

         D convertedNode =  this.convert(onLoad);
         getRepository().save(convertedNode);

    }

}
