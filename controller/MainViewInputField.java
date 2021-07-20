package controller;

import java.time.Duration;
import java.time.LocalTime;

import models.ValidationState;

public class MainViewInputField {

	private String startHour;
	private String startMinute;
	private String endHour;
	private String endMinute;
	
	private LocalTime selfStart;
	private LocalTime predecessorEnd;
	

	public MainViewInputField(String startHour, String startMinute, String endHour, String endMinute) {
		this.startHour = startHour;
		this.startMinute = startMinute;
		this.endHour = endHour;
		this.endMinute = endMinute;
	}
	
	public boolean exists() {
		return(!startHour.isBlank() && !startMinute.isBlank() && !endHour.isBlank() && !endMinute.isBlank()) ? true : false;
	}
	
	public ValidationState valid() {
		try {
			if(isNegativ().equals(ValidationState.NOT_VALID))
				return ValidationState.NOT_VALID;
			return ValidationState.VALID;
			
		// no Input is valid Input	
		}catch(NullPointerException e) {
			return ValidationState.VALID;
		}
		
	}
	
	private ValidationState isNegativ() throws NullPointerException{
		try {
			var start = LocalTime.parse(inputFormatToHHMM(startHour, startMinute));
			var end = LocalTime.parse(inputFormatToHHMM(endHour, endMinute));
			return (Duration.between(start, end).isNegative()) ? ValidationState.NOT_VALID : ValidationState.VALID;
		
		// no Input	
		}catch(NullPointerException e) {
			return null;
		}
				
	}
	public ValidationState isAfterPredecessor() {
		return selfStart.isAfter(predecessorEnd) ? ValidationState.VALID : ValidationState.NOT_VALID;
	}
	
	private String inputFormatToHHMM(String hh, String mm)
	{
		try {
			return String.format("%02d", Integer.parseInt(hh)) + ":" + String.format("%02d", Integer.parseInt(mm));

		}catch(NumberFormatException e) {
			return null;
		}
		
		
	}

	public LocalTime getSelfStart() {
		return selfStart;
	}

	public void setSelfStart() {
		var hh = this.startHour;
		var mm = this.startMinute;
		var hhmm = String.format("%02d", Integer.parseInt(hh)) + ":" + String.format("%02d", Integer.parseInt(mm));
		this.selfStart = LocalTime.parse(hhmm);
	}

	public LocalTime getPredecessorEnd() {
		return predecessorEnd;
	}

	public void setPredecessorEnd(MainViewInputField predecessor) {
		var hh = predecessor.endHour;
		var mm = predecessor.endMinute;
		var hhmm = String.format("%02d", Integer.parseInt(hh)) + ":" + String.format("%02d", Integer.parseInt(mm));
		this.predecessorEnd = LocalTime.parse(hhmm);
	}
}
