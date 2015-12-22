package gcode.fixer.linefixer.fixers;

import gcode.fixer.linefixer.AbstractLineFixer;

import java.util.Arrays;
import java.util.Collection;

public class M998LineFixer extends AbstractLineFixer {
	@Override
	protected boolean spcAccepts(String gcodeLine) {
		return gcodeLine.startsWith("M998"); 
	}

	@Override
	public Collection<String> spcFix(String gcodeLine) {
		String retval = "G00 X0 Y0";
		super.logReplacingGCode(gcodeLine, retval);
		return Arrays.asList(new String[]{retval});
	}
}
