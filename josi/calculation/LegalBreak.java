package josi.calculation;

import java.time.LocalTime;
import java.util.List;

public class LegalBreak {
	
	TotalTimeAtWork totalTimeAtWork;
	
	LocalTime legalBreak;
	
	private static final LocalTime TOTAL_TIME_OVER_6HOURS = LocalTime.of(6,0);
	private static final LocalTime TOTAL_TIME_OVER_9HOURS = LocalTime.of(9,0);
	
	
	public LocalTime calculateLegalBreak(LocalTime totalTimeAtWork) {
		if (totalTimeAtWork.compareTo(TOTAL_TIME_OVER_6HOURS) == 0 || 
				totalTimeAtWork.compareTo(TOTAL_TIME_OVER_6HOURS) > 0 &&
				totalTimeAtWork.compareTo(TOTAL_TIME_OVER_9HOURS) < 0) {
			return legalBreak = LocalTime.of(0,30);
		} else if (totalTimeAtWork.compareTo(TOTAL_TIME_OVER_9HOURS) == 0 ||
				totalTimeAtWork.compareTo(TOTAL_TIME_OVER_9HOURS) > 0)
		{
			return legalBreak = LocalTime.of(0,45);
		} 
		else 
		{
			return legalBreak = LocalTime.of(0,0);
		}
		
		
	}
	
	
//	public static void main(String[] args) 
//	{
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
//		System.out.println("totaltime: " + total_6_00 + " --> Pause: " + calculateLegalBreak(total_6_00));
//		System.out.println("totaltime: " + total_5_59 + " --> Pause: " + calculateLegalBreak(total_5_59));
//		System.out.println("totaltime: " + total_6_01 + " --> Pause: " + calculateLegalBreak(total_6_01));
//		
//		System.out.println("totaltime: " + total_9_00 + " --> Pause: " + calculateLegalBreak(total_9_00));
//		System.out.println("totaltime: " + total_8_59 + " --> Pause: " + calculateLegalBreak(total_8_59));
//		System.out.println("totaltime: " + total_9_01 + " --> Pause: " + calculateLegalBreak(total_9_01));
//		
//		System.out.println("totaltime: " + total_7_30 + " --> Pause: " + calculateLegalBreak(total_7_30));		
//	}
	
	
	
	

	
	
	
	public double getSum_Breaktime_in_h(List<Double> all_Breaks_in_h)
	{
		double sum = 0;
		for(double rest: all_Breaks_in_h)
			sum += rest;
		return sum;
	}

	// fertig Berechnung & getestet
//	public double getBreaktime_min_in_h(double worktime_in_h)
//	{
//		double breaktime_min_in_h = 0;
//		if(worktime_in_h > 6)
//			breaktime_min_in_h += 0.5;
//		if (worktime_in_h > 9)
//			breaktime_min_in_h += 0.25;
//		return breaktime_min_in_h;
//
//	}
	
	// in Break fertig & getestet
	public boolean validate_Break(List<Double> all_Breaks_in_h, double breaktime_min_in_h)
	{
		double valide_Breaktime = 0;
		for(double rest :all_Breaks_in_h)
		{
			if (rest >= 0.25)
				valide_Breaktime += rest;
		}
		if (valide_Breaktime >= breaktime_min_in_h)
			return true;
		else
			return false;
	}
	
}


