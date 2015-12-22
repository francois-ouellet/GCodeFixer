package gcode.fixer.file;

import java.io.IOException;

public interface IFile {
	void readGCodeFile(String inputFileNameAndPath) throws IOException;
	void fixGCode() throws Exception;
	void writeGCodeToOutputFile() throws IOException;
}