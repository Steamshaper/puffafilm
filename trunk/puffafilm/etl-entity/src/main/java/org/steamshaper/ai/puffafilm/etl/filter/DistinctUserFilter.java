package org.steamshaper.ai.puffafilm.etl.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.steamshaper.ai.puffafilm.etl.entity.EUserRatedMovie;
import org.steamshaper.ai.puffafilm.etl.entity.EUserTaggedMovie;
import org.steamshaper.ai.puffafilm.etl.filter.entity.EUser;

public class DistinctUserFilter {
	final static Logger log = Logger.getLogger(DistinctUserFilter.class);

	public static List<EUser> distinctUsers(
			List<EUserRatedMovie> userRatedList,
			List<EUserTaggedMovie> userTaggedList) {
		HashSet<Long> set = new HashSet<Long>(userRatedList.size() / 2);
		List<EUser> o = new ArrayList<EUser>(userRatedList.size() / 2);
		for (EUserRatedMovie userRated : userRatedList) {
			if (!set.contains(userRated.getUserID())) {
				set.add(userRated.getUserID());
				EUser user = new EUser();
				user.setId(userRated.getUserID());
				o.add(user);
			}
		}
		for (EUserTaggedMovie userTagged : userTaggedList) {
			if (!set.contains(userTagged.getUserID())) {
				set.add(userTagged.getUserID());
				EUser user = new EUser();
				user.setId(userTagged.getUserID());
				o.add(user);
			}
		}
		log.info("User List filterd for distinct userID IN ["
				+ userRatedList.size() + userTaggedList.size()
				+ "] duplicate found ["
				+ (userRatedList.size() + userTaggedList.size() - o.size())
				+ "] OUT [" + o.size() + "]");
		return o;
	}

}
