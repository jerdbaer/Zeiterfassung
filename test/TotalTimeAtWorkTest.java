package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import josi.calculation.TotalTimeAtWork;

class TotalTimeAtWorkTest {
	
	static final LocalTime TIME1 = LocalTime.of(1,0);
	static final LocalTime TIME2 = LocalTime.of(2,30);
	static final LocalTime TIME3 = LocalTime.of(3,15);
	
	@Test
	void totalTimeOfEmptyList_Is0(){
		ArrayList<LocalTime> list = new ArrayList<LocalTime>();
		TotalTimeAtWork total = new TotalTimeAtWork(list);
		LocalTime expectedTotalTime = LocalTime.of(0,0);
		
		LocalTime actualTotalTime = total.calculateTotalTimeAtWork();
		
		assertEquals(expectedTotalTime, actualTotalTime);
	}
	
	@Test
	void totalTimeOf1ItemWith1Hour_Is1(){
		ArrayList<LocalTime> list = new ArrayList<LocalTime>();
		list.add(TIME1);
		TotalTimeAtWork total = new TotalTimeAtWork(list);
		LocalTime expectedTotalTime = LocalTime.of(1,0);
		
		LocalTime actualTotalTime = total.calculateTotalTimeAtWork();
		
		assertEquals(expectedTotalTime, actualTotalTime);
	}
	
	@Test
	void totalTimeOf2ItemWith1And230_Is330(){
		ArrayList<LocalTime> list = new ArrayList<LocalTime>();
		list.add(TIME1);
		list.add(TIME2);
		TotalTimeAtWork total = new TotalTimeAtWork(list);
		LocalTime expectedTotalTime = LocalTime.of(3,30);
		
		LocalTime actualTotalTime = total.calculateTotalTimeAtWork();
		
		assertEquals(expectedTotalTime, actualTotalTime);
	}
	
	@Test
	void totalTimeOf5ItemWith1And230And315And1And315_Is11(){
		ArrayList<LocalTime> list = new ArrayList<LocalTime>();
		list.add(TIME1);
		list.add(TIME2);
		list.add(TIME3);
		list.add(TIME1);
		list.add(TIME3);
		TotalTimeAtWork total = new TotalTimeAtWork(list);
		LocalTime expectedTotalTime = LocalTime.of(11,0);
		
		LocalTime actualTotalTime = total.calculateTotalTimeAtWork();
		
		assertEquals(expectedTotalTime, actualTotalTime);
	}
	
	/*
	 * no need for further test due to restrictions for user input
	 * --> no values < 0 for LocalTime
	 * --> no values > 24 for LocalTime
	 */
}
