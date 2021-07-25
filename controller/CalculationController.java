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
	private Duration timeAtWork;
	private LocalTime workBegin;
	private LocalTime workEnd;
	
	private static final Duration LEGAL_BREAK_LIMIT_1 = Duration.ofHours(6);
	private static final Duration LEGAL_BREAK_LIMIT_2 = Duration.ofHours(9);
	private static final Duration LEGAL_BREAK_OVER_SIX_HOURS = Duration.ofMinutes(30);
	private static final Duration LEGAL_BREAK_OVER_NINE_HOURS = Duration.ofMinutes(45);
	
	public CalculationController(ArrayList<Timespann> formattedInput) {
		this.formattedInput = formattedInput;
		this.workBegin = pickWorkBeginFromInput();
		this.workEnd = pickWorkEndFromInput();
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
	
	private LocalTime pickWorkBeginFromInput() {
		var beginList = new ArrayList<LocalTime>();
		formattedInput.stream().filter(elm -> elm instanceof Work).forEach(work -> beginList.add(work.getBegin()));
		var begin = beginList.stream().sorted().findFirst().get();
		return begin;
	}

	private LocalTime pickWorkEndFromInput() {
		var endlist = new ArrayList<LocalTime>();
		formattedInput.stream().filter(elm -> elm instanceof Work).forEach(work -> endlist.add(work.getEnd()));
		var end = endlist.stream().sorted().reduce((first, second) -> second).get();
		return end;
	}

}
