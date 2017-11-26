/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package develop.odata.etl;

 
 
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import develop.odata.etl.model.googleplaces.PlaceRecord;
import develop.odata.etl.model.googleplaces.mappings.PlaceDetails;
import develop.odata.etl.services.googleplaces.PlaceService;
 
@SpringBootApplication
public class Application   implements CommandLineRunner  {

    @Autowired
    PlaceService placeService;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
//		
//		placeService.setApiKey("AIzaSyDi8JIfB0JBBgr830O1hyezWy0Uuj0ncOI"); 
//		String placeId =  "EigyNTHlj7DngaPmlrDljJfluILmt6HmsLTljYDkuK3lsbHot6846Jmf";
//		PlaceDetails place = placeService.getPlaceDetails(placeId,"zh-TW");
//		
//		if(!StringUtils.equals(placeId, place.getPlaceId())) {
//			System.out.println("data Error");
//		}
//		 
// 
//		PlaceRecord record =new PlaceRecord(place.getPlaceId(),"zh-TW",place,new Date());
//		
//		placeService.saveOrUpdate(record); 
//		 
//		List<PlaceRecord> data = placeService.findByUpdateTimeGreaterThanEqual(new Date(0l));
//		
//		
//		PlaceRecord testdata = placeService.findById(placeId, "zh-TW");
//		
//		System.out.println(data.size());;
		
	}    

}