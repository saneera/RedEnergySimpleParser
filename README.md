Nem12 Parser Implementation

This is the implementation of given logic to handle the CSV file 

1) Use gradle (dependency management system) and JAVA 1.8.
2) Use lombok to handle the getter and setter (less code) ( to use lombok your IDE should support lombok feature see : https://projectlombok.org/)
2) Here I used commons-csv library to read the csv files.
3) com.redenergy.model package contains all the model object to parse the csv file content.
4) com.redenergy.csv package contains read the csv file and return list of CSVRecord object.
5) com.redenergy.exception package contains the classes for handle exceptions.
6) com.redenergy.validate package contains validation of csv file
7) com.redenergy.parser.util package contains SimpleNemParserUtil class it handle the parsing data validation

