package develop.odata.etl.model.googleplaces;

import java.io.Serializable;

public class PlaceCompositeKey  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1428278996897880794L;
	
	public PlaceCompositeKey(String placeId, String lang) {
		super();
		this.placeId = placeId;
		this.lang = lang;
	}
	private String placeId;
	private String lang;
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	
}