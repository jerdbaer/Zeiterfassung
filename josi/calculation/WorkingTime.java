package josi.calculation;

import java.time.LocalTime;

public class WorkingTime {
	
	TotalTimeAtWork totalTimeAtWork;
//	TotalBreak totalBreak;
	
	private LocalTime workingTime;
	private LocalTime tempWorkingTime;
	
	public LocalTime calculateWorkingTime(LocalTime totalTimeAtWork, LocalTime totalBreak) {
		
		if (totalTimeAtWork.compareTo(totalBreak) == 0 || totalTimeAtWork.compareTo(totalBreak) < 0)
			return workingTime = LocalTime.of(0,0);
		else {
			tempWorkingTime = totalTimeAtWork.minusHours(totalBreak.getHour());
			workingTime = tempWorkingTime.minusMinutes(totalBreak.getMinute());
			return workingTime;
		}		
	}
	
}
