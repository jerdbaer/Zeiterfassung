package controller;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

import models.Break;
import models.Interruption;
import models.Timespann;
import models.Work;

public class CalculationController {
	
	private ArrayList<Timespann> formattedInput;
	private LocalTime workBegin;
	private LocalTime workEnd;
	private Duration timeAtWork;
	
	private final Duration LEGAL_BREAK_LIMIT_1 = Duration.ofHours(6);
	private final Duration LEGAL_BREAK_LIMIT_2 = Duration.ofHours(9);
	private final Duration LEGAL_BREAK_OVER_SIX_HOURS = Duration.ofMinutes(30);
	private final Duration LEGAL_BREAK_OVER_NINE_HOURS = Duration.ofMinutes(45);
	
	public CalculationController(ArrayList<Timespann> formattedInput) {
		this.formattedInput = formattedInput;
		this.workBegin = workbegin();
		this.workEnd = workend();
		this.timeAtWork = Duration.between(workBegin, workEnd);
	}
	
	public Duration getTimeAtWork() {
		return this.timeAtWork;
	}
	
	public LocalTime getWorkBegin() {
		return this.workBegin;
	}
	
	public LocalTime getWorkEnd() {
		return this.workEnd;
	}
	
	public Duration totalWorktime() {
		Duration totalWorkTime = Duration.ZERO;
		for (Timespann segment : formattedInput) {
			if (segment instanceof Work)
				totalWorkTime = totalWorkTime.plus(segment.getDuration());
			else if (segment instanceof Break)
				totalWorkTime = totalWorkTime.minus(segment.getDuration());
		}
		return totalWorkTime;
	}

	public Duration breakUinterruptionDuration() {
		var breakUinterruptionList = new ArrayList<Duration>();
		formattedInput.stream().filter(elm -> (elm instanceof Break) || (elm instanceof Interruption))
				.forEach(elm -> breakUinterruptionList.add(elm.getDuration()));
		var breakUinterruptionDuration = Duration.ZERO;
		for (Duration breakUinterruption : breakUinterruptionList) {
			breakUinterruptionDuration = breakUinterruptionDuration.plus(breakUinterruption);
		}
		return breakUinterruptionDuration;
	}

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

	private LocalTime workbegin() {
		var beginlist = new ArrayList<LocalTime>();
		formattedInput.stream().filter(elm -> elm instanceof Work).forEach(work -> beginlist.add(work.getBegin()));
		var begin = beginlist.stream().sorted().findFirst().get();
		return begin;
	}

	private LocalTime workend() {
		var endlist = new ArrayList<LocalTime>();
		formattedInput.stream().filter(elm -> elm instanceof Work).forEach(work -> endlist.add(work.getEnd()));
		var end = endlist.stream().sorted().reduce((first, second) -> second).get();
		return end;
	}

}
