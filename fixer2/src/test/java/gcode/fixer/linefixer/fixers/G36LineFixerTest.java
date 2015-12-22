package gcode.fixer.linefixer.fixers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

public class G36LineFixerTest {
	private final G36LineFixer testee = new G36LineFixer();
	private final String nonApplicableGCode = "G00 X0 Y2 Z3";
	private final String G36_GCode_orig = "G36 T10";
	
	@Test
	public void testspcApplies_NOT_G36_HappyPath() {
		assertFalse("Should not be handled since not G36 code", testee.spcAccepts(nonApplicableGCode));
	}
	
	@Test
	public void testspcApplies_G36_HappyPath() {
		assertTrue("Should be handled because it is for a G36 code", testee.spcAccepts(G36_GCode_orig));
	}

	@Test
	public void testG36GcodeModification_HappyPath() {
		Collection<String> result = testee.fix(G36_GCode_orig);
		assertNotNull("Returned collection should not be null", result);
		assertEquals("Returned collection should contain three lines", 3, result.size());
		Iterator<String> it = result.iterator(); 
		assertEquals("G00 Z30.0 F200.0", it.next());
		assertEquals("G00 X0 Y0 F200.0", it.next());
		assertEquals("M06 T10", it.next());
	}
}
