package gcode.fixer.linefixer.fixers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

public class M999LineFixerTest {
	private final M999LineFixer testee = new M999LineFixer();
	private final String nonApplicableGCode = "G00 X0 Y2 Z3";
	private final String M999_GCode = "M999 Gesamt 0 ; Holes";
	
	@Test
	public void testspcApplies_NOT_M999_HappyPath() {
		assertFalse("Should not be handled since not M999 code", testee.spcAccepts(nonApplicableGCode));
	}
	
	@Test
	public void testspcApplies_M999_HappyPath() {
		assertTrue("Should be handled because it is for a M999 code", testee.spcAccepts(M999_GCode));
	}

	@Test
	public void testM999GcodeModification_HappyPath() {
		Collection<String> result = testee.fix(M999_GCode);
		assertNotNull("Returned collection should not be null", result);
		assertTrue("Returned collection should be empty", result.isEmpty());
	}
}
