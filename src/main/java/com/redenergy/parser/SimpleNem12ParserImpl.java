package com.redenergy.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redenergy.csv.CsvReader;
import com.redenergy.exception.SimpleNem12ParserException;
import com.redenergy.model.MeterRead;
import com.redenergy.parser.util.SimpleNemParserUtil;
import com.redenergy.validate.SimpleNemParserValidator;
import com.redenergy.validate.SimpleNemParserValidatorImpl;

import lombok.Getter;
import lombok.Setter;

/**
 * this class is used for the implementation of SimpleNem12Parser
 * and validate and returns Collection of <code>MeterRead</code> object by parsing csv file provided.
 *
 * @author Saneera Yapa
 */

@Getter @Setter
public class SimpleNem12ParserImpl implements SimpleNem12Parser {

	private final Logger LOG = LoggerFactory.getLogger(SimpleNem12ParserImpl.class);
	 
	/** The validator. */
	private SimpleNemParserValidator validator;
	
	/** The csv reader. */
	private CsvReader csvReader;
	
	
	 /**
 	 * This method parses the csv file with contains meter data.
 	 *
 	 * @param simpleNem12File file in Simple NEM12 format
 	 * @return Collection of <code>MeterRead</code> that represents the data in the given file.
 	 * @throws SimpleNem12ParserException the simple nem 12 parser exception
 	 */
	@Override
	public Collection<MeterRead> parseSimpleNem12(File simpleNem12File) throws SimpleNem12ParserException {
		LOG.info("Parsing csv file");
		csvReader.setCsvFile(simpleNem12File);
		validator.validateCsvFile(csvReader);		
		List<CSVRecord> records = csvReader.readLines();
		validator.validateCsvRecords(records);				
		return parseAndReadMeterReads(records);
	}

	
	 /**
 	 * Parses the and read meter reads.
 	 *
 	 * @param csvRecords the csv records
 	 * @return Collection of <code>MeterRead</code> object
 	 * @throws SimpleNem12ParserException the simple nem 12 parser exception
 	 */
 	private Collection<MeterRead> parseAndReadMeterReads(List<CSVRecord> csvRecords) throws SimpleNem12ParserException {
	        List<MeterRead> meterReads = new ArrayList<>();
	        SimpleNemParserUtil.validateCsvConatainsValidRows(csvRecords);
          
	        //iterate the csvRecord and manipulating the data	       
	        csvRecords.forEach(record -> {	           
	        	try {
	             
	            	 String valueOfFirstColumn = record.get(0).trim();	            	
	            	 //check the columns start with 100 or  900 if it is return ( it's not valid file)
	            	 SimpleNemParserUtil.checkRecordsIsValid(valueOfFirstColumn);	            	 
	            	 //if meter start with 200 it means that start of meter read block
	           		 SimpleNemParserUtil.createMeterRead(record, valueOfFirstColumn, meterReads, validator);	           	
	            	 //meter volumes start here
	            	 SimpleNemParserUtil.createMeterVolume(record, meterReads, valueOfFirstColumn, validator);            		 
	            	 
	            	
	            } catch (SimpleNem12ParserException e) {
	            	LOG.error("Error when parsing csv file {}", e.getMessage());
	             	throw new RuntimeException(e.getMessage());					
			    }
	        });
	        
	        return meterReads;
	    }


	 /**
 	 * The main method.
 	 *
 	 * @param args the arguments
 	 * @throws SimpleNem12ParserException the simple nem 12 parser exception
 	 */
 	public static void main(String[] args) throws SimpleNem12ParserException {
 		 ClassLoader classLoader = SimpleNem12ParserImpl.class.getClassLoader();		
 		 File file  = new File(classLoader.getResource("SimpleNem12.csv").getFile());
		 CsvReader reader = new CsvReader();
		 SimpleNem12ParserImpl nem12ParserImpl = new SimpleNem12ParserImpl();
		 SimpleNemParserValidator validator = new SimpleNemParserValidatorImpl();
		 nem12ParserImpl.setCsvReader(reader);
		 nem12ParserImpl.setValidator(validator);		
		 
		 Collection<MeterRead> meterReads = nem12ParserImpl.parseSimpleNem12(file);
		 
		 MeterRead read6123456789 = meterReads.stream().filter(mr -> mr.getNmi().equals("6123456789")).findFirst().get();
		 System.out.println(String.format("Total volume for NMI 6123456789 is %f", read6123456789.getTotalVolume()));
		 
		 MeterRead read6987654321 = meterReads.stream().filter(mr -> mr.getNmi().equals("6987654321")).findFirst().get();
		 System.out.println(String.format("Total volume for NMI 6987654321 is %f", read6987654321.getTotalVolume()));
	 }
	
	
}
