package test.calculationInputValidationControllerTest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import controller.InputValidationController;
import models.Break;
import models.BreakInterruption;
import models.Interruption;
import models.Timespann;
import models.ValidationState;
import models.Work;

public class InputValidationControllerStub extends InputValidationController {
		
	private static final Duration MIN_REQUIRED_DURATION_BETWEEN_WORKING_DAYS = Duration.ofHours(11);
	private static final Duration MAX_DAILY_WORKING_TIME = Duration.ofHours(10);
	private static final Duration MAX_WORKING_TIME_WITHOUT_BREAK = Duration.ofHours(6);
	private static final LocalTime WORKING_LIMIT_BEGIN = LocalTime.of(6, 00);
	private static final LocalTime WORKING_LIMIT_END = LocalTime.of(19, 30);
	private static final long DAYS_FOR_REVISION_RELIABILITY = 31;
	
	public InputValidationControllerStub(ArrayList<Timespann> input, Duration legalBreak, 
			Duration totalWorkingTime, Duration timeAtBreak, LocalTime workBegin, LocalTime workEnd,
			LocalDate selectedDay, LocalTime workEndYesterday, LocalTime workBeginTomorrow) {
		super(input, legalBreak, totalWorkingTime, timeAtBreak, workBegin, workEnd, selectedDay, workEndYesterday, 
				workBeginTomorrow);
	}
	

	protected ValidationState checkDurationBetweenWorkingDays(LocalDateTime workEndYesterday,
			LocalDateTime workBeginToday) {
		var duration = Duration.between(workEndYesterday, workBeginToday);

		return duration.minus(MIN_REQUIRED_DURATION_BETWEEN_WORKING_DAYS).isNegative()
				? ValidationState.NOT_VALID_DURATION_BETWEEN_WORKING_DAYS_ERROR
				: ValidationState.VALID;
	}
	
	protected ValidationState checkTotalWorkingTimeOverTenHours(Duration totalWorkingTime) {
		
		return (totalWorkingTime.compareTo(MAX_DAILY_WORKING_TIME) > 0)
				? ValidationState.NOT_VALID_WORKING_TIME_OVER_TEN_HOURS
				: ValidationState.VALID;
	}

	protected ValidationState checkWorkTimeLimits(LocalTime workBegin, LocalTime workEnd) {
		if (workBegin.isBefore(WORKING_LIMIT_BEGIN))
			return ValidationState.VALID_WORKBEGIN_IS_BEFORE_6_00;

		if (workEnd.isAfter(WORKING_LIMIT_END))
			return ValidationState.VALID_WORKEND_IS_AFTER_19_30;
		
		return ValidationState.VALID;
	}

