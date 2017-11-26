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

	@JsonProperty("error_message")
	private String  errorMessage;
	
	@JsonProperty("status")
	private String  status;
	
	public PlaceDetails getResult() {
		return result;
	}

	public void setResult(PlaceDetails result) {
		this.result = result;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}