package com.redenergy.parser;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.redenergy.csv.CsvReader;
import com.redenergy.exception.SimpleNem12ParserException;
import com.redenergy.model.EnergyUnit;
import com.redenergy.model.MeterRead;
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
	private ClassLoader classLoader;
	private File validCsvFile;
	private File invalidHeaderCsvFile;
	private File invalidFooterCsvFile;
	
	@Before
	public void setUp() throws Exception {
		csvReader = new CsvReader();	
		nem12ParserImpl = new SimpleNem12ParserImpl();
		validator = new SimpleNemParserValidatorImpl();
		ClassLoader classLoader = getClass().getClassLoader();	
		
		validCsvFile  = new File(classLoader.getResource("SimpleNem12.csv").getFile());
		invalidHeaderCsvFile  = new File(classLoader.getResource("SimpleNem13InvalidHeader.csv").getFile());
		invalidFooterCsvFile  = new File(classLoader.getResource("SimpleNem13InvalidFooter.csv").getFile());		
		
		nem12ParserImpl.setCsvReader(csvReader);
		nem12ParserImpl.setValidator(validator);		
	}
	
	
	  @Test
	  public void testMeterReadsWithValidCSV() throws Exception {
		 
		  csvReader.setCsvFile(validCsvFile);
		  Collection<MeterRead> meterReads = nem12ParserImpl.parseSimpleNem12(validCsvFile);
		  Assert.assertNotNull(meterReads);
		  MeterRead firstRecord = meterReads.stream().findFirst().get();
		  Assert.assertNotNull(firstRecord);
		  Assert.assertEquals("6123456789", firstRecord.getNmi());
		  Assert.assertEquals(EnergyUnit.KWH, firstRecord.getEnergyUnit());
		  Assert.assertNotNull(firstRecord.getVolumes());
		  Assert.assertEquals(7, firstRecord.getVolumes().size());		  
		  MeterRead read6123456789 = meterReads.stream().filter(mr -> mr.getNmi().equals("6123456789")).findFirst().get();
		  Assert.assertEquals(new BigDecimal("-36.84"), read6123456789.getTotalVolume());			 
	      MeterRead read6987654321 = meterReads.stream().filter(mr -> mr.getNmi().equals("6987654321")).findFirst().get();
	      Assert.assertEquals(new BigDecimal("14.33"), read6987654321.getTotalVolume());	  
	  }
	
	
	  @Test(expected = SimpleNem12ParserException.class)
	  public void testMeterReadsWithInvalidHeaderCSV() throws Exception {
		  csvReader.setCsvFile(invalidHeaderCsvFile);	  
		  Collection<MeterRead> meterReads = nem12ParserImpl.parseSimpleNem12(invalidHeaderCsvFile);		  
		  
	  }
	
	  
	  @Test(expected = SimpleNem12ParserException.class)
	  public void testMeterReadsWithInvalidFooterCSV() throws Exception {
		  csvReader.setCsvFile(invalidFooterCsvFile);	  
		  Collection<MeterRead> meterReads = nem12ParserImpl.parseSimpleNem12(invalidFooterCsvFile);		 
	  }
	
}
