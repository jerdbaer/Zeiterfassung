package models;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Abstract class to define Timespann objects.
 * @author Tom Weißflog
 * @version 1.0
 */

public abstract class Timespann {
	/**
	 * start time of timespann in hh:mm
	 */
	protected LocalTime begin;
	/**
	 * end time of timespann in hh:mm
	 */
	protected LocalTime end;
	/**
	 * duration between begin end end of timespann in PTnHnMnS (ISO-8061)
	 */
	protected Duration duration;
	
	/**
	 * Constructor
	 * 
	 * @param begin start time of timespann in hh:mm
	 * @param end time of timespann in hh:mm
	 */
	public Timespann(LocalTime begin, LocalTime end) {
		this.begin = begin;
		this.end = end;
		this.duration = Duration.between(begin, end);
	}
	
	/**
	 * gets begin of timespann
	 * @return begin in hh:mm
	 */
	public LocalTime getBegin() {
		return this.begin;
	}
	
	/**
	 * gets end of timespann
	 * @return end in hh:mm
	 */
	public LocalTime getEnd() {
		return this.end;
	}
	
	/**
	 * gets duration of timespann
	 * @return duration on PTnHnMnS (ISO-8061)
	 */
	public Duration getDuration() {
		return this.duration;
	}

}
