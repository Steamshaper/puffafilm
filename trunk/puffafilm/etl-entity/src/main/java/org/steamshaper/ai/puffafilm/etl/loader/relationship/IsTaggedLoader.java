package org.steamshaper.ai.puffafilm.etl.loader.relationship;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.steamshaper.ai.puffafilm.etl.entity.EMovieTag;
import org.steamshaper.ai.puffafilm.etl.finder.MovieFinderOnOid;
import org.steamshaper.ai.puffafilm.etl.finder.TagFinderOnOid;
import org.steamshaper.puffafilm.ai.node.GNMovie;
import org.steamshaper.puffafilm.ai.node.GNTag;
import org.steamshaper.puffafilm.ai.relationship.GRIsTagged;

public class IsTaggedLoader extends
		ARelationshipLoader<EMovieTag, GRIsTagged, GNMovie, Long, GNTag, Long> {

	Logger log = Logger.getLogger(IsTaggedLoader.class);

	public static IsTaggedLoader getInstance() {
		EntityFinder<GNMovie, Long> rhsFinder = new MovieFinderOnOid();
		EntityFinder<GNTag, Long> lhsFinder = new TagFinderOnOid();
		return new IsTaggedLoader(rhsFinder, lhsFinder);

	}

	private IsTaggedLoader(EntityFinder<GNMovie, Long> rhsFinder,
			EntityFinder<GNTag, Long> lhsFinder) {
		super(rhsFinder, lhsFinder);
	}

	@Override
	protected void log(String string) {
		log.debug(string);

	}

	@Override
	public GRIsTagged createRelationship(GNMovie rhsNode, GNTag lhsNode,
			EMovieTag datSource) {
		GRIsTagged relationship = new GRIsTagged();
		relationship.setMovie(rhsNode);
		relationship.setTag(lhsNode);
		relationship.setTagWeight(datSource.getTagWeight());
		relationship.setDescription(rhsNode.getDescription()+"->"+lhsNode.getDescription());
		return relationship;
	}

	@Override
	protected void wireRightRelationShip(GNMovie rhsNode, GRIsTagged relation,
			GNTag lhsNode) {

		Set<GRIsTagged> isTagged = rhsNode.getTags();
		if(rhsNode.getTags().size() == 0){
			isTagged = new HashSet<GRIsTagged>();
			rhsNode.setTags(isTagged);
		}

		saveProcedure(rhsNode,isTagged,relation);



	}

}
