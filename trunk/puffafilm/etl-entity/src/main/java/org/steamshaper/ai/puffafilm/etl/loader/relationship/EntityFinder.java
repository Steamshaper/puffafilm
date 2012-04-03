package org.steamshaper.ai.puffafilm.etl.loader.relationship;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public abstract class EntityFinder<ENTITY, OID_TYPE> {

	// Create cache
	final int MAX_ENTRIES = 40000;
	private boolean enableCache = true;
	private final Map<OID_TYPE, ENTITY> cache = new LinkedHashMap<OID_TYPE, ENTITY>(
			MAX_ENTRIES + 1, .75F, true) {

		private static final long serialVersionUID = 5704578343762231114L;

		// This method is called just after a new entry has been added
		public boolean removeEldestEntry(Map.Entry<OID_TYPE, ENTITY> eldest) {
			return size() > MAX_ENTRIES;
		}
	};

	//
	// // Add to cache
	// Object key = "key";
	// cache.put(key, object);
	//
	// // Get object
	// Object o = cache.get(key);
	// if (o == null && !cache.containsKey(key)) {
	// // Object not in cache. If null is not a possible value in the cache,
	// // the call to cache.contains(key) is not needed
	// }
	//
	// // If the cache is to be used by multiple threads,
	// // the cache must be wrapped with code to synchronize the methods
	// cache = (Map)Collections.synchronizedMap(cache);

	public abstract ENTITY doFindNodeWithIDValue(OID_TYPE value);

	Logger logCache = Logger.getLogger("cache.finders");
	int hits = 0;
	int fault = 0;
	String clazz;

	public EntityFinder() {
		clazz = this.toString();
		clazz = clazz.substring(clazz.lastIndexOf("."), clazz.indexOf("@") + 1);
	}

	public ENTITY findNodeWithIDValue(OID_TYPE value) {
		ENTITY o =null;
		if(enableCache){
		o = cache.get(value);
		if (o == null && !cache.containsKey(value)) {
			if (logCache.isDebugEnabled()) {
				fault++;
				logCache.debug(clazz + "fault " + fault);
			}

			o = doFindNodeWithIDValue(value);
			cache.put(value, o);
		} else {
			if (logCache.isDebugEnabled()) {
				hits++;
				logCache.debug(clazz + "hits " + hits);
			}
		}
		}else{
			o=doFindNodeWithIDValue(value);
		}
		return o;
	}

	public void disableCache() {
		this.enableCache = false;
	}

	public void clearCache() {
		this.cache.clear();
	}

}
