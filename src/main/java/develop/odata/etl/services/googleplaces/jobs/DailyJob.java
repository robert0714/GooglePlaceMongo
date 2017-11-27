package develop.odata.etl.services.googleplaces.jobs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException; 
import java.nio.file.Files;
import java.nio.file.Path; 
import java.text.SimpleDateFormat; 
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.io.BufferedOutputStream; 



import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
 
import com.fasterxml.jackson.databind.ObjectMapper;

import develop.odata.etl.model.googleplaces.PlaceCompositeKey;
import develop.odata.etl.model.googleplaces.PlaceRecord;
import develop.odata.etl.model.googleplaces.mappings.PlaceDetails;
import develop.odata.etl.services.googleplaces.PlaceService;
@Component
public class DailyJob {
	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(DailyJob.class);
	
    static final int BUFFER = 20480;

	
	@Value("${develop.odata.etlservices.googleplaces.source.placeIds.uri}")
	private String source;
	
	
	@Value("${develop.odata.etlservices.googleplaces.artifact.output}")
	private String output;
	
	@Value("${develop.odata.etlservices.googleplaces.source.placeData.beforeDays}")
    private Integer beforeDays;
	
	@Value("${develop.odata.etlservices.googleplaces.artifact.langType}")
	private String[] langTypes;
	
	
	@Value("${develop.odata.etlservices.googleplaces.daily.limit}")
	private Integer limit;
	
	@Autowired
	private  PlaceService service ;
	
	
	private final ObjectMapper mapper;
	
	public DailyJob() {
		super();
		this.mapper = new ObjectMapper();
	}
	
	
	public void processCatchData () throws IOException, URISyntaxException {
		
		//讀取要更新資料placeId清單
		List<String> placeIds = FileUtils.readLines(new File(new URI(source)));
		Date beforeDay = DateUtils.addDays(new Date(), -beforeDays);		 
		int scpoelimit = limit.intValue()/langTypes.length;		
		for (int i = 0 ; i< scpoelimit && i< placeIds.size();i++) {
			final	String placeId = placeIds.get(i);
			for(String lang :langTypes ) {
				
				boolean skip = processCatchDataUnit(  beforeDay, placeId, lang);
				
				if(skip) {
					scpoelimit++;
				}
			}			
		}		
	}

