/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package develop.odata.etl;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableMongoRepositories(basePackages= {"develop.odata.etl.repository.googleplaces"})
@EnableAutoConfiguration(exclude = { org.springframework.boot.autoconfigure.web.WebClientAutoConfiguration.class,
		org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration.class,
		org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration.class , 
		org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration.class})
public class MongoConfiguration extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {
		return "springdata";
	}

	@Value("${spring.data.mongodb.host}")
	private String host;
	
	@Value("${spring.data.mongodb.port}")
	private Integer port; 
	
	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient(host, port);
	}
	@Bean
	public RestTemplate getRest() {
		return new RestTemplate();
	}
	
//	@Override
//	protected Collection<String> getMappingBasePackages() {
//		Collection<String> reesult = super.getMappingBasePackages();
//		// reesult.add("develop.odata.etl.repository.googleplaces");
//		reesult.add("develop.odata.etl.model.googleplaces");
//		reesult.add("develop.odata.etl.model.googleplaces.mappings");
//		return reesult;
//	}

//	@Bean
//	public PlaceService getservice() {
//		PlaceService placeService = new PlaceService();
//		placeService.setRestTemplate(new RestTemplate());
//		placeService.setApiKey("AIzaSyDi8JIfB0JBBgr830O1hyezWy0Uuj0ncOI");
//		placeService.setLanguage("zh-TW");
//		return placeService;
//	}
}
