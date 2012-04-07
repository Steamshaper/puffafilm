package org.steamshaper.ai.puffafilm.etl.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.steamshaper.ai.puffafilm.etl.entity.ETag;

public class DistinctTagsFilter {
	final static Logger log = Logger.getLogger(DistinctTagsFilter.class);

	public static List<ETag> distinctTags(List<ETag> tagsList) {
		HashSet<Long> set = new HashSet<Long>(tagsList.size()/2);
		List<ETag> o =  new ArrayList<ETag>(tagsList.size()/2);
		for(ETag tag :  tagsList){
			if(!set.contains(tag.getId())){
				set.add(tag.getId());
				o.add(tag);
			}
		}
		log.info("Tags List filterd for distinct tag IN ["+tagsList.size()+"] duplicate found ["+(tagsList.size()-o.size())+"] OUT ["+o.size()+"]");
		return o;
	}

}