	/**
	 * Process catch data unit. 跳過不處理，則回傳true ,要處理者回傳false
	 *
	 * @param beforeDay the before day
	 * @param placeId the place id
	 * @param lang the lang
	 * @return true, if successful
	 */
	protected boolean processCatchDataUnit( final Date beforeDay, final String placeId, final String lang) {
		boolean result =false ;
		if(StringUtils.isBlank(placeId)) {
			return true ;
		}
		PlaceCompositeKey id = new PlaceCompositeKey(placeId, lang);
		// 如果找到資料還是在有效期間，就不連到google抓資料！
		List<PlaceRecord> found = service.findByIdAndUpdateTimeGreaterThanEqual(id, beforeDay);
		if (CollectionUtils.isEmpty(found)) {
			// 找不到所以可以連到google抓資料並且存檔
			PlaceDetails place = null;
			
			try {
				place = service.getPlaceDetails(placeId, lang);
			} catch (Exception e) {
				LOGGER.error("placeId: {}  has problem!!! }", placeId );;
			}
			if(place == null ) {
				//google place api回報查無資料 ，跳到下一個
				return true ;
			}
			
			PlaceRecord record;
			if (!StringUtils.equals(placeId, place.getPlaceId())) {
				LOGGER.error("placeId is not constraint with data: {} ,{}", placeId, place.getPlaceId());
				record = new PlaceRecord(placeId, lang, place, new Date());

			} else {
				record = new PlaceRecord(place.getPlaceId(), lang, place, new Date());
			}
			service.saveOrUpdate(record);

		} else {
			// 有找到，跳到下一個
			result =true ; 
		}
		return result ; 
	}
	public void exportFile() throws IOException, URISyntaxException {
		List<PlaceRecord> allData = service.findAll();
		Iterator<PlaceRecord> iterator = allData.iterator() ; 

		Path tmpPath = Files.createTempDirectory("poi-"    );  
		
		File tmpDir = tmpPath.toFile();
		
		if (!(tmpDir.isDirectory())) {
			throw new IOException("Could not create temp directory: " + tmpDir.getAbsolutePath());
		}
		 
		while(iterator.hasNext()) {
			PlaceRecord record = iterator.next();
			String lang = record.getId().getLang(); 
			File langD = new File(tmpDir.getAbsolutePath() , lang  );
			
			if (!langD.exists()) {
				langD.mkdir();
			}
			String fileName =null;
			if(record.getId().getPlaceId().length() < 29) {
				fileName =record.getId().getPlaceId() ;
			}else {
				//檔名長度超過28字元無法存檔，所以乾脆把後面截掉
				fileName = StringUtils.substring(record.getId().getPlaceId(), 0, 28);
			}
			File resultFile = new File(langD.getAbsolutePath() ,fileName );
			this.mapper.writeValue(resultFile, record.getData());
			iterator.remove();
			 
			
		}
		
		//再暫存區 輸出完畢後，接下來就是進行壓縮轉存到輸出位置中。
		File  outputD = new File(new URI(output));
		if(!outputD.exists() ) {
			outputD.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("-yyyy-MM-dd-");
		String suffix = 	sdf.format(new Date()); 
		
		File gzipFile =new File(outputD.getAbsolutePath() ,"poi"+suffix+".tar.gz" ); 
		 
		zipFile( tmpDir.getAbsolutePath()   ,gzipFile.getAbsolutePath());
	}
	 
	public void zipFile(String src, String dest) {

		FileOutputStream destination = null;
		BufferedOutputStream bOut = null;
		GzipCompressorOutputStream gzOut = null;
		TarArchiveOutputStream tOut = null;

		try {
			destination = new FileOutputStream(dest);
		} catch (FileNotFoundException e) {
			LOGGER.error("Output directory does not exist..");
			e.printStackTrace();
		}

		/** Step: 1 ---> create a TarArchiveOutputStream object. **/
		try {
			bOut = new BufferedOutputStream(destination);
			gzOut = new GzipCompressorOutputStream(bOut);
			tOut = new TarArchiveOutputStream(gzOut);
			/**
			 * Step: 2 --->Open the source data and get a list of files from given directory
			 * recursively.
			 **/

			File source = new File(src);
			if (!source.exists()) {
				LOGGER.error("Input directory does not exist..");
			}

			zipFilesRecursively(source.getParentFile(), source, tOut);

			/** Step: 7 --->close the output stream. **/

			tOut.close();

			LOGGER.info("tar.gz file created successfully!!");

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private void zipFilesRecursively(File baseDir, File source, TarArchiveOutputStream out) throws IOException {

		if (source.isFile()) {

			LOGGER.info("Adding File: {} ", baseDir.toURI().relativize(source.toURI()).getPath());

			FileInputStream fi = new FileInputStream(source);
			BufferedInputStream sourceStream = new BufferedInputStream(fi, BUFFER);

			/** Step: 3 ---> Create a tar entry for each file that is read. **/

			/**
			 * relativize is used to to add a file to a tar, without including the entire
			 * path from root.
			 **/

			TarArchiveEntry entry = new TarArchiveEntry(source,
					baseDir.getParentFile().toURI().relativize(source.toURI()).getPath());

			/** Step: 4 ---> Put the tar entry using putArchiveEntry. **/

			out.putArchiveEntry(entry);

			/**
			 * Step: 5 ---> Write the data to the tar file and close the input stream.
			 **/

			int count;
			byte data[] = new byte[BUFFER];
			while ((count = sourceStream.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			sourceStream.close();

			/** Step: 6 --->close the archive entry. **/

			out.closeArchiveEntry();

		} else {

			if (source.listFiles() != null) {

				/** Add an empty folder to the tar **/

				if (source.listFiles().length == 0) {

					LOGGER.info("Adding Empty Folder: {}", baseDir.toURI().relativize(source.toURI()).getPath());
					TarArchiveEntry entry = new TarArchiveEntry(source,
							baseDir.getParentFile().toURI().relativize(source.toURI()).getPath());
					out.putArchiveEntry(entry);
					out.closeArchiveEntry();

				}

				for (File file : source.listFiles())

					zipFilesRecursively(baseDir, file, out);
			}
		}
	}
}

