package gcode.fixer.linefixer.fixers;

import gcode.fixer.linefixer.AbstractLineFixer;

import java.util.ArrayList;
import java.util.Collection;

public class G00LineFixer extends AbstractLineFixer {
	private float feedRate = 0;
	
	@Override
	protected boolean spcAccepts(String gcodeLine) {
		return gcodeLine.startsWith("G00"); 
	}

	@Override
	protected Collection<String> spcFix(String gcodeLine) {
		Collection<String> retval = new ArrayList<String>();
		
		if (!gcodeLine.contains("F")) {
			gcodeLine += " F" + feedRate;
		}
		
		gcodeLine = gcodeLine.replace("Z00.10", "Z01.20");
		
		retval.add(gcodeLine);
		return retval;
	}

	public float getFeedRate() {
		return feedRate;
	}

	public void setFeedRate(float feedRate) {
		this.feedRate = feedRate;
	}
}
