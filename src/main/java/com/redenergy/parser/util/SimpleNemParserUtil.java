package com.redenergy.parser.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redenergy.exception.SimpleNem12ParserException;
import com.redenergy.model.EnergyUnit;
import com.redenergy.model.MeterRead;
import com.redenergy.model.MeterVolume;
import com.redenergy.model.Quality;
import com.redenergy.parser.validate.SimpleNemParserValidator;


public class SimpleNemParserUtil {

	private final static Logger LOG = LoggerFactory.getLogger(SimpleNemParserUtil.class);
	private static final String METER_READ_START_VAL = "200";
	private static final String END_RECORD_CSV_VAL = "900";
	private static final String START_RECORD_CSV_VAL = "100";
	
	/**
     * This method parse the date to local date 
     *
     * @param date - input date
     * @return - LocalDate object
     * @throws SimpleNem12ParserException
     */
	 public static LocalDate parseDate(String date) throws SimpleNem12ParserException {
	        try {
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	            formatter = formatter.withZone(ZoneId.of("Australia/Melbourne"));
	            return LocalDate.parse(date, formatter);
	        } catch (DateTimeParseException e) {
	        	LOG.error(String.format("Not able to parse date %s", date));
	            throw new SimpleNem12ParserException(String.format("Not able to parse date %s", date));
	        }
	 }
	 
	 
	 public static void checkRecordsIsValid(String firstColumn) {
			if (START_RECORD_CSV_VAL.equals(firstColumn) || END_RECORD_CSV_VAL.equals(firstColumn)) {
			     return;
			 }
		}
	 
	 
	 public static void createMeterRead(CSVRecord record, String valueOfFirstColumn, List<MeterRead> meterReads, SimpleNemParserValidator validator) throws SimpleNem12ParserException {
		 if (METER_READ_START_VAL.equals(valueOfFirstColumn)) {
				MeterRead meterRead = new MeterRead();
		        meterRead.setNmi(validator.validateNmi(record.get(1)));
		        meterRead.setEnergyUnit(EnergyUnit.valueOf(validator.validateEnergyUnit(record.get(2))));	
		        meterReads.add(meterRead);		
		 }	
	}
		
	 public static void createMeterVolume(CSVRecord record, List<MeterRead> meterReads, String valueOfFirstColumn, SimpleNemParserValidator validator) throws SimpleNem12ParserException {
		if ("300".equals(valueOfFirstColumn)) {
		 	MeterRead meterRead = meterReads.get(meterReads.size() - 1);
	        MeterVolume meterVolume = new MeterVolume(BigDecimal.valueOf(Double.parseDouble(record.get(2))), Quality.valueOf(validator.validateQuality(record.get(3))));
	        meterRead.appendVolume(SimpleNemParserUtil.parseDate(record.get(1)), meterVolume);
		}		 
	 }

	 public static void validateCsvConatainsValidRows(List<CSVRecord> records) throws SimpleNem12ParserException {
			startRecordContains100(records);
	        endingRecordContains900(records);		
		}



	 public static void startRecordContains100(List<CSVRecord> records)  throws SimpleNem12ParserException {
		 	 Optional<CSVRecord> first = records.stream().findFirst();
		 	 if(records.stream().findFirst().isPresent() && !first.filter(s ->s.get(0).equals(START_RECORD_CSV_VAL)).isPresent()) {
		 		 LOG.error("Start recording 100 must be the first value in the file");
				 throw new SimpleNem12ParserException("Start recording 100 must be the first value in the file");
			 }		
		}
		
	 public static void endingRecordContains900(List<CSVRecord> records)  throws SimpleNem12ParserException {
			Optional<CSVRecord> last = records.stream().reduce((first, second) -> second);		
			if(last.isPresent() && !last.filter(s ->s.get(0).equals(END_RECORD_CSV_VAL)).isPresent()) {
				 LOG.error("End of file recording 900 must be the last value in the file");
				 throw new SimpleNem12ParserException("End of file recording 900 must be the last value in the file");
			}
			
		}
	
}
