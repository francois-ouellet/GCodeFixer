package gcode.fixer.linefixer.fixers;

import java.util.ArrayList;
import java.util.Collection;

import gcode.fixer.linefixer.AbstractLineFixer;

public class G01LineFixer extends AbstractLineFixer {
	@Override
	protected boolean spcAccepts(String gcodeLine) {
		return gcodeLine.startsWith("G01"); 
	}

	@Override
	protected Collection<String> spcFix(String gcodeLine) {
		Collection<String> retval = new ArrayList<String>();
		
		gcodeLine = gcodeLine.replace("Z-0.10", "Z-1.20");
		
		retval.add(gcodeLine);
		return retval;
	}
}
