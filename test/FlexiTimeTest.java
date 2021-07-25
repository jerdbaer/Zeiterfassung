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
}
	
