package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Overtime {
	
	LocalDate date;
	int overtime;
	
	public Overtime(String date, String hhMmSs) {
		this.date = LocalDate.parse(date);
		this.overtime = setOvertime(hhMmSs);			
	}

	private int setOvertime(String hhMmSs) {
		if(hhMmSs.contains("-")) {
			hhMmSs = "0" + hhMmSs.substring(1);
			return (LocalTime.parse(hhMmSs).getHour() + (LocalTime.parse(hhMmSs).getMinute() / 60)) *(-1);
		}
		return LocalTime.parse(hhMmSs).getHour() + (LocalTime.parse(hhMmSs).getMinute() / 60);
	}
	
	public int getOvertime() {
		return this.overtime;
	}
	
	public LocalDate getDate() {
		return this.date;
	}
	
	

}
