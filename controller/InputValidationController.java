package controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import models.Break;
import models.Interruption;
import models.Timespann;
import models.ValidationState;
import models.Work;

public class InputValidationController {

	private ArrayList<Timespann> inputList;
	private Duration legalBreak;
	private Duration timeAtWork;
	private Duration timeAtBreak;
	private LocalTime workBegin;
	private LocalTime workEnd;
	private LocalDate selectedDay;
	
	private final long DAYS_FOR_REVISION_RELIABILITY = 31;
	private final LocalTime WORKING_LIMIT_BEGIN = LocalTime.of(6, 00);
	private final LocalTime WORKING_LIMIT_END = LocalTime.of(19, 30);
	

	public InputValidationController(ArrayList<Timespann> input, Duration legalBreak, Duration timeAtWork, Duration timeAtBreak,
			LocalTime workBegin, LocalTime workEnd, LocalDate selectedDay) {
		this.inputList = input;
		this.legalBreak = legalBreak;
		this.timeAtWork = timeAtWork;
		this.timeAtBreak = timeAtBreak;
		this.workBegin = workBegin;
		this.workEnd = workEnd;
		this.selectedDay = selectedDay;
	}

	public ArrayList<ValidationState> validation() {

		var validation = new ArrayList<ValidationState>();

		validation.add(checkInputExists(inputList));
		validation.add(checkWorkTimeOrder(inputList));
		validation.add(checkBreaksInWorkTime(inputList));
		validation.add(checkTotalBreakCompliance(timeAtWork, timeAtBreak, legalBreak));
		validation.add(checkSingleBreakDurationCompliance(inputList, legalBreak));
		validation.add(checkDatepickerCompliants(selectedDay));
		validation.add(checkWorkTimeLimits(workBegin, workEnd));

		return validation;

	}

	private ValidationState checkWorkTimeLimits(LocalTime workBegin, LocalTime workEnd) {

		if (workBegin.isBefore(WORKING_LIMIT_BEGIN))
			return ValidationState.VALID_WORKBEGIN_IS_BEFORE_6_00;

		if (workEnd.isAfter(WORKING_LIMIT_END))
			return ValidationState.VALID_WORKEND_IS_AFTER_19_30;
		return ValidationState.VALID;
	}

	private ValidationState checkDatepickerCompliants(LocalDate selectedDay) {
		
		try {
			if (selectedDay.isAfter(LocalDate.now()))
				return ValidationState.NOT_VALID_SELECTED_DAY_IS_IN_THE_FUTURE;
			if (selectedDay.isBefore(LocalDate.now().minusDays(DAYS_FOR_REVISION_RELIABILITY)))
				return ValidationState.NOT_VALID_SELECTED_DAY_IS_TO_FAR_IN_THE_PAST;
		} catch (NullPointerException e) {
			return ValidationState.NOT_VALID_NO_DATE_SELECTED;
		}
		return ValidationState.VALID;
	}

	private ValidationState checkSingleBreakDurationCompliance(ArrayList<Timespann> formattedInput, Duration legalBreak) {
		
		var listLegalBreaks = new ArrayList<Timespann>();
		Duration breakDurationCompliant = Duration.ZERO;
		formattedInput.stream().filter(elm -> elm instanceof Break).filter(elm -> ((Break) elm).isLegal())
				.forEach(elm -> listLegalBreaks.add(elm));
		formattedInput.stream().filter(elm -> elm instanceof Interruption).filter(elm -> ((Interruption) elm).isLegal())
				.forEach(elm -> listLegalBreaks.add(elm));
		for (Timespann breaks : listLegalBreaks) {
			breakDurationCompliant = breakDurationCompliant.plus(breaks.getDuration());
		}
		return breakDurationCompliant.minus(legalBreak).isNegative()
				? ValidationState.NOT_VALID_SINGLE_BREAK_DURATIONS_ERROR
				: ValidationState.VALID;
	}

	private ValidationState checkBreaksInWorkTime(ArrayList<Timespann> formattedInput) {
		var breaklist = new ArrayList<Break>();
		formattedInput.stream().filter(elm -> elm instanceof Break).forEach(abreak -> breaklist.add((Break) abreak));
		var worklist = new ArrayList<Work>();
		formattedInput.stream().filter(elm -> elm instanceof Work).forEach(work -> worklist.add((Work) work));

		for (Break breaks : breaklist) {
			var isBreakinbetweenWorksegment = worklist.stream().filter(
					work -> work.getBegin().isBefore(breaks.getBegin()) && work.getEnd().isAfter(breaks.getEnd()))
					.findFirst().isPresent();
			if (!isBreakinbetweenWorksegment)
				return ValidationState.NOT_VALID_BREAK_IS_NOT_IN_WORKTIME;
		}
		return ValidationState.VALID;
	}

	private ValidationState checkWorkTimeOrder(ArrayList<Timespann> formattedInput) {
		if (formattedInput.stream().filter(elm -> elm instanceof Interruption)
				.anyMatch(elm -> elm.getDuration().isNegative()))
			return ValidationState.NOT_VALID_WORKSEGMENTS_MUST_BE_IN_CHRONICAL_ORDER;
		else if (formattedInput.stream().anyMatch(elm -> elm.getDuration().isNegative()))
			return ValidationState.NOT_VALID_START_IS_AFTER_END;
		return ValidationState.VALID;
	}

	private ValidationState checkInputExists(ArrayList<Timespann> formattedInput) {
		return (formattedInput.stream().noneMatch(elm -> elm instanceof Work))
				? ValidationState.NOT_VALID_NO_INPUT_FOUND
				: ValidationState.VALID;
	}

	private ValidationState checkTotalBreakCompliance(Duration timeAtwork, Duration timeAtBreak, Duration legalBreak) {
		return timeAtBreak.minus(legalBreak).isNegative() ? ValidationState.NOT_VALID_TOTAL_BREAK_ERROR
				: ValidationState.VALID;
	}

}
