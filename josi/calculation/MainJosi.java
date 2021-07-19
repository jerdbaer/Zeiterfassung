package josi.calculation;

import java.time.LocalTime;

public class MainJosi {

	public static void main(String[] args) {

		// -------------
		// WorkingTime
		// -------------

//		LocalTime timeAtWork = LocalTime.of(9, 30);
//		LocalTime totalBreak = LocalTime.of(0,55);
//		
//		WorkingTime workingTimeToday = new WorkingTime();
//		
//		System.out.println(workingTimeToday.calculateWorkingTime(timeAtWork, totalBreak));
//
//		System.out.println("TotalTime: " + timeAtWork);
//		System.out.println("TotalBreak: " + totalBreak);

		// -------------
		// LegalBreak
		// -------------

//		LocalTime total_6_00 = LocalTime.of(6,0);
//		LocalTime total_5_59 = LocalTime.of(5,59);
//		LocalTime total_6_01 = LocalTime.of(6,1);
//		
//		LocalTime total_9_00 = LocalTime.of(9,0);
//		LocalTime total_8_59 = LocalTime.of(8,59);
//		LocalTime total_9_01 = LocalTime.of(9,1);
//		
//		LocalTime total_7_30 = LocalTime.of(7,30);
//		
//		
//		
//		System.out.println("totaltime: " + total_6_00 + " --> Pause: " + calculateLegalBreak(total_6_00));
//		System.out.println("totaltime: " + total_5_59 + " --> Pause: " + calculateLegalBreak(total_5_59));
//		System.out.println("totaltime: " + total_6_01 + " --> Pause: " + calculateLegalBreak(total_6_01));
//		
//		System.out.println("totaltime: " + total_9_00 + " --> Pause: " + calculateLegalBreak(total_9_00));
//		System.out.println("totaltime: " + total_8_59 + " --> Pause: " + calculateLegalBreak(total_8_59));
//		System.out.println("totaltime: " + total_9_01 + " --> Pause: " + calculateLegalBreak(total_9_01));
//		
//		System.out.println("totaltime: " + total_7_30 + " --> Pause: " + calculateLegalBreak(total_7_30));
//		
//		
		// -------------
		// LegalBreak
		// -------------
		
		FlexiTime test = new FlexiTime(LocalTime.of(8,0), LocalTime.of(8,0));
		if (test.isOvertime == null) {
			System.out.println("Planned: "+test.plannedWorkingTime + "Actual: " + test.actualWorkingTime + "FlexiTime: " + test.flexiTime);
		}
		else if (test.isOvertime == true) {
			System.out.println("Überstunden " + test.flexiTime);
		} else if (test.isOvertime == false) {
			System.out.println("Minusstunden: " + test.flexiTime);
		}	
		
		System.out.println(test.getIsOvertime());

	}

	public static LocalTime calculateLegalBreak(LocalTime totalTimeAtWork) {

		LocalTime legalBreak;

		final LocalTime TOTAL_TIME_OVER_6HOURS = LocalTime.of(6, 0);
		final LocalTime TOTAL_TIME_OVER_9HOURS = LocalTime.of(9, 0);

		if (totalTimeAtWork.compareTo(TOTAL_TIME_OVER_6HOURS) == 0
				|| totalTimeAtWork.compareTo(TOTAL_TIME_OVER_6HOURS) > 0
						&& totalTimeAtWork.compareTo(TOTAL_TIME_OVER_9HOURS) < 0) {
			return legalBreak = LocalTime.of(0, 30);
		} else if (totalTimeAtWork.compareTo(TOTAL_TIME_OVER_9HOURS) == 0
				|| totalTimeAtWork.compareTo(TOTAL_TIME_OVER_9HOURS) > 0) {
			return legalBreak = LocalTime.of(0, 45);
		} else {
			return legalBreak = LocalTime.of(0, 0);
		}
	}
}