package gcode.fixer.file;

import java.util.ArrayList;
import java.util.List;


public class DrillFile extends AbstractFile {
	
	public DrillFile(String fileNameAndPath) {
		super(fileNameAndPath);
	}

	@Override
	protected List<String> filter(List<String> gcodeToFilter) {
		// Copy everything until we reach a new tool section
		List<String> retval = new ArrayList<String>();
		
		int percentCount = 0;
		for(String gcodeLine : gcodeToFilter) {
			if(gcodeLine.startsWith("%")) {
				percentCount++;
				
				// The 3rd time we see that symbol is because we reached the 2nd tools section of the file.
				if(percentCount >= 3) {
					break;
				}
			}
			retval.add(gcodeLine);
		}
		return retval;
	}

	@Override
	protected float getFeedRate() {
		return 200.0f;
	}
}
