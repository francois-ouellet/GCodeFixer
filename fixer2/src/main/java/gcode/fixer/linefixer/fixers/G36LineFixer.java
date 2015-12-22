package gcode.fixer.linefixer.fixers;

import gcode.fixer.linefixer.AbstractLineFixer;

import java.util.ArrayList;
import java.util.Collection;

public class G36LineFixer extends AbstractLineFixer {
	private static final double MAX_Z_HEIGHT = 30.0;
	
	@Override
	protected boolean spcAccepts(String gcodeLine) {
		return gcodeLine.startsWith("G36"); 
	}

	@Override
	protected Collection<String> spcFix(String gcodeLine) {
    	String tool = gcodeLine.substring(gcodeLine.indexOf("T"));
    	Collection<String> retval = new ArrayList<String>();
    	retval.add("G00 Z" + MAX_Z_HEIGHT + " F200.0");
    	retval.add("G00 X0 Y0 F200.0");
    	retval.add("M06 " + tool);
    	logReplacingGCode(gcodeLine, retval);
    	return retval;
	}
}

//Things that could be used to improve the change tool code but that does require a z probe
//outputLines.add("G01 X0 Y0 Z5 F300.0m");  // Move back to tool length probe position
//outputLines.add("M05");  // Stop spindle
//outputLines.add("M00");  // Stop machine
//outputLines.add("M03");  // Restart spindle
//outputLines.add("G38.2");  // touch probe plate
//outputLines.add("G43.1");  // inform grbl of offset

//http://www.shapeoko.com/forum/viewtopic.php?f=31&t=1426
//The tool change itself with tool length compensation works this way:
//	After M00 stopped the machine you can move it manually to any position you like so you can release the old tool... 
//	Don't worry about changing the position - the machine will later automatically return to the right position...
//	Insert the new tool...
//	Finally touch the Z-Axis off with the new tool against the same surface you used to touch off the old tool -> tool length is now compensated.
//	Continue the program...