package gcode.fixer.linefixer.fixers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

public class G01LineFixerTest {
	private final G01LineFixer testee = new G01LineFixer();
	private final String nonApplicableGCode = "M71";
	private final String G01_GCode_orig = "G01 X0 Y0 Z-0.10";
	private final String G01_GCode_fixed = "G01 X0 Y0 Z-1.20";
	
	@Test
	public void testspcApplies_NOT_G01_HappyPath() {
		assertFalse("Should not be handled since not G01 code", testee.spcAccepts(nonApplicableGCode));
	}
	
	@Test
	public void testspcApplies_G01_HappyPath() {
		assertTrue("Should be handled because it is for a G01 code", testee.spcAccepts(G01_GCode_orig));
	}

	@Test
	public void testG01GcodeModification_HappyPath() {
		Collection<String> result = testee.fix(G01_GCode_orig);
		assertNotNull("Returned collection should not be null", result);
		assertEquals("Returned collection should contain only one line", 1, result.size());
		assertTrue(G01_GCode_fixed.equals(result.iterator().next()));
	}
}
