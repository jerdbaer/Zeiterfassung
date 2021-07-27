package models;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class CalculationModel {
	private String selectedDay;
	private String workBegin;
	private String workEnd;
	private String totalBreakTime;
	private String totalWorkTime;
	private String overtime;
	private String comment;
	
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
		this.overtime = String.format("%02d:%02d:%02d", overtime.toHoursPart(),
				overtime.toMinutesPart(), overtime.toSecondsPart());
		
	}
		
	
}
