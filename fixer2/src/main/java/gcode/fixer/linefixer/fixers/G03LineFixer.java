package gcode.fixer.linefixer.fixers;

import java.util.Collection;

import gcode.fixer.linefixer.AbstractLineFixer;

public class G03LineFixer extends AbstractLineFixer {
	@Override
	protected boolean spcAccepts(String gcodeLine) {
		return gcodeLine.startsWith("G03"); 
	}

	@Override
	public Collection<String> spcFix(String gcodeLine) {
		String retval = gcodeLine.replace("X", "I")
				        		 .replace("Y", "J")
				        		 .replace("Z", "K");
		
		logReplacingGCode(gcodeLine, retval);
		return java.util.Arrays.asList(new String[]{retval});
	}
}
