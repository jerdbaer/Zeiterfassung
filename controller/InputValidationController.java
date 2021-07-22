package controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import models.Break;
import models.BreakInterruption;
import models.Interruption;
import models.Timespann;
import models.ValidationState;
import models.Work;

public class InputValidationController {

	private ArrayList<Timespann> inputList;
	private Duration legalBreak;
	private Duration timeAtWork;
	private Duration totalWorkingTime;
	private Duration timeAtBreak;
	private LocalTime workBegin;
	private LocalTime workEnd;
	private LocalTime workEndYesterday;
	private LocalDate selectedDay;

	private final long DAYS_FOR_REVISION_RELIABILITY = 31;
	private final LocalTime WORKING_LIMIT_BEGIN = LocalTime.of(6, 00);
	private final LocalTime WORKING_LIMIT_END = LocalTime.of(19, 30);
	private final Duration MIN_REQUIRED_DURATION_BETWEEN_WORKING_DAYS = Duration.ofHours(11);
	private final Duration MAX_WORKING_TIME_WITHOUT_BREAK = Duration.ofHours(6);

	public InputValidationController(ArrayList<Timespann> input, Duration legalBreak, Duration timeAtWork, Duration totalWorkingTime,
			Duration timeAtBreak, LocalTime workBegin, LocalTime workEnd, LocalDate selectedDay,
			LocalTime workEndYesterday) {
		this.inputList = input;
		this.legalBreak = legalBreak;
		this.timeAtWork = timeAtWork;
		this.totalWorkingTime = totalWorkingTime;
		this.timeAtBreak = timeAtBreak;
		this.workBegin = workBegin;
		this.workEnd = workEnd;
		this.selectedDay = selectedDay;
		this.workEndYesterday = workEndYesterday;
	}

	public ArrayList<ValidationState> validation() {

		var validation = new ArrayList<ValidationState>();
		try {
			var workBeginToday = LocalDateTime.of(selectedDay, workBegin);
			var workEndYesterdayLDT = LocalDateTime.of(selectedDay.minusDays(1), workEndYesterday);
			validation.add(checkDurationBetweenWorkingDays(workEndYesterdayLDT, workBeginToday));
		} catch (NullPointerException e) {
			validation.add(ValidationState.NOT_VALID_NO_DATE_SELECTED);
		}
		validation.add(checkInputExists(inputList));
		validation.add(checkTimeOrder(inputList));
		validation.add(checkBreaksInWorkTime(inputList, workBegin, workEnd));
		validation.add(checkTotalBreakCompliance(timeAtWork, timeAtBreak, legalBreak));
		validation.add(checkSingleBreakDurationCompliance(inputList, legalBreak));
		validation.add(checkDatepickerCompliants(selectedDay));
		validation.add(checkWorkTimeLimits(workBegin, workEnd));
		validation.add(checkTimeAtWorkOverTenHours(timeAtWork));
		validation.add(checkWorkingTimeOverSixHoursWithoutBreak(inputList, workBegin, workEnd));

		return validation;

	}

	private ValidationState checkDurationBetweenWorkingDays(LocalDateTime workEndYesterday,
			LocalDateTime workBeginToday) {
		var duration = Duration.between(workEndYesterday, workBeginToday);
		return duration.minus(MIN_REQUIRED_DURATION_BETWEEN_WORKING_DAYS).isNegative()
				? ValidationState.NOT_VALID_DURATION_BETWEEN_WORKING_DAYS_ERROR
				: ValidationState.VALID;
	}

	private ValidationState checkTimeAtWorkOverTenHours(Duration timeAtWork) {
		return (totalWorkingTime.compareTo(Duration.ofHours(10)) > 0) ? ValidationState.NOT_VALID_WORKING_TIME_OVER_TEN_HOURS
				: ValidationState.VALID;
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

	private ValidationState checkSingleBreakDurationCompliance(ArrayList<Timespann> formattedInput,
			Duration legalBreak) {

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

	private ValidationState checkBreaksInWorkTime(ArrayList<Timespann> formattedInput, LocalTime workBegin,
			LocalTime workEnd) {
		var breaklist = new ArrayList<Break>();
		formattedInput.stream().filter(elm -> elm instanceof Break).forEach(abreak -> breaklist.add((Break) abreak));
		var worklist = new ArrayList<Work>();
		formattedInput.stream().filter(elm -> elm instanceof Work).forEach(work -> worklist.add((Work) work));

		for (Break breaks : breaklist) {

			if (breaks.getBegin().equals(workBegin) || breaks.getEnd().equals(workEnd))
				return ValidationState.NOT_VALID_BREAK_CANNOT_BE_AT_WORKING_BEGIN_OR_END;

			var isBreakinbetweenWorksegment = worklist.stream().filter(
					work -> work.getBegin().isBefore(breaks.getBegin()) && work.getEnd().isAfter(breaks.getEnd()))
					.findFirst().isPresent();
			if (!isBreakinbetweenWorksegment)
				return ValidationState.NOT_VALID_BREAK_IS_NOT_IN_WORKTIME;
		}
		return ValidationState.VALID;
	}

	private ValidationState checkTimeOrder(ArrayList<Timespann> formattedInput) {
		if (formattedInput.stream().filter(elm -> (elm instanceof Interruption) || (elm instanceof BreakInterruption))
				.anyMatch(elm -> elm.getDuration().isNegative()))
			return ValidationState.NOT_VALID_TIME_INPUT_MUST_BE_IN_CHRONICAL_ORDER;
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

	private ValidationState checkWorkingTimeOverSixHoursWithoutBreak(ArrayList<Timespann> formattedInput,
			LocalTime workBegin, LocalTime workEnd) {

		var workList = new ArrayList<Work>();
		formattedInput.stream().filter(elm -> elm instanceof Work).forEach(work -> workList.add((Work) work));

		var breakList = new ArrayList<Break>();
		formattedInput.stream().filter(elm -> elm instanceof Break).forEach(breaks -> breakList.add((Break) breaks));

		for (Work work : workList) {
			var x = new ArrayList<LocalTime>();
			
			// filter Begin, End for all TimeSpann were begin or end is after work Begin
			formattedInput.stream()
					.filter(elm -> elm.getBegin().isAfter(work.getBegin()) || elm.getEnd().isAfter(work.getBegin()))
					.forEach(other ->{
						x.add(other.getBegin());
						x.add(other.getEnd());
					});
			// filter all Begin, End all who are not work begin AND find first
			var endWorkSegment = x.stream().filter(elm -> !(elm.equals(work.getBegin()))).sorted().findFirst().get();

			if ((Duration.between(work.getBegin(), endWorkSegment).compareTo(Duration.ofHours(6)) > 0))
				return ValidationState.NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR;
		}

		for (Break breaks : breakList) {
			var x = new ArrayList<LocalTime>();
			formattedInput.stream()
					.filter(elm -> elm.getEnd().isAfter(breaks.getEnd()))
					.forEach(other -> x.add(other.getEnd()));
			var endWorkSegmentAfterBreak = x.stream().sorted().findFirst().get();
			if ((Duration.between(breaks.getEnd(), endWorkSegmentAfterBreak).compareTo(Duration.ofHours(6)) > 0))
				return ValidationState.NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR;
		}

		return ValidationState.VALID;

	}

}
