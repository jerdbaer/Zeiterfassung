package models.time;

import java.time.LocalTime;

/**
 * Class to define work objects.
 * 
 * @author Tom Weißflog
 * @version 1.0
 */

public class Work extends Timespann{

	/**
	 * Constructor 
	 * 
	 * @param begin begin of working time in hh:mm
	 * @param end end of working time in hh:mm
	 */
	public Work(LocalTime begin, LocalTime end) {
		super(begin, end);
	}

}
