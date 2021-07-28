package models.time;

import java.time.LocalTime;

/**
 * Class to define break interruption objects.
 * A break interruption is the duration between two break periods, meaning from the end of break period 1 to 
 * the begin of break period 2.
 * 
 * @author Tom Weißflog
 * @version 1.0
 */

public class BreakInterruption extends Timespann {

	/**
	 * 
	 * @param begin begin of break interruption in hh:mm
	 * @param end end of break interruption in hh:mm
	 */
	public BreakInterruption(LocalTime begin, LocalTime end) {
		super(begin, end);
		
	}

}
