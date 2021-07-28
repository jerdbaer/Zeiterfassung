package controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import models.ValidationState;
import models.time.Break;
import models.time.BreakInterruption;
import models.time.Interruption;
import models.time.Timespann;
import models.time.Work;

/**
 * programm to validate user input in working time management tool
 * 
 * @author Tom Weißflog
 * @author Josephine Luksch
 * @version 1.0
 */ 

public class InputValidationController {

	/**
	 * list of working times, break times, work interruptions and break interruptions in Timespann
	 */
	private ArrayList<Timespann> inputList;
	/**
	 * legally required minimum break duration based on working time in hh:mm
	 */
	private Duration legalBreak;
	/**
	 * total break duration in PTnHnMnS (ISO-8601)  
	 */
	private Duration timeAtBreak;
	/**
	 * total working duration at a working day in PTnHnMnS (ISO-8601) 
	 */
	private Duration totalWorkingTime;
	/**
	 * begin of working time at working day in hh:mm
	 */
	private LocalTime workBegin;
	/**
	 * end of working time at working day in hh:mm 
	 */
	private LocalTime workEnd;
	/**
	 * end of working day at the day before the selected working day in hh:mm 
	 */
	private LocalTime workEndYesterday;
	/**
	 * begin of working day at the day after the selected working day in hh:mm
	 */
	private LocalTime workBeginTomorrow;
	/**
	 * date of considered working day for which the working day information should be calculated and recorded 
	 * in yyyy-mm-dd
	 */
	private LocalDate selectedDay;

	/**
	 * constant for the minimum duration between the end of one and the begin of the following working day in PTnHnMnS (ISO-8601) 
	 */
	private static final Duration MIN_REQUIRED_DURATION_BETWEEN_WORKING_DAYS = Duration.ofHours(11);
	/**
	 * constant for the maximum amount of working hours which are legally allowed for one working day in PTnHnMnS (ISO-8601) 
	 */
	private static final Duration MAX_DAILY_WORKING_TIME = Duration.ofHours(10);
	/** 
	 * constant for the maximal single work duration without taking a break which are leagally allowed for one 
	 * working period in PTnHnMnS (ISO-8601) 
	 */
	private static final Duration MAX_WORKING_TIME_WITHOUT_BREAK = Duration.ofHours(6);
	/**
	 * constant for the earliest work begin which are given by the operational working time frame (defined by company) 
	 * in hh:mm
	 */
	private static final LocalTime WORKING_LIMIT_BEGIN = LocalTime.of(6, 00);
	/**
	 *  constant for the latest work end which are given by the operational working time frame (defined by company) 
	 * in hh:mm
	 */
	private static final LocalTime WORKING_LIMIT_END = LocalTime.of(19, 30);
	/**
	 * constant for ensuring revision safety in the way that working days of the past can be updated only for a 
	 * defined time period in days
	 */
	private static final long DAYS_FOR_REVISION_RELIABILITY = 31;

	
	/**
	 * Class constructor creating input validation controller 
	 * 
	 * @param input a list of working times, break times, work interruptions and break interruptions in Timespann
	 * @param legalBreak legally required minimum break duration based on working time in PTnHnMnS (ISO-8601) 
	 * @param totalWorkingTime total working duration at a working day in PTnHnMnS (ISO-8601)  
	 * @param timeAtBreak total break duration in PTnHnMnS (ISO-8601) 
	 * @param workBegin begin of working time at working day in hh:mm
	 * @param workEnd end of working time at working day in hh:mm
	 * @param selectedDay date of considered working day for which the working day information should be calculated 
	 * and recorded in yyyy-mm-dd
	 * @param workEndYesterday end of working time the day before the selected working day in hh:mm
	 * @param workBeginTomorrow begin of working time the day after the selected working day in hh:mm
	 */
	
	public InputValidationController(ArrayList<Timespann> input, Duration legalBreak, 
			Duration totalWorkingTime, Duration timeAtBreak, LocalTime workBegin, LocalTime workEnd,
			LocalDate selectedDay, LocalTime workEndYesterday, LocalTime workBeginTomorrow) {
		this.inputList = input;
		this.legalBreak = legalBreak;
		this.totalWorkingTime = totalWorkingTime;
		this.timeAtBreak = timeAtBreak;
		this.workBegin = workBegin;
		this.workEnd = workEnd;
		this.selectedDay = selectedDay;
		this.workEndYesterday = workEndYesterday;
		this.workBeginTomorrow = workBeginTomorrow;
	}

	/**
	 * executes validation steps
	 * 
	 * @return a list of ValidationStates with VALID (means pass) or NOT_VALID+Failing outcome (means fail)
	 * @throws NullPointerException when no date is selected
	 */
	
