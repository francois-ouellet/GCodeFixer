package gcode.fixer;

import gcode.fixer.file.CutFile;
import gcode.fixer.file.DrillFile;
import gcode.fixer.file.IFile;
import gcode.fixer.file.TracesFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
	public static void main(String[] args) {
		CommandLine cmd = parseCommandLineOptions(args);
		String inputFileNameAndPath = cmd.getOptionValue("input");
		String inputFileFolderName = new File(inputFileNameAndPath).getParent();
		
		List<IFile> files = new ArrayList<IFile>();
		files.add(new DrillFile(inputFileFolderName.toString() + "drill.txt"));
		files.add(new TracesFile(inputFileFolderName.toString() + "traces.txt"));
		files.add(new CutFile(inputFileFolderName.toString() + "cut.txt"));
		
		try {
			for(IFile file : files) {
				file.readGCodeFile(inputFileNameAndPath);
				file.fixGCode();
				file.writeGCodeToOutputFile();
			}
		}
		catch(Exception ex) {
			System.exit(-1);
		}
	}

	private static CommandLine parseCommandLineOptions(String[] args) {
		Options options = new Options();
		options.addOption("input", true, "input file name");
		
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		
		try {
		   cmd = parser.parse(options, args);
		}
		catch(ParseException ex) {
		   System.err.println( "Parsing failed.  Reason: " + ex.getMessage() );
		   
		   HelpFormatter formatter = new HelpFormatter();
		   formatter.printHelp( "java -jar fixer.jar", options );
		   
		   System.exit(-1);
		}
		return cmd;
	}
}
