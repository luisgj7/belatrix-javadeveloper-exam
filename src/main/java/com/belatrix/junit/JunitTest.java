package com.belatrix.junit;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.belatrix.service.JobLoggerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JunitTest {
	
	
	@Autowired
	@Qualifier("jobLoggerService")
	private JobLoggerService jobLoggerService;
	
	@Before
	public void before() {
		
		//Show Log on console 
		jobLoggerService.setLogToConsole(true);
		
		//Write log to file
		jobLoggerService.setLogToFile(true);
		
		//Save log to Database
		jobLoggerService.setLogToDatabase(true);
	}
	
	@Test
	public void test_logError() throws Exception {
		assertEquals(1, jobLoggerService.LogMessage("this is a ErrorMessage", false, false, true) );
	}
	
	@Test
	public void test_logWarning() throws Exception {
		assertEquals(1, jobLoggerService.LogMessage("This is a Warning Message", false, true, false) );
	}
	
	@Test
	public void test_logMessage() throws Exception {
		assertEquals(1, jobLoggerService.LogMessage("This is a Message", true, false, false) );
	}


}
