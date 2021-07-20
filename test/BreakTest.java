package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import josi.calculation.Break;

class BreakTest {

	// for break duration calculation	
	@Test
	void breakFrom8To9_Is1() {
		Break break1 = new Break(LocalTime.of(8,0), LocalTime.of(9,0));
		LocalTime expectedBreak = LocalTime.of(1,0);
		
		LocalTime actualBreak = break1.calculateBreakDuration();
		
		assertEquals(expectedBreak, actualBreak);
	}
	
	@Test
	void breakFrom9To8_Is23() {
		Break break1 = new Break(LocalTime.of(9,0), LocalTime.of(8,0));
		LocalTime expectedBreak = LocalTime.of(23,0);
		
		LocalTime actualBreak = break1.calculateBreakDuration();
		
		assertEquals(expectedBreak, actualBreak);	
	}
	
	@Test
	void breakFrom1233To1452_Is219() {
		Break break1 = new Break(LocalTime.of(12,33), LocalTime.of(14,52));
		LocalTime expectedBreak = LocalTime.of(2,19);
		
		LocalTime actualBreak = break1.calculateBreakDuration();
		
		assertEquals(expectedBreak, actualBreak);	
	}
	
	
	// For legal break validation
	@Test
	void breakFrom8To815_BreakIs15_IsLegalCompliant() {
		Break break1 = new Break(LocalTime.of(8,0), LocalTime.of(8,15));
		
		boolean actuelLegalCompliant = break1.validateLegalCompliance();
		
		assertTrue(actuelLegalCompliant);
	}
	
	@Test
	void breakFrom8To814_BreakIs14_IsNotLegalCompliant() {
		Break break1 = new Break(LocalTime.of(8,0), LocalTime.of(8,14));
		
		boolean actuelLegalCompliant = break1.validateLegalCompliance();
		
		assertFalse(actuelLegalCompliant);
	}
	
	@Test
	void breakFrom8To8_BreakIs0_IsNotLegalCompliant() {
		Break break1 = new Break(LocalTime.of(8,0), LocalTime.of(8,0));
		
		boolean actuelLegalCompliant = break1.validateLegalCompliance();
		
		assertFalse(actuelLegalCompliant);
	}
	
	
}
