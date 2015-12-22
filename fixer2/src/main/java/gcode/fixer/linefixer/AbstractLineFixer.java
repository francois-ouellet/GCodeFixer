package gcode.fixer.linefixer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractLineFixer implements ILineFixer {
	protected static final Collection<String> EMPTY_GCODE = new ArrayList<String>(); 

	public final boolean accepts(String gcodeLine) {
		if(StringUtils.isEmpty(gcodeLine)) {
			return false;
		}
		
		return spcAccepts(gcodeLine); 
	}
	
	public final Collection<String> fix(String gcodeLine) {
		if(StringUtils.isEmpty(gcodeLine)) {
			return EMPTY_GCODE;
		}
			
		return spcFix(gcodeLine);
	}
	
	protected abstract boolean spcAccepts(String gcodeLine);
	protected abstract Collection<String> spcFix(String gcodeLine);

	protected void logRemovingGCode(String gcodeLine) {
		Logger.getAnonymousLogger().log(Level.INFO, "Removing the following GCode line: " + gcodeLine);
	}
	
	protected void logReplacingGCode(String oldGCodeLine, String newGCodeLine) {
		String msg = "Replacing the following GCode line: " + oldGCodeLine + " with: " + newGCodeLine;
		Logger.getAnonymousLogger().log(Level.INFO, msg);
	}
	
	protected void logReplacingGCode(String oldGCodeLine, Collection<String> newGCodeLines) {
		String msg = "Replacing the following GCode line: \n" + oldGCodeLine + " \nwith: ";
		for(String line : newGCodeLines) {
			msg += "\n" + line;
		}
		Logger.getAnonymousLogger().log(Level.INFO, msg);
	}
}
