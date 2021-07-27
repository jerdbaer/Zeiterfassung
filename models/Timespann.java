package models;

import java.time.Duration;
import java.time.LocalTime;

public abstract class Timespann {
	protected LocalTime begin;
	protected LocalTime end;
	protected Duration duration;
	
	public Timespann(LocalTime begin, LocalTime end) {
		this.begin = begin;
		this.end = end;
		this.duration = Duration.between(begin, end);
	}
	
	public LocalTime getBegin() {
		return this.begin;
	}
	
	public LocalTime getEnd() {
		return this.end;
	}
	
	public Duration getDuration() {
		return this.duration;
	}

}
