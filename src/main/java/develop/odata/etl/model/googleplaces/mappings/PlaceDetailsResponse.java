package develop.odata.etl.model.googleplaces.mappings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceDetailsResponse {
	@JsonProperty("result")
	private PlaceDetails result;

	public PlaceDetails getResult() {
		return result;
	}

	public void setResult(PlaceDetails result) {
		this.result = result;
	}
}