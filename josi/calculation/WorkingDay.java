package josi.calculation;

import java.time.LocalTime;
import java.util.ArrayList;

public class WorkingDay {
	
	private LocalTime workingTime;
	private LocalTime totalBreak;
	private boolean legalBreakCompliant;
	private ArrayList<String> costCenter;
	
	public WorkingDay(LocalTime workingTime, 
						LocalTime totalBreak, 
						boolean legalBreakCompliant, 
						ArrayList<String> costCenter) 
	{
		this.workingTime = workingTime;
		this.totalBreak = totalBreak;
		this.legalBreakCompliant = legalBreakCompliant;
		this.costCenter = costCenter;
	}
	
	

}
