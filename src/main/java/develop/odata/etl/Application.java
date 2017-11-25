/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package develop.odata.etl;

 
 
import java.util.Date;
import java.util.List;

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
		
//		 placeService.setApiKey("AIzaSyDi8JIfB0JBBgr830O1hyezWy0Uuj0ncOI");
		 placeService.setLanguage("zh-TW"); 
		
		PlaceDetails place = placeService.getPlaceDetails("ChIJYRhVuXL3ZzQRpDbHe2NGvyI");
		PlaceRecord record =new PlaceRecord();
		record.setData(place);
		record.setPlaceId(place.getPlaceId());
		record.setLang("zh-TW");
		record.setUpdateTime(new Date());
		placeService.saveOrUpdate(record); 
		 
		List<PlaceRecord> data = placeService.finadByupdateTimeGreaterThanEqual(new Date(0l));
		System.out.println(data.size());;
		
	}

    

}