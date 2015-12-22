package gcode.fixer.file;

import java.util.ArrayList;
import java.util.List;


public class CutFile extends AbstractFile {
	
	public CutFile(String fileNameAndPath) {
		super(fileNameAndPath);
	}
	
	@Override
	protected List<String> filter(List<String> gcodeToFilter) {
		// Copy everything from the 2nd tool section until we reach the M999 code
		List<String> retval = new ArrayList<String>();
		
		int M999Index = gcodeToFilter.size() - 1;
		
		// Search for the 2nd occurrence of M999 from the end of the file
		int M999FoundCount =  0;
		while(M999FoundCount != 2 && M999Index >= 0) {
			String gcodeLine = gcodeToFilter.get(M999Index);
			if(gcodeLine.startsWith("M999")) {
				M999FoundCount++; 
			}
			M999Index--;
		}
		
		for(int i = M999Index+2; i<gcodeToFilter.size(); i++) {
			retval.add(gcodeToFilter.get(i));
		}

		return retval;
	}

	@Override
	protected float getFeedRate() {
		return 200.0f;
	}
}
