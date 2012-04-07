package org.steamshaper.ai.puffafilm.etl.finder;

import org.steamshaper.ai.puffafilm.etl.loader.relationship.EntityFinder;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNCountry;
import org.steamshaper.puffafilm.ai.repository.GNCountryRepository;

public class CountryFinderOnOid extends EntityFinder<GNCountry, String> {

	private final static GNCountryRepository repo = Help.me.getContext().getBean(
			GNCountryRepository.class);

	@Override
	public GNCountry doFindNodeWithIDValue(String value) {
		GNCountry o = repo.findByPropertyValue("name", value);
		return o;
	}

}
