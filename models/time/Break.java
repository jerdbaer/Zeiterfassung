package models.time;

import java.time.*;

/**
 * Class to define working break objects.
 * 
 * @author Tom Weißflog
 * @version 1.0
 */

public class Break extends Timespann implements IBreak{
	
	/**
	 * constant of the minimum duration of a break to count as legally accepted break
	 */
	private final long LEGAL_REQUIREMENT_MINUTES = 15;

	/**
	 * Constructor 
	 * 
	 * @param breakBegin begin of break time in hh:mm
	 * @param breakEnd end of break time in hh:mm
	 */
	public Break(LocalTime breakBegin, LocalTime breakEnd) {
		super(breakBegin, breakEnd);
	}

	/**
	 * Validates, if break fulfills legal requirements
	 * @return boolean, if fulfilled (true) or not (false)
	 */
	public boolean isLegal() {
		return !(duration.minusMinutes(LEGAL_REQUIREMENT_MINUTES).isNegative());
	}

}
