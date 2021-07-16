package josi.calculation;

import java.time.LocalTime;
//import java.util.ArrayList;

public class Break {

	private LocalTime breakBegin;
	private LocalTime breakEnd;
	private LocalTime breakDuration;
	private LocalTime tempBreakDuration;
	
	private final LocalTime LEGAL_REQUIREMENT = LocalTime.of(0,15);
	boolean legalCompliant;
	
	//private ArrayList<Break> totalBreak;
	
	public Break(LocalTime breakBegin, LocalTime breakEnd) {
		this.breakBegin = breakBegin;
		this.breakEnd = breakEnd;
		this.breakDuration = calculateBreakDuration();
		this.legalCompliant = validateLegalCompliance();
	}
	
	public LocalTime calculateBreakDuration() {
		tempBreakDuration = breakEnd.minusHours(breakBegin.getHour());
		breakDuration = tempBreakDuration.minusMinutes(breakBegin.getMinute());
		return breakDuration;
	}
		
	public boolean validateLegalCompliance() {
		if (breakDuration.compareTo(LEGAL_REQUIREMENT) == 0 || breakDuration.compareTo(LEGAL_REQUIREMENT) > 0) {
			return true;
		}
		return false;
	}
	

}
