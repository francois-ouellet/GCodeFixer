package gcode.fixer.linefixer.fixers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

public class G03LineFixerTest {
	private final G03LineFixer testee = new G03LineFixer();
	private final String nonApplicableGCode = "G00 X0 Y2 Z3";
	private final String G03_GCode_orig = "G03 X0.0939 Y0.0939 Z0.0179 R0.0939";
	private final String G03_GCode_fixed = "G03 I0.0939 J0.0939 K0.0179 R0.0939";
	
	@Test
	public void testspcApplies_NOT_G03_HappyPath() {
		assertFalse("Should not be handled since not G03 code", testee.spcAccepts(nonApplicableGCode));
	}
	
	@Test
	public void testspcApplies_G03_HappyPath() {
		assertTrue("Should be handled because it is for a G03 code", testee.spcAccepts(G03_GCode_orig));
	}

	@Test
	public void testG03GcodeModification_HappyPath() {
		Collection<String> result = testee.fix(G03_GCode_orig);
		assertNotNull("Returned collection should not be null", result);
		assertEquals("Returned collection should contain only one line", 1, result.size());
		assertTrue(G03_GCode_fixed.equals(result.iterator().next()));
	}
}
