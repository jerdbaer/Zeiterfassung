package models;

import java.time.LocalTime;

//
public class Interruption extends Timespann implements IBreak{

	private final long LEGAL_REQUIREMENT_MINUTES = 15;
	
	public Interruption(LocalTime breakBegin, LocalTime breakEnd) {
		super(breakBegin, breakEnd);
	}
	
	public boolean isLegal() {
		return !(duration.minusMinutes(LEGAL_REQUIREMENT_MINUTES).isNegative());
	}

}
