package org.steamshaper.ai.puffafilm.etl.loader.relationship;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.steamshaper.ai.puffafilm.etl.entity.EUserTaggedMovie;
import org.steamshaper.ai.puffafilm.etl.finder.TagFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.finder.UserFinderOnOid;
import org.steamshaper.puffafilm.ai.node.GNTag;
import org.steamshaper.puffafilm.ai.node.GNUser;
import org.steamshaper.puffafilm.ai.relationship.GRHaveTagged;

public class HaveTaggedLoader
		extends
		ARelationshipLoader<EUserTaggedMovie, GRHaveTagged, GNUser, Long, GNTag, Long> {

	Logger log = Logger.getLogger(HaveTaggedLoader.class);

	public static HaveTaggedLoader getInstance() {
		EntityFinder<GNUser, Long> rhsFinder = new UserFinderOnOid();
		EntityFinder<GNTag, Long> lhsFinder = new TagFinderOnOid();
		return new HaveTaggedLoader(rhsFinder, lhsFinder);

	}

	private HaveTaggedLoader(EntityFinder<GNUser, Long> rhsFinder,
			EntityFinder<GNTag, Long> lhsFinder) {
		super(rhsFinder, lhsFinder);
	}

	@Override
	protected void log(String string) {
		log.debug(string);

	}

	@Override
	protected void wireRightRelationShip(GNUser rhsNode, GRHaveTagged relation,
			GNTag lhsNode) {
		Set<GRHaveTagged> taggedMovies = rhsNode.getTaggedMovie();

		if (rhsNode.getTaggedMovie().size() == 0) {
			taggedMovies = new HashSet<GRHaveTagged>();
			rhsNode.setTaggedMovie(taggedMovies);
		}

		saveProcedure(rhsNode,taggedMovies,relation);


	}

	@Override
	public GRHaveTagged createRelationship(GNUser user, GNTag tag , EUserTaggedMovie datSource) {
		GRHaveTagged oRel = new GRHaveTagged();
		oRel.setTimestamp(datSource.getTimestamp());
		oRel.setTag(tag);
		oRel.setUser(user);
		oRel.setDescription(user.getDescription()+"->"+tag.getDescription());
		return oRel;
	}

}
