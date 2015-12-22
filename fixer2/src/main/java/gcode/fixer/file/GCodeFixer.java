package gcode.fixer.file;

import gcode.fixer.linefixer.ILineFixer;
import gcode.fixer.linefixer.fixers.G00LineFixer;
import gcode.fixer.linefixer.fixers.G01LineFixer;
import gcode.fixer.linefixer.fixers.G03LineFixer;
import gcode.fixer.linefixer.fixers.G36LineFixer;
import gcode.fixer.linefixer.fixers.M30LineFixer;
import gcode.fixer.linefixer.fixers.M48LineFixer;
import gcode.fixer.linefixer.fixers.M71LineFixer;
import gcode.fixer.linefixer.fixers.M74LineFixer;
import gcode.fixer.linefixer.fixers.M998LineFixer;
import gcode.fixer.linefixer.fixers.M999LineFixer;
import gcode.fixer.linefixer.fixers.PoundSymbolLineFixer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class GCodeFixer {
	private final static Collection<ILineFixer> FIRST_PASS_FIXERS = new ArrayList<ILineFixer>();
	{
		FIRST_PASS_FIXERS.add(new G03LineFixer());
		FIRST_PASS_FIXERS.add(new G36LineFixer());
		FIRST_PASS_FIXERS.add(new M30LineFixer());
		FIRST_PASS_FIXERS.add(new M48LineFixer());
		FIRST_PASS_FIXERS.add(new M71LineFixer());
		FIRST_PASS_FIXERS.add(new M74LineFixer());
		FIRST_PASS_FIXERS.add(new M998LineFixer());
		FIRST_PASS_FIXERS.add(new M999LineFixer());
		FIRST_PASS_FIXERS.add(new PoundSymbolLineFixer());
	}

	private static final G00LineFixer g00LineFixer = new G00LineFixer();
	private static final G01LineFixer g01LineFixer = new G01LineFixer();
	
	private final static Collection<ILineFixer> SECOND_PASS_FIXERS = new ArrayList<ILineFixer>();
	{
		SECOND_PASS_FIXERS.add(g00LineFixer);
		SECOND_PASS_FIXERS.add(g01LineFixer);
	}

	public List<String> applyFirstPassOfFixers(List<String> gcodeInputLines) throws IOException {
		final List<String> gcodeOutputLines = new ArrayList<String>();
		
		for(String gcodeInputLine : gcodeInputLines) {
			Collection<String> fixedGCode = Arrays.asList(new String[]{gcodeInputLine});
			for(ILineFixer fixer : FIRST_PASS_FIXERS) {
				if(fixer.accepts(gcodeInputLine)) {
					fixedGCode = fixer.fix(gcodeInputLine);
					break;
				}
			}
			if(!CollectionUtils.isEmpty(fixedGCode)) {
				gcodeOutputLines.addAll(fixedGCode);
			}
		}
		
		return gcodeOutputLines;
	}

	public List<String> applySecondPassOfFixers(List<String> gcodeInputLines, float feedRate) throws IOException {
		g00LineFixer.setFeedRate(feedRate);
		
		final List<String> gcodeOutputLines = new ArrayList<String>();
		
		for(String gcodeInputLine : gcodeInputLines) {
			Collection<String> fixedGCode = Arrays.asList(new String[]{gcodeInputLine});
			for(ILineFixer fixer : SECOND_PASS_FIXERS) {
				if(fixer.accepts(gcodeInputLine)) {
					fixedGCode = fixer.fix(gcodeInputLine);
					break;
				}
			}
			if(!CollectionUtils.isEmpty(fixedGCode)) {
				gcodeOutputLines.addAll(fixedGCode);
			}
		}
		
		return gcodeOutputLines;
	}
}
