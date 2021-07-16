package josi.calculation;

import java.time.LocalTime;
import java.util.ArrayList;


public class TotalTimeAtWork {
	
	ArrayList<LocalTime> timeAtworkList;
	TimeAtWork timeAtWork;
	LocalTime totalTimeAtWork;
	LocalTime tempTotalTimeAtWork;
	
	public TotalTimeAtWork(ArrayList<LocalTime> timeAtworkList) {
		this.timeAtworkList = timeAtworkList;
		this.totalTimeAtWork = calculateTotalTimeAtWork();
	}
	
	public LocalTime calculateTotalTimeAtWork() {
		totalTimeAtWork = LocalTime.of(0,0);
			
		for (LocalTime times : timeAtworkList) 
		{
			tempTotalTimeAtWork = totalTimeAtWork.plusHours(times.getHour());
			totalTimeAtWork = tempTotalTimeAtWork.plusMinutes(times.getMinute());
		}
		return totalTimeAtWork;
	}
		
		
		
	

}
