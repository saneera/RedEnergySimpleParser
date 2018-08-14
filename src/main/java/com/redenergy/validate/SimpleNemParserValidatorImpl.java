package com.redenergy.validate;

import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redenergy.csv.CsvReader;
import com.redenergy.exception.SimpleNem12ParserException;
import com.redenergy.model.EnergyUnit;
import com.redenergy.model.Quality;


/**
 * SimpleNemParserValidatorImpl implementation class for validaton of csv file
 * @author Saneera Yapa
 *
 */
public class SimpleNemParserValidatorImpl implements SimpleNemParserValidator {

	private final Logger LOG = LoggerFactory.getLogger(SimpleNemParserValidatorTest.class);
	private static final int NMI_DEFAULT_LENGTH = 10;
	
	/**
     * Validate the Energy Unit file - valid values KWH
     *
     * @param input - energyUnit value in csv file
     * @return - input value if its valid
     * @throws SimpleNem12ParserException
     */
	@Override
	public String validateEnergyUnit(String input) throws SimpleNem12ParserException {
		
		if (StringUtils.isBlank(input)) {
            throw new SimpleNem12ParserException("EnergyUnit cannot be blank");
        }
       
		try {
            EnergyUnit.valueOf(input);
        } catch (IllegalArgumentException e) {
        	LOG.error(String.format("EnergyUnit value '%s' is invalid, value should be 'KWH'", input));
            throw new SimpleNem12ParserException(String.format("EnergyUnit value '%s' is invalid, value should be 'KWH'", input));
        }
        
        return input;
	}
	
	
	/**
     * Validate the Quality IN FILE - valid values A, E
     *
     * @param input - Quality value in csv file
     * @return - input value if its valid
     * @throws SimpleNem12ParserException
     */

	@Override
	public String validateQuality(String input) throws SimpleNem12ParserException {
		if (StringUtils.isBlank(input)) {
            throw new SimpleNem12ParserException("Quality cannot be blank");
        }
        try {
            Quality.valueOf(input);
        } catch (IllegalArgumentException e) {
        	LOG.error(String.format("Quality value '%s' is not valid, value should be either 'A' or 'E'", input));
            throw new SimpleNem12ParserException(String.format("Quality value '%s' is not valid, value should contains either 'A' or 'E'", input));
        }
        return input;
	}
	
	/**
     * Validate the NMI  IN FILE - valid length 10
     *
     * @param input - nmi value in csv file
     * @return - input value if its valid
     * @throws SimpleNem12ParserException
     */
	@Override
	public String validateNmi(String input) throws SimpleNem12ParserException {
		if (StringUtils.isBlank(input)) {
	        throw new SimpleNem12ParserException("Input cannot be blank");
	    }
		
        if (input.length() != NMI_DEFAULT_LENGTH) {
        	LOG.error(String.format("NMI '%s' length should be %s", input, NMI_DEFAULT_LENGTH));
            throw new SimpleNem12ParserException(String.format("NMI '%s' length should be %s", input, NMI_DEFAULT_LENGTH));
        }
	    return input;
	}
	
	
	/**
     * Validate csv reader is empty
     *
     * @param csvReader - CsvReader in csv file
     * @return -csvReader value if its valid
     * @throws SimpleNem12ParserException
     */
	@Override
	public CsvReader validateCsvFile(CsvReader csvReader) throws SimpleNem12ParserException {
		if (csvReader == null) {
			LOG.error("CsvReader can't be null");
            throw new SimpleNem12ParserException("CsvReader can't be null");
        }
		return csvReader;
	}

	/**
     * Validate the file contains is empty
     *     
     * @param records - List<CSVRecord> in csv files
     * @throws SimpleNem12ParserException
     */
	@Override
	public void validateCsvRecords(List<CSVRecord> records) throws SimpleNem12ParserException {
		if(records == null || records.isEmpty()) {
			 LOG.error("File does not contains any records to process");
			 throw new SimpleNem12ParserException("File does not contains any records to process");
		}
	}
	

}
