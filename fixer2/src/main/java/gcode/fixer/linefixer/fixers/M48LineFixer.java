package gcode.fixer.linefixer.fixers;

import gcode.fixer.linefixer.AbstractLineFixer;

import java.util.Collection;

public class M48LineFixer extends AbstractLineFixer {
	@Override
	protected boolean spcAccepts(String gcodeLine) {
		return gcodeLine.startsWith("M48"); 
	}
	
	@Override
	protected Collection<String> spcFix(String gcodeLine) {
		logRemovingGCode(gcodeLine);
		return EMPTY_GCODE;
	}
}
