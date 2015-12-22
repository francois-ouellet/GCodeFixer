package gcode.fixer.file;

import java.util.ArrayList;
import java.util.List;


public class TracesFile extends AbstractFile {
	
	public TracesFile(String fileNameAndPath) {
		super(fileNameAndPath);
	}

	@Override
	protected List<String> filter(List<String> gcodeToFilter) {
		// Copy everything from the 2nd tool section until we reach the M999 code
		List<String> retval = new ArrayList<String>();
		
		int percentCount = 0;
		boolean copying = false;
		for(String gcodeLine : gcodeToFilter) {
			// Skip everything until we reach the 2nd tool section
			if(!copying && gcodeLine.startsWith("%")) {
				percentCount++;
				
				// The 3rd time we see that symbol is because we reached the 2nd tools section of the file.
				if(percentCount >= 3) {
					copying = true;
				}
			}
			// If we have already reached the 2nd tool section, copy until we find a M999 code
			if(copying) {
				if(!gcodeLine.startsWith("M999")) {
					retval.add(gcodeLine);
				}
				else {
					break;
				}
			}
		}

		return retval;
	}

	@Override
	protected float getFeedRate() {
		return 200.0f;
	}
}
