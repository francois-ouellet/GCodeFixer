package gcode.fixer.linefixer.fixers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

public class PoundSymbolLineFixerTest {
	private final PoundSymbolLineFixer testee = new PoundSymbolLineFixer();
	private final String nonApplicableGCode = "G00 X0 Y2 Z3";
	private final String PoundSymbol_GCode = "#5522";
	
	@Test
	public void testspcApplies_NOT_PoundSymbol_HappyPath() {
		assertFalse("Should not be handled since not # code", testee.spcAccepts(nonApplicableGCode));
	}
	
	@Test
	public void testspcApplies_PoundSymbol_HappyPath() {
		assertTrue("Should be handled because it is for a # code", testee.spcAccepts(PoundSymbol_GCode));
	}

	@Test
	public void testPoundSymbolGcodeModification_HappyPath() {
		Collection<String> result = testee.fix(PoundSymbol_GCode);
		assertNotNull("Returned collection should not be null", result);
		assertTrue("Returned collection should be empty", result.isEmpty());
	}
}
