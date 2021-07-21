package josi.calculation;

import java.time.LocalTime;

public class TimeAtWork {

	LocalTime workStart;
	LocalTime workEnd;
	
	public TimeAtWork(LocalTime workStart, LocalTime workEnd) {
		this.workStart = workStart;
		this.workEnd = workEnd;
	}	
}
