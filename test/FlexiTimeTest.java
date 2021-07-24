package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import josi.calculation.FlexiTime;

class FlexiTimeTest {
	
//	private static final Duration TIME_DATA_ZERO = Duration.ZERO;
//	private static final Duration TIME_DATA_PLUS = Duration.parse("P2DT3H4M");
//	private static final Duration TIME_DATA_MINUS = Duration.parse("P2DT3H4M").negated();
//	
//	private static final Duration TIME_UPDATE_ZERO = Duration.ZERO;
//	private static final Duration TIME_UPDATE_PLUS = Duration.parse("PT3H15M");
//	private static final Duration TIME_UPDATE_MINUS = Duration.parse("PT1H30M").negated();
//	
//	private static final LocalTime TIME = LocalTime.of(0,0);
	
	
	// calculateFlexiTime()-Tests
	@Test
	void planned0AndWorking0_FlexitimeIs0() {
		FlexiTime time = new FlexiTime(LocalTime.of(0,0), LocalTime.of(0,0));
		Duration expectedFlexiTime = Duration.ZERO;
		
		Duration actualFlexiTime = time.calculateFlexiTime();
		
		assertEquals(expectedFlexiTime, actualFlexiTime);
	}
	
	
	@Test
	void planned8AndWorking0_FlexitimeIsMinus8() {
		FlexiTime time = new FlexiTime(LocalTime.of(8,0), LocalTime.of(0,0));
		Duration expectedFlexiTime = Duration.parse("PT8H").negated();
		
		Duration actualFlexiTime = time.calculateFlexiTime();
		
		assertEquals(expectedFlexiTime,actualFlexiTime);
	}
	
	@Test
	void planned0AndWorking8_FlexitimeIsPlus8() {
		FlexiTime time = new FlexiTime(LocalTime.of(0,0), LocalTime.of(8,0));
		Duration expectedFlexiTime = Duration.parse("PT8H");
		
		Duration actualFlexiTime = time.calculateFlexiTime();
		
		assertEquals(expectedFlexiTime,actualFlexiTime);
	}
	
	@Test
	void planned8AndWorked724_FlexitimeIsMinus36() {
		FlexiTime time = new FlexiTime(LocalTime.of(8,0), LocalTime.of(7,24));
		Duration expectedFlexiTime = Duration.parse("PT36M").negated();
		
		Duration actualFlexiTime = time.calculateFlexiTime();
		
		assertEquals(expectedFlexiTime,actualFlexiTime);
	}
	
	@Test
	void planned430AndWorked724_FlexitimeIsMinus36() {
		FlexiTime time = new FlexiTime(LocalTime.of(4,30), LocalTime.of(9,15));
		Duration expectedFlexiTime = Duration.parse("PT4H45M");
		
		Duration actualFlexiTime = time.calculateFlexiTime();
		
		assertEquals(expectedFlexiTime,actualFlexiTime);
	}
	
	// updateFlexiTime()-Test
//	@Test
//	void timeDatabase0AndTimeUpdate0_Is0() {
//		FlexiTime time = new FlexiTime(TIME,TIME);
//		Duration expectedFlexiTimeAccount = Duration.ZERO;
//		
//		Duration actualFlexiTimeAccount = time.updateFlexiTimeAccount(TIME_UPDATE_ZERO);
//		
//		assertEquals(expectedFlexiTimeAccount, actualFlexiTimeAccount);
//	}
	
	
}
