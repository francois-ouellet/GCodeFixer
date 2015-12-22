package gcode.fixer.linefixer.fixers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

public class M48LineFixerTest {
	private final M48LineFixer testee = new M48LineFixer();
	private final String nonApplicableGCode = "G00 X0 Y2 Z3";
	private final String M48_GCode = "M48";
	
	@Test
	public void testspcApplies_NOT_M48_HappyPath() {
		assertFalse("Should not be handled since not M48 code", testee.spcAccepts(nonApplicableGCode));
	}
	
	@Test
	public void testspcApplies_M48_HappyPath() {
		assertTrue("Should be handled because it is for a M48 code", testee.spcAccepts(M48_GCode));
	}

	@Test
	public void testM48GcodeModification_HappyPath() {
		Collection<String> result = testee.fix(M48_GCode);
		assertNotNull("Returned collection should not be null", result);
		assertTrue("Returned collection should be empty", result.isEmpty());
	}
}
