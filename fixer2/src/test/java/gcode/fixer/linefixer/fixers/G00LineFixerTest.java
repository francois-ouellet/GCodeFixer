package gcode.fixer.linefixer.fixers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

public class G00LineFixerTest {
	private final G00LineFixer testee = new G00LineFixer();
	private final String nonApplicableGCode = "M71";
	private final String G00_GCode_orig = "G00 X0 Y0 Z00.10";
	private final String G00_GCode_fixed = "G00 X0 Y0 Z01.20 F200.0";
	
	@Test
	public void testspcApplies_NOT_G00_HappyPath() {
		assertFalse("Should not be handled since not G00 code", testee.spcAccepts(nonApplicableGCode));
	}
	
	@Test
	public void testspcApplies_G00_HappyPath() {
		assertTrue("Should be handled because it is for a G00 code", testee.spcAccepts(G00_GCode_orig));
	}

	@Test
	public void testG00GcodeModification_HappyPath() {
		testee.setFeedRate(200.0f);
		Collection<String> result = testee.fix(G00_GCode_orig);
		assertNotNull("Returned collection should not be null", result);
		assertEquals("Returned collection should contain only one line", 1, result.size());
		assertTrue(G00_GCode_fixed.equals(result.iterator().next()));
	}
}
