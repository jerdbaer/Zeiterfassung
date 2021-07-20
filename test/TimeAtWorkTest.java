package test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import josi.calculation.TimeAtWork;

class TimeAtWorkTest {

	
	@Test
	void workFrom8To8_Is0() {
		TimeAtWork timeAtWork = new TimeAtWork(LocalTime.of(8,0), LocalTime.of(8,00));
		LocalTime expectetTimeAtWork = LocalTime.of(0,0);
		
		LocalTime actualTimeAtWork = timeAtWork.calculateTimeAtWork();
		
		assertEquals(expectetTimeAtWork, actualTimeAtWork);
	}
	
	@Test
	void workFrom8To16_Is800() {
		TimeAtWork timeAtWork = new TimeAtWork(LocalTime.of(8,0), LocalTime.of(16,00));
		LocalTime expectetTimeAtWork = LocalTime.of(8,0);
		
		LocalTime actualTimeAtWork = timeAtWork.calculateTimeAtWork();
		
		assertEquals(expectetTimeAtWork, actualTimeAtWork);
	}
	
	@Test
	void workFrom0To2359_Is2359() {
		TimeAtWork timeAtWork = new TimeAtWork(LocalTime.of(0,0), LocalTime.of(23,59));
		LocalTime expectetTimeAtWork = LocalTime.of(23,59);
		
		LocalTime actualTimeAtWork = timeAtWork.calculateTimeAtWork();
		
		assertEquals(expectetTimeAtWork, actualTimeAtWork);
	}
	
	@Test
	void workFrom16To8_Is16() {
		TimeAtWork timeAtWork = new TimeAtWork(LocalTime.of(16,0), LocalTime.of(8,0));
		LocalTime expectetTimeAtWork = LocalTime.of(16,0);
		
		LocalTime actualTimeAtWork = timeAtWork.calculateTimeAtWork();
		
		assertEquals(expectetTimeAtWork, actualTimeAtWork);
	}
}
