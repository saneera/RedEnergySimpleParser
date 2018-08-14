package com.redenergy.csv;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redenergy.exception.SimpleNem12ParserException;
import com.redenergy.validate.SimpleNemParserValidatorTest;

import lombok.Getter;
import lombok.Setter;

/**
 * The CsvReader used to read the csv file and returns .
 * 
 * @author Saneera Yapa
 */
@Getter @Setter
public class CsvReader {

	private final Logger LOG = LoggerFactory.getLogger(SimpleNemParserValidatorTest.class);
	
	private File csvFile;
       
    /**
     * Read list csv file provided    *.
     *
     * @return all CSVRecord contains in csv file .
     * @throws SimpleNem12ParserException  parser exception
     */
    public List<CSVRecord> readLines() throws SimpleNem12ParserException {

        if (csvFile == null) {
            throw new SimpleNem12ParserException("Input csv file cant be null");
        }
        
        //in here use try with resources concept on java
        try (
	            Reader reader = new FileReader(csvFile);
        		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(','));
	        ) {
        	
        	return csvParser.getRecords();
        	
        } catch (IOException e) {
        	LOG.error("Csv file reading error {}",e.getMessage());
            throw new SimpleNem12ParserException(String.format("Csv file reading error [%s]", e.getMessage()));
		}	
         
    }


}
