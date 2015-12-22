package gcode.fixer.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public abstract class AbstractFile implements IFile {
	private final String fileNameAndPath;
	final GCodeFixer fixer = new GCodeFixer();
	final List<String> gcode = new ArrayList<String>();
	
	public AbstractFile(String fileNameAndPath) {
		this.fileNameAndPath = fileNameAndPath;
	}

	public void readGCodeFile(String inputFileNameAndPath) throws IOException {
		System.out.println("Attempting to read file: " + inputFileNameAndPath);
		List<String> inputLines = null;
		try {
			File inputFile = new File(inputFileNameAndPath);
			inputLines = FileUtils.readLines(inputFile, "UTF-8");
		} catch (IOException ex) {
			System.out.println("Unable to read file: " + inputFileNameAndPath);
			throw ex;
		}
		System.out.println("Finished reading the file: " + inputFileNameAndPath);
		this.gcode.clear();
		this.gcode.addAll(filter(inputLines));
	}

	public void fixGCode() throws Exception {
		System.out.println("Starting to fix GCode");
		List<String> outputLines = null;
		try {
			outputLines = fixer.applyFirstPassOfFixers(gcode);
			outputLines = fixer.applySecondPassOfFixers(outputLines, getFeedRate());
		} catch (Exception ex) {
			System.out.println("Exception while fixing GCode" + ex);
			throw ex;
		}
		this.gcode.clear();
		this.gcode.addAll(outputLines);
		this.gcode.add("G00 X0 Y0");
		System.out.println("Finished to fix GCode");
		
	}
	
	protected abstract float getFeedRate();
	protected abstract List<String> filter(List<String> gcodeToFilter);

	public void writeGCodeToOutputFile() throws IOException {
		System.out.println("Starting to write output file: " + fileNameAndPath);

		File outputFile = new File(fileNameAndPath);
		if (outputFile.exists()) {
			FileUtils.deleteQuietly(outputFile);
			outputFile = new File(fileNameAndPath);
		}
		
		try {
			outputFile.createNewFile();
			FileUtils.writeLines(outputFile, gcode);
		}
		catch(IOException ex) {
			System.out.println("Exception while writing output file" + ex);
			throw ex;
		}

		System.out.println("Finished to write output file: " + fileNameAndPath);
	}

}
