package gcode.fixer.linefixer.fixers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

public class M30LineFixerTest {
	private final M30LineFixer testee = new M30LineFixer();
	private final String nonApplicableGCode = "G00 X0 Y2 Z3";
	private final String M30_GCode_orig = "M30";
	
	@Test
	public void testspcApplies_NOT_M30_HappyPath() {
		assertFalse("Should not be handled since not M30 code", testee.spcAccepts(nonApplicableGCode));
	}
	
	@Test
	public void testspcApplies_M30_HappyPath() {
		assertTrue("Should be handled because it is for a M30 code", testee.spcAccepts(M30_GCode_orig));
	}

	@Test
	public void testM30GcodeModification_HappyPath() {
		Collection<String> result = testee.fix(M30_GCode_orig);
		assertNotNull("Returned collection should not be null", result);
		assertTrue("Returned collection should be empty", result.isEmpty());
	}
}
