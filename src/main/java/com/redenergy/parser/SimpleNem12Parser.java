package com.redenergy.parser;

import java.io.File;
import java.util.Collection;

import com.redenergy.exception.SimpleNem12ParserException;
import com.redenergy.model.MeterRead;


/**
 * This is a interface for SimpleNem12Parser 
 * @author Saneera Yapa
 */
public interface SimpleNem12Parser {
	
	
	 /**
 	 * This method parses the csv file with contains meter data.
 	 *
 	 * @param simpleNem12File file in Simple NEM12 format
 	 * @return Collection of <code>MeterRead</code> that represents the data in the given file.
 	 * @throws SimpleNem12ParserException the simple nem 12 parser exception
 	 */
	  Collection<MeterRead> parseSimpleNem12(File simpleNem12File) throws SimpleNem12ParserException;
}
