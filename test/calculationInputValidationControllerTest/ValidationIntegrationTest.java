package test.calculationInputValidationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import controller.InputValidationController;
import models.*;
import models.time.Break;
import models.time.BreakInterruption;
import models.time.Interruption;
import models.time.Timespann;
import models.time.Work;

class ValidationIntegrationTest {

	private ArrayList<Timespann> addInputToList(ArrayList<Timespann> list, Timespann... timespann) {
		for (Timespann element : timespann) {
			list.add(element);
		}
		return list;
	}

	private ArrayList<ValidationState> addExpectations(ArrayList<ValidationState> list,
			ValidationState... expectation) {
		for (ValidationState element : expectation) {
			list.add(element);
		}
		return list;
	}

//-------- allInputIsValid Pass
	@Test
	void allInputsValid_Pass() {
		Work work = new Work(LocalTime.of(8, 0), LocalTime.of(13, 0));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, work);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ofMinutes(0), // legalBreak
				Duration.ofHours(5), // totalWorkingTime
				Duration.ZERO, // timeAtBreak
				LocalTime.of(8, 0), // workBegin
				LocalTime.of(13, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID, // checkTotalWorkingTimeOverTenHours
				ValidationState.VALID); // checkWorkingTimeOverSixHoursWithoutBreak););

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

//--------	noDateSelected
	@Test
	void noDateSelected_Fail() {
		Work work = new Work(LocalTime.of(8, 0), LocalTime.of(13, 0));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, work);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ofMinutes(0), // legalBreak
				Duration.ofHours(5), // totalWorkingTime
				Duration.ZERO, // timeAtBreak
				LocalTime.of(8, 0), // workBegin
				LocalTime.of(13, 0), // workEnd
				null, // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.NOT_VALID_NO_DATE_SELECTED, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.NOT_VALID_NO_DATE_SELECTED, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

//-------- no Input in WorkTime
	@Test
	void noInputList_Fail() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ofMinutes(0), // legalBreak
				Duration.ofHours(0), // totalWorkingTime
				Duration.ZERO, // timeAtBreak
				LocalTime.of(6, 0), // workBegin
				LocalTime.of(10, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.NOT_VALID_NO_INPUT_FOUND, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

//-------- ChronologicalTimeOrder
	@Test
	void workTimeOrderNotChronological_Fail() {
		Work work1 = new Work(LocalTime.of(16, 0), LocalTime.of(17, 0));
		Work work2 = new Work(LocalTime.of(8, 0), LocalTime.of(12, 0));
		Interruption interruption = new Interruption(work1.getEnd(), work2.getBegin());
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, work2, work1, interruption);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ofMinutes(0), // legalBreak
				Duration.ofHours(5), // totalWorkingTime
				Duration.ZERO, // timeAtBreak
				LocalTime.of(8, 0), // workBegin
				LocalTime.of(13, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.NOT_VALID_TIME_INPUT_MUST_BE_IN_CHRONICAL_ORDER, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

	@Test
	void BreakTimeOrderNotChronological_Fail() {
		Work work1 = new Work(LocalTime.of(7, 0), LocalTime.of(18, 0));
		Break break1 = new Break(LocalTime.of(16, 0), LocalTime.of(17, 0));
		Break break2 = new Break(LocalTime.of(10, 0), LocalTime.of(11, 0));
		BreakInterruption breakInterruption = new BreakInterruption(break1.getEnd(), break2.getBegin());
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, break1, work1, break2, breakInterruption);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ofMinutes(45), // legalBreak
				Duration.ofHours(9), // totalWorkingTime
				Duration.ofHours(2), // timeAtBreak
				LocalTime.of(7, 0), // workBegin
				LocalTime.of(18, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.NOT_VALID_TIME_INPUT_MUST_BE_IN_CHRONICAL_ORDER, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

	@Test
	void chronologicalInputButEndTimeBeforeStartTimeWork_Fail() {
		Work work1 = new Work(LocalTime.of(10, 0), LocalTime.of(12, 0));
		Work work2 = new Work(LocalTime.of(14, 0), LocalTime.of(13, 0));
		Interruption interruption = new Interruption(work1.getEnd(), work2.getBegin());
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, work1, work2, interruption);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ZERO, // legalBreak
				Duration.ofHours(4), // totalWorkingTime
				Duration.ofHours(1), // timeAtBreak
				LocalTime.of(10, 0), // workBegin
				LocalTime.of(14, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.NOT_VALID_START_IS_AFTER_END, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

	@Test
	void chronologicalInputButEndTimeBeforeStartTimeBreak_Fail() {
		Work work1 = new Work(LocalTime.of(10, 0), LocalTime.of(15, 0));
		Break break1 = new Break(LocalTime.of(11, 0), LocalTime.of(12, 0));
		Break break2 = new Break(LocalTime.of(14, 0), LocalTime.of(13, 0));
		BreakInterruption breakInterruption = new BreakInterruption(break1.getEnd(), break2.getBegin());
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, break1, break2, work1, breakInterruption);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ZERO, // legalBreak
				Duration.ofHours(5), // totalWorkingTime
				Duration.ZERO, // timeAtBreak
				LocalTime.of(10, 0), // workBegin
				LocalTime.of(15, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.NOT_VALID_START_IS_AFTER_END, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

//-------- Break In WorkTime	
	@Test
	void breakIsNotInWorkTime_Fail() {
		Work work1 = new Work(LocalTime.of(10, 0), LocalTime.of(15, 0));
		Break break1 = new Break(LocalTime.of(8, 0), LocalTime.of(9, 0));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, break1, work1);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ZERO, // legalBreak
				Duration.ofHours(5), // totalWorkingTime
				Duration.ofHours(1), // timeAtBreak
				LocalTime.of(10, 0), // workBegin
				LocalTime.of(15, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.NOT_VALID_BREAK_IS_NOT_IN_WORKTIME, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

	@Test
	void breakIsatWorkBegin_Fail() {
		Work work1 = new Work(LocalTime.of(10, 0), LocalTime.of(15, 0));
		Break break1 = new Break(LocalTime.of(10, 0), LocalTime.of(11, 0));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, break1, work1);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ZERO, // legalBreak
				Duration.ofHours(5), // totalWorkingTime
				Duration.ofHours(1), // timeAtBreak
				LocalTime.of(10, 0), // workBegin
				LocalTime.of(15, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.NOT_VALID_BREAK_CANNOT_BE_AT_WORKING_BEGIN_OR_END, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

	@Test
	void breakIsatWorkEnd_Fail() {
		Work work1 = new Work(LocalTime.of(10, 0), LocalTime.of(15, 0));
		Break break1 = new Break(LocalTime.of(14, 0), LocalTime.of(15, 0));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, break1, work1);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ZERO, // legalBreak
				Duration.ofHours(4), // totalWorkingTime
				Duration.ofHours(1), // timeAtBreak
				LocalTime.of(10, 0), // workBegin
				LocalTime.of(15, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.NOT_VALID_BREAK_CANNOT_BE_AT_WORKING_BEGIN_OR_END, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

//-------- TotalBreak
	@Test
	void notTotalBreakCompliantForLegalBreakRequirementOf30Minutes_alsoSingleBreakError_legalBreakIsHigherThanActualBreak_Fail() {
		Work work1 = new Work(LocalTime.of(10, 0), LocalTime.of(17, 0));
		Break break1 = new Break(LocalTime.of(11, 0), LocalTime.of(11, 29));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, break1, work1);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ofMinutes(30), // legalBreak
				Duration.ofHours(5), // totalWorkingTime
				Duration.ofMinutes(29), // timeAtBreak
				LocalTime.of(10, 0), // workBegin
				LocalTime.of(17, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.NOT_VALID_TOTAL_BREAK_ERROR, // checkTotalBreakCompliance
				ValidationState.NOT_VALID_SINGLE_BREAK_DURATIONS_ERROR, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

	@Test
	void notTotalBreakCompliantForLegalBreakRequirementOf45Minutes_alsoSingleBreakError_legalBreakIsHigherThanActualBreak_Fail() {
		Work work1 = new Work(LocalTime.of(10, 0), LocalTime.of(17, 0));
		Break break1 = new Break(LocalTime.of(11, 0), LocalTime.of(11, 44));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, break1, work1);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ofMinutes(45), // legalBreak
				Duration.ofHours(5), // totalWorkingTime
				Duration.ofMinutes(44), // timeAtBreak
				LocalTime.of(10, 0), // workBegin
				LocalTime.of(17, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.NOT_VALID_TOTAL_BREAK_ERROR, // checkTotalBreakCompliance
				ValidationState.NOT_VALID_SINGLE_BREAK_DURATIONS_ERROR, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

//-------- SingleBreak	
	@Test
	void SingleBreakErrorForLegalBreak45Minutes_TotalBreakOverLegalBreakButAtLeastOneSingleBreakUnder15MinutesToReachLegalBreakRequirement_Fail() {
		Work work1 = new Work(LocalTime.of(10, 0), LocalTime.of(17, 0));
		Break break1 = new Break(LocalTime.of(11, 0), LocalTime.of(11, 15));
		Break break2 = new Break(LocalTime.of(12, 0), LocalTime.of(12, 25));
		Break break3 = new Break(LocalTime.of(13, 0), LocalTime.of(13, 5));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, break1, break2, break3, work1);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ofMinutes(45), // legalBreak
				Duration.parse("PT6H15M"), // totalWorkingTime
				Duration.ofMinutes(45), // timeAtBreak
				LocalTime.of(10, 0), // workBegin
				LocalTime.of(17, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.NOT_VALID_SINGLE_BREAK_DURATIONS_ERROR, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

	@Test
	void SingleBreakErrorForLegalBreak30Minutes_TotalBreakOverLegalBreakButAtLeastOneSingleBreakUnder15MinutesToReachLegalBreakRequirement_Fail() {
		Work work1 = new Work(LocalTime.of(10, 0), LocalTime.of(17, 0));
		Break break1 = new Break(LocalTime.of(11, 0), LocalTime.of(11, 15));
		Break break2 = new Break(LocalTime.of(12, 0), LocalTime.of(12, 10));
		Break break3 = new Break(LocalTime.of(13, 0), LocalTime.of(13, 5));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, break1, break2, break3, work1);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ofMinutes(30), // legalBreak
				Duration.parse("PT6H30M"), // totalWorkingTime
				Duration.ofMinutes(30), // timeAtBreak
				LocalTime.of(10, 0), // workBegin
				LocalTime.of(17, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.NOT_VALID_SINGLE_BREAK_DURATIONS_ERROR, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

//-------- Datepicker
	@Test
	void selectedDateIs1DayInTheFuture_Fail() {
		Work work1 = new Work(LocalTime.of(10, 0), LocalTime.of(17, 0));
		Break break1 = new Break(LocalTime.of(11, 0), LocalTime.of(11, 30));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, break1, work1);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ofMinutes(30), // legalBreak
				Duration.parse("PT6H30M"), // totalWorkingTime
				Duration.ofMinutes(30), // timeAtBreak
				LocalTime.of(10, 0), // workBegin
				LocalTime.of(17, 0), // workEnd
				LocalDate.now().plusDays(1), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.NOT_VALID_SELECTED_DAY_IS_IN_THE_FUTURE, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

	@Test
	void selectedDateIs32DayInThePast_Fail() {
		Work work1 = new Work(LocalTime.of(10, 0), LocalTime.of(17, 0));
		Break break1 = new Break(LocalTime.of(11, 0), LocalTime.of(11, 30));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, break1, work1);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ofMinutes(30), // legalBreak
				Duration.parse("PT6H30M"), // totalWorkingTime
				Duration.ofMinutes(30), // timeAtBreak
				LocalTime.of(10, 0), // workBegin
				LocalTime.of(17, 0), // workEnd
				LocalDate.now().minusDays(32), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.NOT_VALID_SELECTED_DAY_IS_TO_FAR_IN_THE_PAST, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

//-------- WorkTimeLimits
	@Test
	void workBeginExceedsWorkTimeLimits_Fail() {
		Work work1 = new Work(LocalTime.of(5, 0), LocalTime.of(10, 0));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, work1);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ZERO, // legalBreak
				Duration.ofHours(5), // totalWorkingTime
				Duration.ZERO, // timeAtBreak
				LocalTime.of(5, 0), // workBegin
				LocalTime.of(10, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID_WORKBEGIN_IS_BEFORE_6_00, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

	@Test
	void workEndExceedsWorkTimeLimits_Fail() {
		Work work1 = new Work(LocalTime.of(15, 0), LocalTime.of(20, 0));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, work1);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ZERO, // legalBreak
				Duration.ofHours(5), // totalWorkingTime
				Duration.ZERO, // timeAtBreak
				LocalTime.of(15, 0), // workBegin
				LocalTime.of(20, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID_WORKEND_IS_AFTER_19_30, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

//-------- WorkTimeOver10Hours
	@Test
	void TotalWorkingTimeOver10Hours_Fail() {
		Work work1 = new Work(LocalTime.of(8, 0), LocalTime.of(18, 45));
		Break break1 = new Break(LocalTime.of(13, 0), LocalTime.of(13, 30));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, work1, break1);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ofMinutes(30), // legalBreak
				Duration.parse("PT10H15M"), // totalWorkingTime
				Duration.ofMinutes(30), // timeAtBreak
				LocalTime.of(8, 0), // workBegin
				LocalTime.of(18, 45), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.NOT_VALID_WORKING_TIME_OVER_TEN_HOURS); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

//-------- WorkingTimeOver6HoursWithoutBreak	
	@Test
	void WorkingTimeOver6HoursWithoutBreakAtWorkBeginPart_workingSegmentOfWorkInputAtBeginToLong_Fail() {
		Work work1 = new Work(LocalTime.of(8, 0), LocalTime.of(18, 0));
		Break break1 = new Break(LocalTime.of(15, 0), LocalTime.of(16, 00));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, work1, break1);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ofMinutes(45), // legalBreak
				Duration.ofHours(9), // totalWorkingTime
				Duration.ofHours(1), // timeAtBreak
				LocalTime.of(8, 0), // workBegin
				LocalTime.of(18, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID, // checkTotalWorkingTimeOverTenHours
				ValidationState.NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR); // checkWorkingTimeOverSixHoursWithoutBreak

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

	@Test
	void WorkingTimeOver6HoursWithoutBreakAtWorkEndPart_workingSegmentOfWorkInputAtEndToLong_Fail() {
		Work work1 = new Work(LocalTime.of(8, 0), LocalTime.of(18, 0));
		Break break1 = new Break(LocalTime.of(9, 0), LocalTime.of(10, 0));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, work1, break1);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ofMinutes(45), // legalBreak
				Duration.ofHours(9), // totalWorkingTime
				Duration.ofHours(1), // timeAtBreak
				LocalTime.of(8, 0), // workBegin
				LocalTime.of(18, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID, // checkTotalWorkingTimeOverTenHours
				ValidationState.NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR); // checkWorkingTimeOverSixHoursWithoutBreak

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

	// -------- Duration between working days
	@Test
	void workBeginToday8AndWorkEndYesterday21_01_durationBetweenWorkingDaysIs10H59MHoursAndLessThanRequired_Fail() {
		Work work = new Work(LocalTime.of(8, 0), LocalTime.of(13, 0));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, work);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ofMinutes(0), // legalBreak
				Duration.ofHours(5), // totalWorkingTime
				Duration.ZERO, // timeAtBreak
				LocalTime.of(8, 0), // workBegin
				LocalTime.of(13, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(21, 1), // workEndYesterday
				LocalTime.of(23, 59)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations,
				ValidationState.NOT_VALID_DURATION_BETWEEN_WORKING_DAYS_ERROR, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours
		// checkWorkingTimeOverSixHoursWithoutBreak););

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

	@Test
	void workBeginToday8AndWorkEndYesterday21_durationBetweenWorkingDaysIs11HoursAndEqualsRequired_Pass() {
		Work work = new Work(LocalTime.of(8, 0), LocalTime.of(13, 0));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, work);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ofMinutes(0), // legalBreak
				Duration.ofHours(5), // totalWorkingTime
				Duration.ZERO, // timeAtBreak
				LocalTime.of(8, 0), // workBegin
				LocalTime.of(13, 0), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(21, 0), // workEndYesterday
				LocalTime.of(23, 49)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID, // checkTotalWorkingTimeOverTenHours
				ValidationState.VALID); // checkWorkingTimeOverSixHoursWithoutBreak););

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

	@Test
	void workBeginToday8_01_AndWorkEndYesterday21_durationBetweenWorkingDaysIs11H01MHoursAndIsMoreThanRequired_Pass() {
		Work work = new Work(LocalTime.of(8, 1), LocalTime.of(13, 1));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, work);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ofMinutes(0), // legalBreak
				Duration.ofHours(5), // totalWorkingTime
				Duration.ZERO, // timeAtBreak
				LocalTime.of(8, 1), // workBegin
				LocalTime.of(13, 1), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(21, 0), // workEndYesterday
				LocalTime.of(23, 59)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID, // checkTotalWorkingTimeOverTenHours
				ValidationState.VALID); // checkWorkingTimeOverSixHoursWithoutBreak););

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

	@Test
	void workEndToday19_30_AndWorkEndTomorrow6_29_durationBetweenWorkingDaysIs10H59MAndIsLessThanRequired_Fail() {
		Work work = new Work(LocalTime.of(16, 30), LocalTime.of(19, 30));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, work);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ZERO, // legalBreak
				Duration.ofHours(3), // totalWorkingTime
				Duration.ZERO, // timeAtBreak
				LocalTime.of(16, 30), // workBegin
				LocalTime.of(19, 30), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(6, 29)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations,
				ValidationState.NOT_VALID_DURATION_BETWEEN_WORKING_DAYS_ERROR, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID); // checkTotalWorkingTimeOverTenHours

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

	@Test
	void workEndToday19_30_AndWorkEndTomorrow6_30_durationBetweenWorkingDaysIs11HoursAndEqualsRequired_Pass() {
		Work work = new Work(LocalTime.of(16, 30), LocalTime.of(19, 30));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, work);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ZERO, // legalBreak
				Duration.ofHours(3), // totalWorkingTime
				Duration.ZERO, // timeAtBreak
				LocalTime.of(16, 30), // workBegin
				LocalTime.of(19, 30), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(6, 30)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID, // checkTotalWorkingTimeOverTenHours
				ValidationState.VALID); // checkWorkingTimeOverSixHoursWithoutBreak););

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}

	@Test
	void workEndToday19_30_AndWorkEndTomorrow6_31_durationBetweenWorkingDaysIs11HM01HoursAndMoreThanRequired_Pass() {
		Work work = new Work(LocalTime.of(16, 30), LocalTime.of(19, 30));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, work);
		InputValidationController inputValidationController = new InputValidationController(list, // list
				Duration.ZERO, // legalBreak
				Duration.ofHours(3), // totalWorkingTime
				Duration.ZERO, // timeAtBreak
				LocalTime.of(16, 30), // workBegin
				LocalTime.of(19, 30), // workEnd
				LocalDate.now(), // selectedDay
				LocalTime.of(0, 0), // workEndYesterday
				LocalTime.of(6, 31)); // workBeginTomorrow
		ArrayList<ValidationState> expecteValidations = new ArrayList<ValidationState>();
		expecteValidations = addExpectations(expecteValidations, ValidationState.VALID, // checkDurationBetweenWorkingDays
				ValidationState.VALID, // checkInputExists
				ValidationState.VALID, // checkTimeOrderIsChronological
				ValidationState.VALID, // checkBreaksInWorkTime
				ValidationState.VALID, // checkTotalBreakCompliance
				ValidationState.VALID, // checkSingleBreakDurationCompliance
				ValidationState.VALID, // checkDatepickerCompliance
				ValidationState.VALID, // checkWorkTimeLimits
				ValidationState.VALID, // checkTotalWorkingTimeOverTenHours
				ValidationState.VALID); // checkWorkingTimeOverSixHoursWithoutBreak););

		ArrayList<ValidationState> validationAll = inputValidationController.validation();

		assertEquals(expecteValidations, validationAll);
	}
}