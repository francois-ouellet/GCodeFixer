package gcode.fixer.linefixer.fixers;

import gcode.fixer.linefixer.AbstractLineFixer;

import java.util.Collection;

public class M30LineFixer extends AbstractLineFixer {
	@Override
	protected boolean spcAccepts(String gcodeLine) {
		return gcodeLine.startsWith("M30"); 
	}

	@Override
	public Collection<String> spcFix(String gcodeLine) {
		logRemovingGCode(gcodeLine);
		return EMPTY_GCODE;
	}
}
