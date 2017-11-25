package develop.odata.etl.model.googleplaces;

import java.util.Date;

import org.springframework.data.annotation.Id;

import develop.odata.etl.model.googleplaces.mappings.PlaceDetails;

public class PlaceRecord {
	@Id
	private String placeId;
	private PlaceDetails data;
	private String lang;
	private Date updateTime;
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public PlaceDetails getData() {
		return data;
	}
	public void setData(PlaceDetails data) {
		this.data = data;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	
}
