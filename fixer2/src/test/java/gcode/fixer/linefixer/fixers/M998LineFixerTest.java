package gcode.fixer.linefixer.fixers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

public class M998LineFixerTest {
	private final M998LineFixer testee = new M998LineFixer();
	private final String nonApplicableGCode = "G00 X0 Y2 Z3";
	private final String M998_GCode_orig = "M998";
	
	@Test
	public void testspcApplies_NOT_M998_HappyPath() {
		assertFalse("Should not be handled since not M998 code", testee.spcAccepts(nonApplicableGCode));
	}
	
	@Test
	public void testspcApplies_M998_HappyPath() {
		assertTrue("Should be handled because it is for a M998 code", testee.spcAccepts(M998_GCode_orig));
	}

	@Test
	public void testM998GcodeModification_HappyPath() {
		Collection<String> result = testee.fix(M998_GCode_orig);
		assertNotNull("Returned collection should not be null", result);
		assertEquals("Returned collection should contain one line", 1, result.size());
		Iterator<String> it = result.iterator();
		assertEquals("G00 X0 Y0", it.next());
	}
}
