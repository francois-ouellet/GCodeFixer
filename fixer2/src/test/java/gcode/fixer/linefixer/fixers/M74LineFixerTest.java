package gcode.fixer.linefixer.fixers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

public class M74LineFixerTest {
	private final M74LineFixer testee = new M74LineFixer();
	private final String nonApplicableGCode = "G00 X0 Y2 Z3";
	private final String M74_GCode = "M74";
	
	@Test
	public void testspcApplies_NOT_M74_HappyPath() {
		assertFalse("Should not be handled since not M74 code", testee.spcAccepts(nonApplicableGCode));
	}
	
	@Test
	public void testspcApplies_M74_HappyPath() {
		assertTrue("Should be handled because it is for a M74 code", testee.spcAccepts(M74_GCode));
	}

	@Test
	public void testM74GcodeModification_HappyPath() {
		Collection<String> result = testee.fix(M74_GCode);
		assertNotNull("Returned collection should not be null", result);
		assertTrue("Returned collection should be empty", result.isEmpty());
	}
}