	public ArrayList<ValidationState> validation() {
		var validation = new ArrayList<ValidationState>();
		try {
			var workBeginToday = LocalDateTime.of(selectedDay, workBegin);
			var workEndToday = LocalDateTime.of(selectedDay, workEnd);
			var workEndYesterdayLDT = LocalDateTime.of(selectedDay.minusDays(1), workEndYesterday);
			var workBeginTomorrowLDT = LocalDateTime.of(selectedDay.plusDays(1), workBeginTomorrow);
			validation.add(checkDurationBetweenWorkingDays(workEndYesterdayLDT,workBeginTomorrowLDT, workBeginToday, workEndToday));
		} catch (NullPointerException e) {
			validation.add(ValidationState.NOT_VALID_NO_DATE_SELECTED);
		}
		validation.add(checkInputExists(inputList));
		validation.add(checkTimeOrderIsChronological(inputList));
		validation.add(checkBreaksInWorkTime(inputList, workBegin, workEnd));
		validation.add(checkTotalBreakCompliance(timeAtBreak, legalBreak));
		validation.add(checkSingleBreakDurationCompliance(inputList, legalBreak));
		validation.add(checkDatepickerCompliance(selectedDay));
		validation.add(checkWorkTimeLimits(workBegin, workEnd));
		validation.add(checkTotalWorkingTimeOverTenHours(totalWorkingTime));
		
		//checkWorkingTimeOverSixHoursWithoutBreak only works with valid input
		var areAllPreviousValidationsValid = validation.stream().allMatch(elm -> elm.equals(ValidationState.VALID));
		if(areAllPreviousValidationsValid)
			validation.add(checkWorkingTimeOverSixHoursWithoutBreak(inputList));

		return validation;

	}
	
	/**
	 * validates, if duration between work begin of the selected working day and the work end of the previous day
	 * fulfill the legal requirements of rest period
	 * 2021, in Germany: 11 hours 
	 * 
	 * @param workEndYesterday end of working time the day before the selected working day in hh:mm 
	 * @param workBeginTomorrow begin of working time the day after the selected working day in hh:mm 
	 * @param workBeginToday begin of working time at the selected working day in hh:mm
	 * @param workEndToday end of working time at the selected working day in hh:mm
	 * 
	 * @return validation result as ValidationState of pass (VALID) or fail (NOT_VALID_DURATION_BETWEEN_WORKING_DAYS_ERROR)
	 * 
	 * @see ValidationState
	 */
	
	private ValidationState checkDurationBetweenWorkingDays(LocalDateTime workEndYesterday, LocalDateTime workBeginTomorrow,
			LocalDateTime workBeginToday, LocalDateTime workEndToday) {
		var duration = Duration.between(workEndYesterday, workBeginToday);
		var otherDuration = Duration.between(workEndToday, workBeginTomorrow);

		return (duration.minus(MIN_REQUIRED_DURATION_BETWEEN_WORKING_DAYS).isNegative() || otherDuration.minus(MIN_REQUIRED_DURATION_BETWEEN_WORKING_DAYS).isNegative())
				? ValidationState.NOT_VALID_DURATION_BETWEEN_WORKING_DAYS_ERROR
				: ValidationState.VALID;
	}

	/**
	 * validates, if total working duration fulfills the maximum amount of legally allowed working hours at one working day
	 * 2021, Germany: 10 hours
	 * 
	 * @param totalWorkingTime total working duration at a working day in PTnHnMnS (ISO-8601)
	 * 
	 * @return validation result as ValidationState of pass (VALID) or fail (NOT_VALID_WORKING_TIME_OVER_TEN_HOURS)
	 * 
	 * @see ValidationState
	 */
	
	private ValidationState checkTotalWorkingTimeOverTenHours(Duration totalWorkingTime) {
		
		return (totalWorkingTime.compareTo(MAX_DAILY_WORKING_TIME) > 0)
				? ValidationState.NOT_VALID_WORKING_TIME_OVER_TEN_HOURS
				: ValidationState.VALID;
	}

	/**
	 * validates, if work begin and/or work end exceeds operational working time frame (defined by company)
	 * 2021, AfS Berlin-Brandenburg: 6:30 - 19:30
	 * 
	 * @param workBegin begin of working time at working day in hh:mm
	 * @param workEnd end of working time at working day in hh:mm
	 * 
	 * @return validation result as ValidationState of pass (VALID) or fail (VALID_WORKBEGIN_IS_BEFORE_6_00 or
	 * VALID_WORKEND_IS_AFTER_19_30)
	 * 
	 * @see ValidationState
	 */
	
	private ValidationState checkWorkTimeLimits(LocalTime workBegin, LocalTime workEnd) {
		if (workBegin.isBefore(WORKING_LIMIT_BEGIN))
			return ValidationState.VALID_WORKBEGIN_IS_BEFORE_6_00;

		if (workEnd.isAfter(WORKING_LIMIT_END))
			return ValidationState.VALID_WORKEND_IS_AFTER_19_30;
		
		return ValidationState.VALID;
	}

	/**
	 * validates, if selected working day exceeds operating limits (defined by company)
	 * 2021, AfS Berlin-Brandenburg: not allows are dates in the future or dates earlier than 31 days in the past of the 
	 * actual calendar date
	 * 
	 * @param selectedDay date of considered working day for which the working day information should be calculated 
	 * and recorded in yyyy-mm-dd
	 * 
	 * @return validation result as ValidationState of pass (VALID) or fail (NOT_VALID_SELECTED_DAY_IS_IN_THE_FUTURE or
	 * NOT_VALID_SELECTED_DAY_IS_TO_FAR_IN_THE_PAST)
	 * 
	 * @see ValidationState
	 */
	
