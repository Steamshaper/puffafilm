package org.steamshaper.ai.puffafilm.etl.extracors;

import org.steamshaper.ai.puffafilm.etl.entity.ETag;

public class TagExtractor extends AExtractor<ETag> {

	private static final int ID = 0;
	private static final int VALUE = 1;

	@Override
	protected ETag createEntityBean(String... fields) {
		ETag tag = new ETag();
		boolean integrity = true;
		for (int i = 0; i < fields.length; i++) {
			if (!integrity) {
				tag = new ETag();
				break;
			}
			String field = fields[i];
			if (field != null) {
				switch (i) {
				case ID:
					tag.setId(Long.valueOf(field));
					break;
				case VALUE:
					tag.setValue(field);
					break;
				default:
					log.error("Failing parsing String: " + field);
					integrity = false;
					break;
				}
			}
		}
		if (!checkIntegrity(tag)) {
			tag = null;
		}
		return tag;
	}

	private boolean checkIntegrity(ETag tag) {
		if (tag.getId() == null) {
			return false;
		} else {
			return true;
		}
	}

}
