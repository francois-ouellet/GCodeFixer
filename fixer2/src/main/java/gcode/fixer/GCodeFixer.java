package gcode.fixer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GCodeFixer {
	public List<String> fixGCode(List<String> gcodeInputLines) throws IOException {
		final List<String> gcodeOutputLines = new ArrayList<String>();
		
		for(String gcodeInputLine : gcodeInputLines) {
		    if(gcodeInputLine.startsWith("M48") ||  // Feed rate overwrite allowed
			   gcodeInputLine.startsWith("M71") ||  // Pause activity and display message, resuming build on button push. Optional timeout specified by P-code in seconds. If timeout is specified and no button is pushed, machine should shut down or reset.
			   gcodeInputLine.startsWith("M74") ||  // ERROR DETECT ON / Progressive Move  "Canned-Cycle".
			   gcodeInputLine.startsWith("M999")||  // Arc (Canned Cycle) / Automatic tool compensation X
			   gcodeInputLine.startsWith("#")) {    // no clue why this is there
		    	System.out.println("Completely removed the following line: " + gcodeInputLine);
			}
		    else if(gcodeInputLine.startsWith("G03")) {  // Arc - Counter Clockwise
		    	gcodeInputLine = gcodeInputLine.replace("X", "I");
		    	gcodeInputLine = gcodeInputLine.replace("Y", "J");
		    	gcodeInputLine = gcodeInputLine.replace("Z", "K");
		    	gcodeOutputLines.add(gcodeInputLine);
		    	System.out.println("Fixed G03 entry to use (I,J,K) instead of (X,Y,Z): " + gcodeInputLine);
		    }
		    else if(gcodeInputLine.startsWith("M30")) {  // Program end code
		    	gcodeOutputLines.add("G00 X0 Y0");
		    	gcodeOutputLines.add(gcodeInputLine);
		    	System.out.println("Replaced M30 code with G00 X0 Y0");
		    }
		    else if(gcodeInputLine.startsWith("G36")) {
		    	String tool = gcodeInputLine.substring(gcodeInputLine.indexOf("T"));
		    	gcodeOutputLines.add("M06 " + tool);
		    	System.out.println("Replacing G36 with M06 (Change tool) operation: M06 " + tool);
		    }
		    else if(gcodeInputLine.startsWith("M998")) {  // GOTO TOOLCHANGE POSITION
		    	gcodeOutputLines.add("G00 X0 Y0");
		    	System.out.println("Replaced M998 (Goto tool change) with G00 X0 Y0");
		    } 
			else {
				gcodeOutputLines.add(gcodeInputLine);  // Keep line as is
			}
		}
		
		fixFeedRate(gcodeOutputLines);
		return gcodeOutputLines;
	}

	/**
	 * Finds the first G00/G01 GCode command and adds a feed rate to it
	 * 
	 * @param gcodeLines
	 */
	private void fixFeedRate(List<String> gcodeLines) {
		for(String line : gcodeLines) {
			if(line.startsWith("G00") || line.startsWith("G01")) {
				if(!line.contains("F")) {
					int index = gcodeLines.indexOf(line);
					line = line + " F300.0";
					gcodeLines.set(index, line);
					break;
				}
			}
		}
	}
}



// Things that could be used to improve the change tool code but that does require a z probe
//		    	outputLines.add("G01 X0 Y0 Z5 F300.0m");  // Move back to tool length probe position
//		    	outputLines.add("M05");  // Stop spindle
//		    	outputLines.add("M00");  // Stop machine
//		    	outputLines.add("M03");  // Restart spindle
//		    	outputLines.add("G38.2");  // touch probe plate
//		    	outputLines.add("G43.1");  // inform grbl of offset
		    	
//		    	http://www.shapeoko.com/forum/viewtopic.php?f=31&t=1426
//		    	The tool change itself with tool length compensation works this way:
//		    		After M00 stopped the machine you can move it manually to any position you like so you can release the old tool... 
//		    		Don't worry about changing the position - the machine will later automatically return to the right position...
//		    		Insert the new tool...
//		    		Finally touch the Z-Axis off with the new tool against the same surface you used to touch off the old tool -> tool length is now compensated.
//		    		Continue the program...
