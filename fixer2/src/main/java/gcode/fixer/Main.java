package gcode.fixer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class Main {
	final GCodeFixer fixer = new GCodeFixer();
	List<String> inputLines = null;
	List<String> outputLines = null;

	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("input", true, "input file name");
		options.addOption("output", true, "output file name");
		
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		try {
		   cmd = parser.parse(options, args);
		}
		catch(ParseException ex) {
		   System.err.println( "Parsing failed.  Reason: " + ex.getMessage() );
		   
		   HelpFormatter formatter = new HelpFormatter();
		   formatter.printHelp( "ant", options );
		   
		   System.exit(-1);
		}
		
		String inputFileName = cmd.getOptionValue("input");
		String outputFileName = cmd.getOptionValue("output");
		
		if(StringUtils.isEmpty(inputFileName) || StringUtils.isEmpty(outputFileName)) {
		   System.err.println( "input and output file names must be provided");
		   
		   HelpFormatter formatter = new HelpFormatter();
		   formatter.printHelp( "ant", options );

		   System.exit(-1);
		}
		
		Main program = new Main();
		try {
			program.readGCodeToBeFixed(inputFileName);
			program.fixGCode();
			program.writeGCodeToOutputFile(outputFileName);
		}
		catch(Exception ex) {
			System.exit(-1);
		}
	}
		

	public void readGCodeToBeFixed(String inputFileName) throws IOException {
		System.out.println("Attempting to read file: " + inputFileName);
		try {
			File inputFile = new File(inputFileName);
			inputLines = FileUtils.readLines(inputFile, "UTF-8");
		} catch (IOException ex) {
			System.out.println("Unable to read file: " + inputFileName);
			throw ex;
		}
		System.out.println("Finished reading the file: " + inputFileName);
	}

	public void fixGCode() throws IOException {
		System.out.println("Starting to fix GCode");
		try {
			outputLines = fixer.fixGCode(inputLines);
		} catch (IOException ex) {
			System.out.println("Exception while fixing GCode" + ex);
			throw ex;
		}
		System.out.println("Finished to fix GCode");
	}

	public void writeGCodeToOutputFile(String outputFileName) throws IOException {
		System.out.println("Starting to write output file: " + outputFileName);

		File outputFile = new File(outputFileName);
		if (outputFile.exists()) {
			FileUtils.deleteQuietly(outputFile);
			outputFile = new File(outputFileName);
		}
		
		try {
			outputFile.createNewFile();
			FileUtils.writeLines(outputFile, outputLines);
		}
		catch(IOException ex) {
			System.out.println("Exception while writing output file" + ex);
			throw ex;
		}

		System.out.println("Finished to write output file: " + outputFileName);
	}
}
