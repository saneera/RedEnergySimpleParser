package com.redenergy.validate;

import org.junit.Before;
import org.junit.Test;

import com.redenergy.csv.CsvReader;
import com.redenergy.exception.SimpleNem12ParserException;
import com.redenergy.parser.validate.SimpleNemParserValidator;
import com.redenergy.parser.validate.SimpleNemParserValidatorImpl;

import org.junit.Assert;

/**
 * SimpleNemParserValidatorTest test class for the validator
 * @author Saneera Yapa
 *
 */
public class SimpleNemParserValidatorTest {

	private SimpleNemParserValidator simpleNemParserValidator;
	CsvReader csvReader;	
	
	
	@Before
	public void setUp() throws Exception {
		simpleNemParserValidator =  new SimpleNemParserValidatorImpl();
		csvReader = new CsvReader();		
	}
	
	@Test
	public void testvalidateEnergyUnit() throws Exception {
		Assert.assertSame("KWH",simpleNemParserValidator.validateEnergyUnit("KWH"));			
	}
	
	
	@Test(expected = SimpleNem12ParserException.class)
	public void testvalidateInvalidEnergyUnit() throws Exception {
		Assert.assertSame("AAA", simpleNemParserValidator.validateEnergyUnit("AAA"));			
	}
	
	
	@Test
	public void testvalidateValidateQuality() throws Exception {
		Assert.assertSame("A",simpleNemParserValidator.validateQuality("A"));	
		Assert.assertSame("E",simpleNemParserValidator.validateQuality("E"));	
	}
	
	
	@Test(expected = SimpleNem12ParserException.class)
	public void testvalidateInvalidValidateQuality() throws Exception {
		Assert.assertSame("B", simpleNemParserValidator.validateQuality("B"));	
		Assert.assertSame("C", simpleNemParserValidator.validateQuality("C"));	
	}
	
	
	@Test
	public void testvalidateValidateNmi() throws Exception {
		Assert.assertSame("1234567890",simpleNemParserValidator.validateNmi("1234567890"));	
		Assert.assertSame("1234567893",simpleNemParserValidator.validateNmi("1234567893"));	
	}
	
	
	@Test(expected = SimpleNem12ParserException.class)
	public void testvalidateInvalidValidateNmi() throws Exception {
		Assert.assertSame("12345678911", simpleNemParserValidator.validateNmi("12345678911"));	
		Assert.assertSame("12345678956", simpleNemParserValidator.validateNmi("12345678956"));	
	}
	
	
	@Test
	public void testvalidateValidateCsvFile() throws Exception {		
		Assert.assertSame(csvReader,simpleNemParserValidator.validateCsvFile(csvReader));	
		Assert.assertSame(csvReader,simpleNemParserValidator.validateCsvFile(csvReader));	
	}
	
	
	@Test(expected = SimpleNem12ParserException.class)
	public void testvalidateInvalidValidateCsvFile() throws Exception {
		
		csvReader = null;
		Assert.assertSame(csvReader, simpleNemParserValidator.validateCsvFile(csvReader));	
		Assert.assertSame(csvReader, simpleNemParserValidator.validateCsvFile(csvReader));	
	}
	
}
