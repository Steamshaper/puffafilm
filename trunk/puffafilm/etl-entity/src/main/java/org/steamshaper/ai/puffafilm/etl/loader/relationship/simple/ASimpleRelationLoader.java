package org.steamshaper.ai.puffafilm.etl.loader.relationship.simple;

import org.steamshaper.ai.puffafilm.etl.loader.relationship.EntityFinder;
import org.steamshaper.puffafilm.ai.node.IGNode;

public abstract class ASimpleRelationLoader< RHS_ENTITY extends IGNode, RHS_OID_TYPE, LHS_ENTITY extends IGNode, LHS_OID_TYPE> {

	private EntityFinder<RHS_ENTITY, RHS_OID_TYPE> rhsFinder = null;


	private EntityFinder<LHS_ENTITY, LHS_OID_TYPE> lhsFinder = null;

	public ASimpleRelationLoader(
			EntityFinder<RHS_ENTITY, RHS_OID_TYPE> rhsFinder,
			EntityFinder<LHS_ENTITY, LHS_OID_TYPE> lhsFinder) {
		this.setRhsFinder(rhsFinder);
		this.setLhsFinder(lhsFinder);
	}

	/**
	 * Find relation ship member then wire it.
	 *
	 * @param rhs_oid_value the rhs_oid_value
	 * @param lhs_oid_value the lhs_oid_value
	 * @param datSource the dat source
	 * @return true, if successful
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public boolean findRelationShipMemberThenWireIt(RHS_OID_TYPE rhs_oid_value,
			LHS_OID_TYPE lhs_oid_value) throws InstantiationException, IllegalAccessException {

		RHS_ENTITY rhsNode = findRHSEntity(rhs_oid_value);
		if (rhsNode == null) {
			return false;
		}else{
			log("RHS Found ["+rhsNode.getOidAsString()+"]");
		}
		LHS_ENTITY lhsNode = findLHSEntity(lhs_oid_value);
		if (lhsNode == null) {
			return false;
		}else{
			log("LHS Found ["+lhsNode.getOidAsString()+"]");
		}



		wireRightRelationShip(rhsNode,lhsNode);
		return true;
	}


	protected abstract void log(String string);


	protected abstract void wireRightRelationShip(RHS_ENTITY rhsNode,
			LHS_ENTITY lhsNode);


	private RHS_ENTITY findRHSEntity(RHS_OID_TYPE searchValue) {
		return rhsFinder.findNodeWithIDValue(searchValue);
	}

	private LHS_ENTITY findLHSEntity(LHS_OID_TYPE searchValue) {
		return lhsFinder.findNodeWithIDValue(searchValue);
	}

	public EntityFinder<LHS_ENTITY, LHS_OID_TYPE> getLhsFinder() {
		return lhsFinder;
	}

	public void setLhsFinder(EntityFinder<LHS_ENTITY, LHS_OID_TYPE> lhsFinder) {
		this.lhsFinder = lhsFinder;
	}

	public EntityFinder<RHS_ENTITY, RHS_OID_TYPE> getRhsFinder() {
		return rhsFinder;
	}

	public void setRhsFinder(EntityFinder<RHS_ENTITY, RHS_OID_TYPE> rhsFinder) {
		this.rhsFinder = rhsFinder;
	}
}
