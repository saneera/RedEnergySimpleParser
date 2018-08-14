package com.redenergy.csv;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * CsvReaderTest test class for the csv reader
 * 
 * @author Saneera Yapa *
 */
public class CsvReaderTest {
	
	private CsvReader csvReader;
	
	@Before
	public void setUp() throws Exception {
		csvReader = new CsvReader();	
		ClassLoader classLoader = getClass().getClassLoader();		
		File file  = new File(classLoader.getResource("SimpleNem12.csv").getFile());
		csvReader.setCsvFile(file);
	}
	
	@Test
	public void testvalidateInvalidCsvFile() throws Exception {
		Assert.assertNotNull(csvReader.readLines());		
	}
	
}
