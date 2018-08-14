package com.redenergy.validate;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.redenergy.csv.CsvReader;
import com.redenergy.exception.SimpleNem12ParserException;

/**
 * SimpleNemParserValidator - class for validaton of csv file contains
 * @author Saneera Yapa
 *
 */
public interface  SimpleNemParserValidator {
	String validateEnergyUnit(String input) throws SimpleNem12ParserException;
	String validateQuality(String input) throws SimpleNem12ParserException;
	String validateNmi(String input) throws SimpleNem12ParserException;
	CsvReader validateCsvFile(CsvReader csvReader) throws SimpleNem12ParserException;
	void validateCsvRecords(List<CSVRecord> records) throws SimpleNem12ParserException;
}
