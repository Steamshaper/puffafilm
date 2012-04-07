package org.steamshaper.ai.puffafilm.etl.extracors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public abstract class AExtractor<T> {

	protected static Logger log = Logger.getLogger(AExtractor.class);
	private static final String FIELD_SEPARATOR = "\t";

	public final List<T> extract(List<String> rowList){
		List<T> entityBeanList = new ArrayList<T>();
		Iterator<String> rowIter = rowList.iterator();
		//salto l'intestazione
		rowIter.next();
		while(rowIter.hasNext()){
			T bean = createEntityBean(rowIter.next().split(FIELD_SEPARATOR));
			if(bean!=null){
				entityBeanList.add(bean);
			}
		}
		return entityBeanList;
	}

	protected abstract T createEntityBean(String... fields);


}
