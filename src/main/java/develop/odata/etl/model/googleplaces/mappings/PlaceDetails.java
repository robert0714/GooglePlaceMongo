package develop.odata.etl.model.googleplaces.mappings;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceDetails {
	@JsonProperty("place_id")
	private String placeId;
	
	@JsonProperty("name")
	private String name;

	@JsonProperty("icon")
	private String icon;

	@JsonProperty("url")
	private String url;

	@JsonProperty("address_components")
	private List<PlaceAddressComponent> addressComponents = Collections.emptyList();
	
	@JsonProperty("formatted_address")
	private String address;

	@JsonProperty("geometry")
	private PlaceGeometry geometry;

	@JsonProperty("photos")
	private List<PlacePhoto> photos = Collections.emptyList();

		

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public PlaceGeometry getGeometry() {
		return geometry;
	}

	public void setGeometry(PlaceGeometry geometry) {
		this.geometry = geometry;
	}

	public List<PlacePhoto> getPhotos() {
		return photos;
	}

	public void setPhotos(List<PlacePhoto> photos) {
		this.photos = photos;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public List<PlaceAddressComponent> getAddressComponents() {
		return addressComponents;
	}

	public void setAddressComponents(List<PlaceAddressComponent> addressComponents) {
		this.addressComponents = addressComponents;
	}
	

}