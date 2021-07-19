package josi.calculation;

import java.time.Duration;
import java.time.LocalTime;

public class FlexiTime {
	
	LocalTime plannedWorkingTime;
	LocalTime actualWorkingTime;
	Duration flexiTime;
	
	Duration flexiTimeAccount;
	
	public FlexiTime(LocalTime plannedWorkingTime, LocalTime actualWorkiTime) {
		this.plannedWorkingTime = plannedWorkingTime;
		this.actualWorkingTime = actualWorkiTime;
		this.flexiTime = calculateFlexiTime();
	}
	
	public Duration calculateFlexiTime() {
		
		if (plannedWorkingTime.compareTo(actualWorkingTime) == 0) {
			return Duration.ZERO;
		} else {
			return Duration.between(plannedWorkingTime, actualWorkingTime);
		}
	}
	
	public Duration updateFlexiTimeAccount(Duration currentFlexiTimeAccount) {
		return currentFlexiTimeAccount.plus(flexiTime);
	}

}
