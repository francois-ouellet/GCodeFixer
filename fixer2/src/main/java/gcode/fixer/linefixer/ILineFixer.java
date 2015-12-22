package gcode.fixer.linefixer;

import java.util.Collection;

public interface ILineFixer {
	/**
	 * Allows the fixer to indicate if he needs to fix that GCode line.
	 * 
	 * @param gcodeLine GCode to check against
	 * 
	 * @return <code>true</code> if this fixer handles that GCode, <code>false</code> otherwise.
	 */
	boolean accepts(String gcodeLine);
	
	/**
	 * Allows the fixer to fix that GCode.
	 * 
	 * @param gcodeLine GCode to be fixed
	 * 
	 * @return Fixed GCode
	 */
	Collection<String> fix(String gcodeLine);
}
