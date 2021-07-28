package models.time;

import java.time.LocalTime;

/**
 * Class to define Interruption objects.
 * An interruption is the duration between two work periods, meaning from the end of work period 1 to the 
 * begin of work period 2.
 * 
 * @author Tom Weißflog
 * @version 1.0
 */

public class Interruption extends Timespann implements IBreak{

	/**
	 * constant of the minimum duration of an interruption to count as legally accepted interruption
	 */
	private final long LEGAL_REQUIREMENT_MINUTES = 15;
	
	/**
	 * Constructor 
	 * 
	 * @param breakBegin begin of break time in hh:mm
	 * @param breakEnd end of break time in hh:mm
	 */
	public Interruption(LocalTime breakBegin, LocalTime breakEnd) {
		super(breakBegin, breakEnd);
	}
	
	/**
	 * Validates, if interruption fulfills legal requirements
	 * @return boolean if fulfilled (true) or not (false)
	 */
	public boolean isLegal() {
		return !(duration.minusMinutes(LEGAL_REQUIREMENT_MINUTES).isNegative());
	}

}
