package controller;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

import models.Break;
import models.Interruption;
import models.Timespann;
import models.Work;

/**
 * Program to get information about a working day from a list of working times, break times, 
 * break interruptions and working time interruptions which are given by an ui-interface.
 * 
 * Calculated values are time at work, total work time, work begin, work end, (required) legal break time, 
 * and total break time.
 * 
 * @author Tom Weißflog
 * @author Josephine Luksch
 * @version 1.0
 */

public class CalculationController {
	
	/**
	 * list of working times, break times, work interruptions and break interruptions in Timespann
	 */
	private ArrayList<Timespann> formattedInput;
	/**
	 * duration between begin and end of a working day in PTnHnMnS (ISO-8601) 
	 */
	private Duration timeAtWork;
	/**
	 * begin of working time at working day in hh:mm
	 */
	private LocalTime workBegin;
	/**
	 * end of working time at working day in hh:mm 
	 */
	private LocalTime workEnd;
	
	/**
	 * constant for total working duration limit of defined hours for calculating the legal break requirement in 
	 * PTnHnMnS (ISO-8601)  
	 */
	private static final Duration LEGAL_BREAK_LIMIT_1 = Duration.ofHours(6);
	/**
	 * constant for total working duration limit of defined hours for calculating the legal break requirement in 
	 * PTnHnMnS (ISO-8601) 
	 */
	private static final Duration LEGAL_BREAK_LIMIT_2 = Duration.ofHours(9);
	/**
	 * legal break requirement of 30 minutes in PTnHnMnS (ISO-8601) 
	 */
	private static final Duration LEGAL_BREAK_OVER_SIX_HOURS = Duration.ofMinutes(30);
	/**
	 * legal break requirement of 45 minutes in PTnHnMnS (ISO-8601) 
	 */
	private static final Duration LEGAL_BREAK_OVER_NINE_HOURS = Duration.ofMinutes(45);
	
	
	/**
	 * Constructor
	 * 
	 * Selects time of begin and end of working day as work begin and work end from the given list with working times,
	 * break times, break interruptions and working time interruptions.
	 * Also calculates time at work as duration between work begin and work end of working day
	 * 
	 * @param formattedInput is a list of single working times, break times, break interruptions and working time 
	 * interruptions in format Timespann
	 * 
	 * @see Timespann
	 */
	
	public CalculationController(ArrayList<Timespann> formattedInput) {
		this.formattedInput = formattedInput;
		this.workBegin = pickWorkBeginFromInput();
		this.workEnd = pickWorkEndFromInput();
		this.timeAtWork = Duration.between(workBegin, workEnd);
	}
	
	/**
	 * returns time at work for a working day
	 * 
	 * @return time at work in PTnHnMnS (ISO-8601) 
	 */
	
	public Duration getTimeAtWork() {
		return this.timeAtWork;
	}
	
	/**
	 * returns work begin for a working day
	 * 
	 * @return work begin in hh:mm 
	 */
	
	public LocalTime getWorkBegin() {
		return this.workBegin;
	}
	
	/**
	 * returns work end for a working day
	 * 
	 * @return work end in hh:mm
	 */
	
	public LocalTime getWorkEnd() {
		return this.workEnd;
	}
	
	/**
	 * calculates total break duration from all given break times and work interruption times
	 * 
	 * @return total break time in PTnHnMnS (ISO-8601) 
	 */
	
	public Duration calculateBreakAndInterruptionDuration() {
		var breakAndInterruptionList = new ArrayList<Duration>();
		formattedInput.stream().filter(elm -> (elm instanceof Break) || (elm instanceof Interruption))
				.forEach(elm -> breakAndInterruptionList.add(elm.getDuration()));
		var breakAndInterruptionDuration = Duration.ZERO;
		for (Duration breakOrInterruption : breakAndInterruptionList) {
			breakAndInterruptionDuration = breakAndInterruptionDuration.plus(breakOrInterruption);
		}
		return breakAndInterruptionDuration;
	}

	/**
	 * calculates required legal break based on all work periods and Work Conditions Act
	 * 
	 * @return legal break in PTnHnMnS (ISO-8601) 
	 */
	
	public Duration calculateLegalBreak() {
		Duration legalBreak;
		if (timeAtWork.minus(LEGAL_BREAK_LIMIT_1).isNegative())
			legalBreak = Duration.ZERO;
		else if (timeAtWork.minus(LEGAL_BREAK_LIMIT_2).isNegative())
			legalBreak = LEGAL_BREAK_OVER_SIX_HOURS;
		else
			legalBreak = LEGAL_BREAK_OVER_NINE_HOURS;

		return legalBreak;
	}

	/**
	 * calculates total working time based on working times minus break times
	 * 
	 * @return total time at work in PTnHnMnS (ISO-8601) 
	 */
	
	public Duration calculateTotalWorktime() {
		Duration totalWorkTime = Duration.ZERO;
		for (Timespann segment : formattedInput) {
			if (segment instanceof Work)
				totalWorkTime = totalWorkTime.plus(segment.getDuration());
			else if (segment instanceof Break)
				totalWorkTime = totalWorkTime.minus(segment.getDuration());
		}
		return totalWorkTime;
	}
	
	/**
	 * selects first work begin of all working times
	 * 
	 * @return work begin of a working day in hh:mm
	 */
	
	private LocalTime pickWorkBeginFromInput() {
		var beginList = new ArrayList<LocalTime>();
		formattedInput.stream().filter(elm -> elm instanceof Work).forEach(work -> beginList.add(work.getBegin()));
		var begin = beginList.stream().sorted().findFirst().get();
		return begin;
	}

	/**
	 * selects last work end of all working times
	 * 
	 * @return work end of a working day in hh:mm
	 */
	
	private LocalTime pickWorkEndFromInput() {
		var endlist = new ArrayList<LocalTime>();
		formattedInput.stream().filter(elm -> elm instanceof Work).forEach(work -> endlist.add(work.getEnd()));
		var end = endlist.stream().sorted().reduce((first, second) -> second).get();
		return end;
	}

}