	private ValidationState checkDatepickerCompliance(LocalDate selectedDay) {
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

	/**
	 * validates, if single break durations fulfill a legally required single break duration.
	 * For understanding: total break duration is the sum of all single break durations which can be under, equal or 
	 * higher of the legal requirements. To count as legally valid single break duration, break duration must have or  
	 * exceed the given threshold.
	 * 2021, Germany: 15 minutes
	 * 
	 * @param formattedInput a list of working times, break times, work interruptions and break interruptions in Timespann
	 * @param legalBreak legally required minimum break duration based on working time in PTnHnMnS (ISO-8601)
	 * 
	 * @return validation result as ValidationState of pass (VALID) or fail (NOT_VALID_SINGLE_BREAK_DURATIONS_ERROR)
	 * 
	 * @see ValidationState
	 */
	
	private ValidationState checkSingleBreakDurationCompliance(ArrayList<Timespann> formattedInput,
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

	/**
	 * validates, if single breaks are within working durations.
	 * Ensures that the break's begin or end time doesn't match the exact work begin or work end of the working day.
	 * 
	 * @param formattedInput  a list of working times, break times, work interruptions and break interruptions in Timespann
	 * @param workBegin begin of working time at working day in hh:mm
	 * @param workEnd end of working time at working day in hh:mm
	 * 
	 * @return validation result as ValidationState of pass (VALID) or fail (NOT_VALID_BREAK_IS_NOT_IN_WORKTIME or 
	 * NOT_VALID_BREAK_CANNOT_BE_AT_WORKING_BEGIN_OR_END)
	 * 
	 * @see ValidationState
	 */
	
	private ValidationState checkBreaksInWorkTime(ArrayList<Timespann> formattedInput, LocalTime workBegin,
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

	/**
	 * validates, if the work and break input (by the user) is chronological (from earlier to later) and if the start time
	 * is always given before the end time (meaning also from earlier to later).
	 * 
	 * @param formattedInput a list of working times, break times, work interruptions and break interruptions in Timespann
	 * 
	 * @return validation result as ValidationState of pass (VALID) or fail (NOT_VALID_START_IS_AFTER_END or 
	 * NOT_VALID_TIME_INPUT_MUST_BE_IN_CHRONICAL_ORDER)
	 * 
	 * @see ValidationState
	 */
	
	private ValidationState checkTimeOrderIsChronological(ArrayList<Timespann> formattedInput) {
		if (formattedInput.stream().filter(elm -> (elm instanceof Interruption) || (elm instanceof BreakInterruption))
				.anyMatch(elm -> elm.getDuration().isNegative()))
			return ValidationState.NOT_VALID_TIME_INPUT_MUST_BE_IN_CHRONICAL_ORDER;
		else if (formattedInput.stream().anyMatch(elm -> elm.getDuration().isNegative()))
			return ValidationState.NOT_VALID_START_IS_AFTER_END;
		
		return ValidationState.VALID;
	}

	/**
	 * validates, if at least one full work input is given (meaning start and end time)
	 * 
	 * @param formattedInput a list of working times, break times, work interruptions and break interruptions in Timespann
	 * 
	 * @return validation result as ValidationState of pass (VALID) or fail (NOT_VALID_NO_INPUT_FOUND)
	 * 
	 * @see ValidationState
	 */
	
	private ValidationState checkInputExists(ArrayList<Timespann> formattedInput) {
		
		return (formattedInput.stream().noneMatch(elm -> elm instanceof Work))
				? ValidationState.NOT_VALID_NO_INPUT_FOUND
				: ValidationState.VALID;
	}

	/**
	 * validates, if total break duration fulfills the legal required break duration
	 * 
	 * @param timeAtBreak total break duration in PTnHnMnS (ISO-8601)
	 * @param legalBreak legally required minimum break duration based on working time in PTnHnMnS (ISO-8601)
	 * 
	 * @return validation result as ValidationState of pass (VALID) or fail (NOT_VALID_TOTAL_BREAK_ERROR)
	 * 
	 * @see ValidationState
	 */
	
	private ValidationState checkTotalBreakCompliance(Duration timeAtBreak, Duration legalBreak) {
		
		return timeAtBreak.minus(legalBreak).isNegative() 
				? ValidationState.NOT_VALID_TOTAL_BREAK_ERROR
				: ValidationState.VALID;
	}

	/**
	 * validates, if there is any work period without breaks or interruptions which are longer than a legally admitted 
	 * working duration without breaks or interruptions
	 * 2021, Germany: 6 hours of working time without break
	 * 
	 * @param formattedInput a list of working times, break times, work interruptions and break interruptions in Timespann
	 * 
	 * @return validation result as ValidationState of pass (VALID) or fail (NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR)
	 * 
	 * @see ValidationState
	 */
	
	private ValidationState checkWorkingTimeOverSixHoursWithoutBreak(ArrayList<Timespann> formattedInput) {
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
