package org.steamshaper.ai.puffafilm.etl.entity;

/**
 * The Class ETags.
 */
public class ETag extends ADataAdapter {

	/** The id. */
	Long id;

	/** The value. */
	String value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ETag [id=" + id + ", value=" + value + "]";
	}
	
}
