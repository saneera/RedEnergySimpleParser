package com.redenergy.parser;

import java.io.File;

import org.junit.Before;

import com.redenergy.csv.CsvReader;
import com.redenergy.parser.validate.SimpleNemParserValidator;
import com.redenergy.parser.validate.SimpleNemParserValidatorImpl;

/**
 * SimpleNem12ParserTest test class for the parser implementation
 * 
 * @author Saneera Yapa *
 */
public class SimpleNem12ParserTest {

	private CsvReader csvReader;
	private SimpleNem12ParserImpl nem12ParserImpl;
	private SimpleNemParserValidator validator;
	
	@Before
	public void setUp() throws Exception {
		csvReader = new CsvReader();	
		nem12ParserImpl = new SimpleNem12ParserImpl();
		validator = new SimpleNemParserValidatorImpl();
		ClassLoader classLoader = getClass().getClassLoader();		
		File file  = new File(classLoader.getResource("SimpleNem12.csv").getFile());
		csvReader.setCsvFile(file);
		nem12ParserImpl.setCsvReader(csvReader);
		nem12ParserImpl.setValidator(validator);		
	}
	
	
	
	
	
	
	
	
}
