package models;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * class to define overtime object
 * 
 * @see OverviewController.fetchData(int MA_ID, String beginDate, String
 *      endDate)
 * @author Tom_W
 *
 */
public class Overtime {
	/**
	 * date of the day
	 */
	LocalDate date;
	/**
	 * overtime per day as float e.g. '1,5' is 1h 30min
	 */
	float overtime;

	/**
	 * 
	 * Constructor
	 * 
	 * @param String date as yyyy-mm-dd
	 * @param String overtime as HH:MM:SS
	 */
	public Overtime(String date, String hhMmSs) {
		this.date = LocalDate.parse(date);
		setOvertime(hhMmSs);
	}

	/**
	 * method to setOvertime converts String HH:MM:SS into float
	 * 
	 * @param String overtime HH:MM:SS
	 * 
	 */
	public void setOvertime(String hhMmSs) {
		if (hhMmSs.contains("-")) {
			hhMmSs = "0" + hhMmSs.substring(1);
			this.overtime = (LocalTime.parse(hhMmSs).getHour() + (LocalTime.parse(hhMmSs).getMinute() / 60)) * (-1);
		}
		this.overtime = LocalTime.parse(hhMmSs).getHour() + (LocalTime.parse(hhMmSs).getMinute() / 60);
	}

	public float getOvertime() {
		return this.overtime;
	}

	public LocalDate getDate() {
		return this.date;
	}

}