	protected ValidationState checkDatepickerCompliance(LocalDate selectedDay) {
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

	protected ValidationState checkSingleBreakDurationCompliance(ArrayList<Timespann> formattedInput,
			Duration legalBreak) {
		var legalBreakList = new ArrayList<Timespann>();
		Duration compliantBreakDuration = Duration.ZERO;
		formattedInput.stream().filter(elm -> elm instanceof Break).filter(elm -> ((Break) elm).isLegal())
				.forEach(elm -> legalBreakList.add(elm));
		formattedInput.stream().filter(elm -> elm instanceof Interruption).filter(elm -> ((Interruption) elm).isLegal())
				.forEach(elm -> legalBreakList.add(elm));
		for (Timespann breaks : legalBreakList) {
			compliantBreakDuration = compliantBreakDuration.plus(breaks.getDuration());
		}
		
		return compliantBreakDuration.minus(legalBreak).isNegative()
				? ValidationState.NOT_VALID_SINGLE_BREAK_DURATIONS_ERROR
				: ValidationState.VALID;
	}

	protected ValidationState checkBreaksInWorkTime(ArrayList<Timespann> formattedInput, LocalTime workBegin,
			LocalTime workEnd) {
		var breakList = new ArrayList<Break>();
		formattedInput.stream().filter(elm -> elm instanceof Break).forEach(abreak -> breakList.add((Break)abreak));
		var workList = new ArrayList<Work>();
		formattedInput.stream().filter(elm -> elm instanceof Work).forEach(work -> workList.add((Work)work));

		for (Break breaks : breakList) {
			if (breaks.getBegin().equals(workBegin) || breaks.getEnd().equals(workEnd))
				return ValidationState.NOT_VALID_BREAK_CANNOT_BE_AT_WORKING_BEGIN_OR_END;

			var isBreakInWorksegmentCheck = workList.stream()
					.filter( work -> work.getBegin().isBefore(breaks.getBegin()) 
							&& work.getEnd().isAfter(breaks.getEnd()))
					.findFirst().isPresent();
			if (!isBreakInWorksegmentCheck)
				return ValidationState.NOT_VALID_BREAK_IS_NOT_IN_WORKTIME;
		}
		
		return ValidationState.VALID;
	}

	protected ValidationState checkTimeOrderIsChronological(ArrayList<Timespann> formattedInput) {
		if (formattedInput.stream().filter(elm -> (elm instanceof Interruption) || (elm instanceof BreakInterruption))
				.anyMatch(elm -> elm.getDuration().isNegative()))
			return ValidationState.NOT_VALID_TIME_INPUT_MUST_BE_IN_CHRONICAL_ORDER;
		else if (formattedInput.stream().anyMatch(elm -> elm.getDuration().isNegative()))
			return ValidationState.NOT_VALID_START_IS_AFTER_END;
		
		return ValidationState.VALID;
	}

	protected ValidationState checkInputExists(ArrayList<Timespann> formattedInput) {
		
		return (formattedInput.stream().noneMatch(elm -> elm instanceof Work))
				? ValidationState.NOT_VALID_NO_INPUT_FOUND
				: ValidationState.VALID;
	}

	protected ValidationState checkTotalBreakCompliance(Duration timeAtBreak, Duration legalBreak) {
		
		return timeAtBreak.minus(legalBreak).isNegative() 
				? ValidationState.NOT_VALID_TOTAL_BREAK_ERROR
				: ValidationState.VALID;
	}
	
	
	protected ValidationState checkWorkingTimeOverSixHoursWithoutBreak(ArrayList<Timespann> formattedInput) {

		var workList = new ArrayList<Work>();
		formattedInput.stream().filter(elm -> elm instanceof Work).forEach(work -> workList.add((Work) work));

		var breakList = new ArrayList<Break>();
		formattedInput.stream().filter(elm -> elm instanceof Break).forEach(breaks -> breakList.add((Break) breaks));

		for (Work work : workList) {
			var listOfBeginAndEndTimesAfterWorkBegin = new ArrayList<LocalTime>();
			formattedInput.stream()
					.filter(elm -> elm.getBegin().isAfter(work.getBegin()) || elm.getEnd().isAfter(work.getBegin()))
					.forEach(other -> {
						listOfBeginAndEndTimesAfterWorkBegin.add(other.getBegin());
						listOfBeginAndEndTimesAfterWorkBegin.add(other.getEnd());
					});
			var endWorkSegment = listOfBeginAndEndTimesAfterWorkBegin.stream()
					.filter(elm -> !(elm.equals(work.getBegin()))).sorted().findFirst().get();

			if ((Duration.between(work.getBegin(), endWorkSegment).compareTo(MAX_WORKING_TIME_WITHOUT_BREAK) > 0))
				return ValidationState.NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR;
		}

		for (Break breaks : breakList) {
			var listOfEndTimesAfterBreakEnd = new ArrayList<LocalTime>();
			formattedInput.stream().filter(elm -> elm.getEnd().isAfter(breaks.getEnd()))
					.forEach(other -> listOfEndTimesAfterBreakEnd.add(other.getEnd()));
			var endWorkSegmentAfterBreak = listOfEndTimesAfterBreakEnd.stream().sorted().findFirst().get();
			if ((Duration.between(breaks.getEnd(), endWorkSegmentAfterBreak)
					.compareTo(MAX_WORKING_TIME_WITHOUT_BREAK) > 0))
				return ValidationState.NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR;
		}

		return ValidationState.VALID;
	}
	
	
}
