package develop.odata.etl.services.googleplaces;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith; 
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import develop.odata.etl.model.googleplaces.mappings.PlaceDetails;

@RunWith(MockitoJUnitRunner.class)
public class PlaceServiceTest {
	 
	
	private PlaceService placeService;

	@Before
	public void setUp() throws Exception {
		this. placeService = new PlaceService(); 
		this. placeService.setRestTemplate(new RestTemplate());
		this. placeService.setApiKey("AIzaSyDi8JIfB0JBBgr830O1hyezWy0Uuj0ncOI");
		this. placeService.setLanguage("zh-TW"); 
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetPlaceDetails() throws Exception {
		PlaceDetails place = this. placeService.getPlaceDetails("ChIJYRhVuXL3ZzQRpDbHe2NGvyI");
		System.out.println(place);	
	}

}
