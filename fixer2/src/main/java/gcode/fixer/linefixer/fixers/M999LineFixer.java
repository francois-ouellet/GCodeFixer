package gcode.fixer.linefixer.fixers;

import java.util.Collection;

import gcode.fixer.linefixer.AbstractLineFixer;

public class M999LineFixer extends AbstractLineFixer {
	@Override
	protected boolean spcAccepts(String gcodeLine) {
		return gcodeLine.startsWith("M999"); 
	}

	@Override
	public Collection<String> spcFix(String gcodeLine) {
		logRemovingGCode(gcodeLine);
		return EMPTY_GCODE;
	}
}
