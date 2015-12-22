package gcode.fixer.linefixer.fixers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

public class M71LineFixerTest {
	private final M71LineFixer testee = new M71LineFixer();
	private final String nonApplicableGCode = "G00 X0 Y2 Z3";
	private final String M71_GCode = "M71";
	
	@Test
	public void testspcApplies_NOT_M71_HappyPath() {
		assertFalse("Should not be handled since not M71 code", testee.spcAccepts(nonApplicableGCode));
	}
	
	@Test
	public void testspcApplies_M71_HappyPath() {
		assertTrue("Should be handled because it is for a M71 code", testee.spcAccepts(M71_GCode));
	}

	@Test
	public void testM71GcodeModification_HappyPath() {
		Collection<String> result = testee.fix(M71_GCode);
		assertNotNull("Returned collection should not be null", result);
		assertTrue("Returned collection should be empty", result.isEmpty());
	}
}
