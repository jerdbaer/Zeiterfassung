//package test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.time.LocalTime;
//
//import org.junit.jupiter.api.Test;
//
//import josi.calculation.WorkingTime;
//
//class WorkingTimeTest {
//
//	@Test
//	void totalTimeIs0AndTotalBreakIs0_WorkingTimeIs0() {
//		WorkingTime time = new WorkingTime();
//		LocalTime expectedWorkingTime = LocalTime.of(0,0);
//		
//		LocalTime actualWorkingTime = time.calculateWorkingTime(LocalTime.of(0,0), LocalTime.of(0,0));
//		
//		assertEquals(expectedWorkingTime,actualWorkingTime);
//	}
//
//	@Test
//	void totalTimeIs0AndTotalBreakIs1_WorkingTimeIs0() {
//		WorkingTime time = new WorkingTime();
//		LocalTime expectedWorkingTime = LocalTime.of(0,0);
//		
//		LocalTime actualWorkingTime = time.calculateWorkingTime(LocalTime.of(0,0), LocalTime.of(1,0));
//		
//		assertEquals(expectedWorkingTime,actualWorkingTime);
//	}
//	
//	@Test
//	void totalTimeIs830AndTotalBreakIs13_WorkingTimeIs0() {
//		WorkingTime time = new WorkingTime();
//		LocalTime expectedWorkingTime = LocalTime.of(0,0);
//		
//		LocalTime actualWorkingTime = time.calculateWorkingTime(LocalTime.of(8,30), LocalTime.of(13,0));
//		
//		assertEquals(expectedWorkingTime,actualWorkingTime);
//	}
//	
//	@Test
//	void totalTimeIs1AndTotalBreakIs1_WorkingTimeIs0() {
//		WorkingTime time = new WorkingTime();
//		LocalTime expectedWorkingTime = LocalTime.of(0,0);
//		
//		LocalTime actualWorkingTime = time.calculateWorkingTime(LocalTime.of(1,0), LocalTime.of(1,0));
//		
//		assertEquals(expectedWorkingTime,actualWorkingTime);
//	}
//	
//	@Test
//	void totalTimeIs1AndTotalBreakIs0_WorkingTimeIs1() {
//		WorkingTime time = new WorkingTime();
//		LocalTime expectedWorkingTime = LocalTime.of(1,0);
//		
//		LocalTime actualWorkingTime = time.calculateWorkingTime(LocalTime.of(1,0), LocalTime.of(0,0));
//		
//		assertEquals(expectedWorkingTime,actualWorkingTime);
//	}
//	
//	@Test
//	void totalTimeIs915AndTotalBreakIs130_WorkingTimeIs745() {
//		WorkingTime time = new WorkingTime();
//		LocalTime expectedWorkingTime = LocalTime.of(7,45);
//		
//		LocalTime actualWorkingTime = time.calculateWorkingTime(LocalTime.of(9,15), LocalTime.of(1,30));
//		
//		assertEquals(expectedWorkingTime,actualWorkingTime);
//	}
//}
