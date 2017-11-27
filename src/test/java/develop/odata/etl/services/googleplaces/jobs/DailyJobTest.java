package develop.odata.etl.services.googleplaces.jobs;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DailyJobTest {

	@Autowired
	private DailyJob job;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public void testProcessCatchData() throws Exception {
		job.processCatchData();
		;
	}

	@Test
//	@Ignore
	public void testExportFile() throws Exception {
		job.exportFile();
	}

}
