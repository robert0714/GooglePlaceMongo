package develop.odata.etl.model.googleplaces;

import java.io.Serializable;
import java.util.Date;
 
import org.springframework.data.annotation.Id;

import develop.odata.etl.model.googleplaces.mappings.PlaceDetails;
 

 
public class PlaceRecord {
	@Id
	private PlaceCompositeKey id;
		
	private PlaceDetails data;
	
	private Date updateTime;

	
	 
	public PlaceRecord( String placeId,String lang, PlaceDetails data, Date updateTime) {
		super();
		this.id = new PlaceCompositeKey(placeId, lang);
		this.data = data;
		this.updateTime = updateTime;
	}
  
	public PlaceRecord( ) {
		super(); 
	}

	public PlaceCompositeKey getId() {
		return id;
	}

	public void setId(PlaceCompositeKey id) {
		this.id = id;
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
	
}
