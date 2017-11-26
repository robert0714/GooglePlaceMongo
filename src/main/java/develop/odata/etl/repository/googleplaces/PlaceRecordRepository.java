package develop.odata.etl.repository.googleplaces;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
 

import develop.odata.etl.model.googleplaces.PlaceRecord;

import develop.odata.etl.model.googleplaces.PlaceCompositeKey;;

@RepositoryRestResource(collectionResourceRel = "place", path = "place")
public interface PlaceRecordRepository  extends MongoRepository<PlaceRecord, PlaceCompositeKey> {
	
	public PlaceRecord findById(  PlaceCompositeKey id);
    
    public List<PlaceRecord> updateTimeGreaterThanEqual(@Param("updateTime") Date d1  );  
    
    public List<PlaceRecord> updateTimeBetween(@Param("updateTime") Date d1 ,@Param("updateTime") Date d2  );
    
    public List<PlaceRecord> findByIdAndUpdateTimeBetween( PlaceCompositeKey id ,   Date updateTime , Date d2  );
    
    public List<PlaceRecord> findByIdAndUpdateTimeGreaterThanEqual( PlaceCompositeKey id ,   Date updateTime   );
    
}
