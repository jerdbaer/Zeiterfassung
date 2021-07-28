package models;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Class to define DataEntryModel objects.
 * 
 * @author Tom Weißflog
 * @version 1.0
 *
 */

public class DataEntryModel {
	
	/**
	 * individual employee's id in int
	 */
	private int MA_ID;
	/**
	 * password for login account in String
	 */
	private String password;
	/**
	 * date of considered working day for which the working day information should be calculated and recorded in String
	 */	
	private String selectedDay;
	/**
	 * begin of working time at working day in String
	 */
	private String workBegin;
	/**
	 * end of working time at working day in String
	 */
	private String workEnd;
	/**
	 * total break duration in String
	 */
	private String totalBreakTime;
	/**
	 * total working duration at a working day in String
	 */
	private String totalWorkTime;
	
	/**
	 * overtime at a working day in String
	 */
	private String overtime;

	/*
	 * comment by the user which can oder must be filled during working time recording steps in String
	 */
	private String comment;
	
	
	
	
	public int getMA_ID() {
		return MA_ID;
	}
	public void setMA_ID(int MA_ID) {
		this.MA_ID = MA_ID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSelectedDay() {
		return selectedDay;
	}
	public void setSelectedDay(LocalDate selectedDay) {
		this.selectedDay = selectedDay.toString();
	}
	public String getWorkBegin() {
		return workBegin;
	}
	public void setWorkBegin(LocalTime workBegin) {
		this.workBegin = workBegin.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
	}
	public String getWorkEnd() {
		return workEnd;
	}
	public void setWorkEnd(LocalTime workEnd) {
		this.workEnd = workEnd.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
	}
	public String getTotalBreakTime() {
		return totalBreakTime;
	}
	public void setTotalBreakTime(Duration totalBreakTime) {
		this.totalBreakTime = String.format("%02d:%02d:%02d", totalBreakTime.toHoursPart(),
				totalBreakTime.toMinutesPart(), totalBreakTime.toSecondsPart());
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getTotalWorkTime() {
		return totalWorkTime;
	}
	public void setTotalWorkTime(Duration totalWorkTime) {
		this.totalWorkTime =String.format("%02d:%02d:%02d", totalWorkTime.toHoursPart(),
				totalWorkTime.toMinutesPart(), totalWorkTime.toSecondsPart());
	}
	public String getOvertime() {
		return this.overtime;
	}
	public void setOvertime(Duration overtime) {
		if(overtime.isNegative()) {
			var temp = overtime.abs();
			this.overtime = String.format("-%02d:%02d:%02d", temp.toHoursPart(),
					temp.toMinutesPart(), temp.toSecondsPart());	
		}else {
			this.overtime = String.format("%02d:%02d:%02d", overtime.toHoursPart(),
					overtime.toMinutesPart(), overtime.toSecondsPart());
		}
			
		
		
	}
		
}
