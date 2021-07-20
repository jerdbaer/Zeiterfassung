package josi.calculation;

import java.time.LocalTime;

public class TimeAtWork {

	LocalTime workStart;
	LocalTime workEnd;
	LocalTime timeAtWork;
	LocalTime tempTimeAtWork;
	
	public TimeAtWork(LocalTime workStart, LocalTime workEnd) {
		this.workStart = workStart;
		this.workEnd = workEnd;
		this.timeAtWork = calculateTimeAtWork();
	}
	
	public LocalTime calculateTimeAtWork() {
		tempTimeAtWork = workEnd.minusHours(workStart.getHour());
		timeAtWork = tempTimeAtWork.minusMinutes(workStart.getMinute());
		return timeAtWork;
	}
}
