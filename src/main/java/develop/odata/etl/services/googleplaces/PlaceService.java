package develop.odata.etl.services.googleplaces;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import develop.odata.etl.model.googleplaces.PlaceRecord;
import develop.odata.etl.model.googleplaces.mappings.PlaceDetails;
import develop.odata.etl.model.googleplaces.mappings.PlaceDetailsResponse;
import develop.odata.etl.repository.googleplaces.PlaceRecordRepository;

@Component
public class PlaceService {
	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(PlaceService.class);

	private static final String PLACE_DETAILS_URL = "https://maps.googleapis.com/"
			+ "maps/api/place/details/json?placeid=" + "{searchId}&sensor=false&key={key}&language={language}";

	@Value("${api.key}")
	private String apiKey;

	// @Value("${api.language}")
	private String language;

	@Autowired
	PlaceRecordRepository repository;

	@Autowired
	private RestTemplate restTemplate;

	private LoadingCache<String, PlaceDetails> placeDetails = CacheBuilder.newBuilder().maximumSize(1000)
			.expireAfterAccess(24, TimeUnit.HOURS).build(new CacheLoader<String, PlaceDetails>() {
				@Override
				public PlaceDetails load(String searchId) throws Exception {
					PlaceDetailsResponse response = restTemplate.getForObject(PLACE_DETAILS_URL,
							PlaceDetailsResponse.class, searchId, apiKey, language);
					if (response.getResult() != null) {
						return response.getResult();
					} else {
						throw new Exception("Unable to find details for placeid: " + searchId);
					}
				}
			});

	public PlaceDetails getPlaceDetails(String searchId) {
		try {
			return placeDetails.get(searchId);
		} catch (ExecutionException e) {
			LOGGER.warn("An exception occurred while " + "fetching place details!", e.getCause());
			return null;
		}
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void setRepository(PlaceRecordRepository repository) {
		this.repository = repository;
	}

	public PlaceRecord findByPlaceId(@Param("placeId") String placeId) {
		return this.repository.findByPlaceId(placeId);
	}

	public List<PlaceRecord> finadByupdateTimeGreaterThanEqual(@Param("updateTime") Date d1) {
		return this.repository.updateTimeGreaterThanEqual(d1);
	}

	public List<PlaceRecord> finadByupdateTimeBetween(@Param("updateTime") Date d1, @Param("updateTime") Date d2) {
		return this.repository.updateTimeBetween(d1, d2);
	}
	public PlaceRecord saveOrUpdate(PlaceRecord placeRecord) {
		return this.repository.save(placeRecord );
	}
	 
	public void saveOrUpdate(Iterable<PlaceRecord> placeRecords) {
		  this.repository.save(placeRecords );
	}

}
