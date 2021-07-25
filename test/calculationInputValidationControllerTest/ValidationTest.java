package test.calculationInputValidationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import controller.InputValidationController;
import models.*;

class ValidationTest {
	
	private static final String VALID = 
			"VALID";
	private static final String	VALID_WORKBEGIN_IS_BEFORE_6_00 = 
			"VALID_WORKBEGIN_IS_BEFORE_6_00";
	private static final String	VALID_WORKEND_IS_AFTER_19_30 = 
			"VALID_WORKEND_IS_AFTER_19_30";
	private static final String	NOT_VALID_WORKING_TIME_OVER_TEN_HOURS = 
			"NOT_VALID_WORKING_TIME_OVER_TEN_HOURS";
	private static final String	NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR = 
			"NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR";
	private static final String	NOT_VALID_NO_INPUT_FOUND = 
			"NOT_VALID_NO_INPUT_FOUND";
	private static final String	NOT_VALID_START_IS_AFTER_END = 
			"NOT_VALID_START_IS_AFTER_END";
	private static final String	NOT_VALID_TIME_INPUT_MUST_BE_IN_CHRONICAL_ORDER = 
			"NOT_VALID_TIME_INPUT_MUST_BE_IN_CHRONICAL_ORDER";
	private static final String	NOT_VALID_BREAK_CANNOT_BE_AT_WORKING_BEGIN_OR_END = 
			"NOT_VALID_BREAK_CANNOT_BE_AT_WORKING_BEGIN_OR_END";
	private static final String	NOT_VALID_BREAK_IS_NOT_IN_WORKTIME = 
			"NOT_VALID_BREAK_IS_NOT_IN_WORKTIME";
	private static final String NOT_VALID_TOTAL_BREAK_ERROR = 
			"NOT_VALID_TOTAL_BREAK_ERROR";
	private static final String	NOT_VALID_SINGLE_BREAK_DURATIONS_ERROR = 
			"NOT_VALID_SINGLE_BREAK_DURATIONS_ERROR";
	private static final String	NOT_VALID_NO_DATE_SELECTED = 
			"NOT_VALID_NO_DATE_SELECTED";
	private static final String NOT_VALID_SELECTED_DAY_IS_IN_THE_FUTURE = 
			"NOT_VALID_SELECTED_DAY_IS_IN_THE_FUTURE";
	private static final String	NOT_VALID_SELECTED_DAY_IS_TO_FAR_IN_THE_PAST = 
			"NOT_VALID_SELECTED_DAY_IS_TO_FAR_IN_THE_PAST";
	private static final String NOT_VALID_DURATION_BETWEEN_WORKING_DAYS_ERROR  = 
			"NOT_VALID_DURATION_BETWEEN_WORKING_DAYS_ERROR";
	
	private ArrayList<Timespann> addInputToList(ArrayList<Timespann> list, Timespann... timespann) {
		for (Timespann element : timespann) {
			list.add(element);
		}
		return list;
	}
	
	private ArrayList<String> addExpectations(ArrayList<String> list, String... expectation) {
		for (String element : expectation) {
			list.add(element);
		}
		return list;
	}
		
	@Test
	void allInputsValid_Pass() {
		Work work = new Work(LocalTime.of(8,0),LocalTime.of(13,0));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, work);
		InputValidationController inputValidationController = new InputValidationController(
				list, 					//list
				Duration.ofMinutes(0), 	//legalBreak
				Duration.ofHours(5), 	//totalWorkingTime
				Duration.ZERO, 			//timeAtBreak
				LocalTime.of(8,0), 		//workBegin
				LocalTime.of(13,0), 	//workEnd
				LocalDate.now(), 		// selectedDay
				LocalTime.of(13,0));  	//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, VALID, VALID, VALID, VALID, VALID, VALID, VALID, VALID, VALID, VALID);
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}
//--------	
	@Test
	void noDateSelected_Fail() {
		Work work = new Work(LocalTime.of(8,0),LocalTime.of(13,0));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, work);
		InputValidationController inputValidationController = new InputValidationController(
				list, 					//list
				Duration.ofMinutes(0), 	//legalBreak
				Duration.ofHours(5), 	//totalWorkingTime
				Duration.ZERO, 			//timeAtBreak
				LocalTime.of(8,0), 		//workBegin
				LocalTime.of(13,0), 	//workEnd
				null, 					// selectedDay
				LocalTime.of(13,0));  	//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, NOT_VALID_NO_DATE_SELECTED, VALID, VALID, VALID, VALID, VALID, NOT_VALID_NO_DATE_SELECTED, VALID, VALID, VALID);
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}
//--------	
	@Test
	void noInputList_Fail() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		InputValidationController inputValidationController = new InputValidationController(
				list, 					//list
				Duration.ofMinutes(0), 	//legalBreak
				Duration.ofHours(0), 	//totalWorkingTime
				Duration.ZERO, 			//timeAtBreak
				LocalTime.of(6,0), 		//workBegin
				LocalTime.of(10,0), 	//workEnd
				LocalDate.now(), 		// selectedDay
				LocalTime.of(13,0));  	//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, VALID, NOT_VALID_NO_INPUT_FOUND, VALID, VALID, VALID, VALID, VALID, VALID, VALID, VALID);
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}
//--------
	@Test
	void workTimeOrderNotChronological_Fail() {
		Work work1 = new Work(LocalTime.of(16,0),LocalTime.of(17,0));
		Work work2 = new Work(LocalTime.of(8,0),LocalTime.of(12,0));
		Interruption interruption = new Interruption(work1.getEnd(),work2.getBegin());
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, work2, work1, interruption);
		InputValidationController inputValidationController = new InputValidationController(
				list, 					//list
				Duration.ofMinutes(0), 	//legalBreak
				Duration.ofHours(5), 	//totalWorkingTime
				Duration.ZERO, 			//timeAtBreak
				LocalTime.of(8,0), 		//workBegin
				LocalTime.of(13,0), 	//workEnd
				LocalDate.now(), 		// selectedDay
				LocalTime.of(13,0));  	//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, VALID, VALID, NOT_VALID_TIME_INPUT_MUST_BE_IN_CHRONICAL_ORDER, VALID, VALID, VALID, VALID, VALID, VALID, VALID);
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}
	
	@Test
	void BreakTimeOrderNotChronological_Fail() {
		Work work1 = new Work(LocalTime.of(7,0),LocalTime.of(18,0));
		Break break1 = new Break(LocalTime.of(16,0),LocalTime.of(17,0));
		Break break2 = new Break(LocalTime.of(10,0),LocalTime.of(11,0));
		BreakInterruption breakInterruption = new BreakInterruption(break1.getEnd(),break2.getBegin());
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, break1, work1, break2, breakInterruption);
		InputValidationController inputValidationController = new InputValidationController(
				list, 					//list
				Duration.ofMinutes(45), //legalBreak
				Duration.ofHours(9), 	//totalWorkingTime
				Duration.ofHours(2), 	//timeAtBreak
				LocalTime.of(7,0), 		//workBegin
				LocalTime.of(18,0), 	//workEnd
				LocalDate.now(), 		// selectedDay
				LocalTime.of(13,0));  	//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, VALID, VALID, NOT_VALID_TIME_INPUT_MUST_BE_IN_CHRONICAL_ORDER, VALID, VALID, VALID, VALID, VALID, VALID, VALID);
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}
	
//	@Test
//	void chronologicalInputButEndTimeBeforeStartTime_Fail() {
//		Work work1 = new Work(LocalTime.of(10,0),LocalTime.of(12,0));
//		Work work2 = new Work(LocalTime.of(14,0),LocalTime.of(13,0));
//		Interruption interruption = new Interruption(work1.getEnd(),work2.getBegin());
//		
//		Break break1 = new Break(LocalTime.of(10,15),LocalTime.of(10,30));
//		Break break2 = new Break(LocalTime.of(13,15),LocalTime.of(13,30));
//		BreakInterruption breakInterruption = new BreakInterruption(break1.getEnd(), break2.getBegin());
//		
//		
//		ArrayList<Timespann> list = new ArrayList<Timespann>();
//		addInputToList(list, work1, work2, break1, break2, breakInterruption, interruption);
//		InputValidationController inputValidationController = new InputValidationController(
//				list, 					//list
//				Duration.ZERO, 			//legalBreak
//				Duration.ofHours(3), 	//totalWorkingTime
//				Duration.ofHours(1), 	//timeAtBreak
//				LocalTime.of(11,0), 	//workBegin
//				LocalTime.of(14,0), 	//workEnd
//				LocalDate.now(), 		// selectedDay
//				LocalTime.of(13,0));  	//workEndYesterday
//		ArrayList<String> expecteValidations = new ArrayList<String>();
//		addExpectations(expecteValidations, VALID, VALID, NOT_VALID_START_IS_AFTER_END, VALID, VALID, VALID, VALID, VALID, VALID, VALID);
//		String validationAll = inputValidationController.validation().toString();	
//		
//		assertEquals(expecteValidations.toString(),validationAll);
//	}

//	@Test
//	void chronologicalInputButEndTimeBeforeStartTime_Fail() {
//		Work work1 = new Work(LocalTime.of(8,0),LocalTime.of(9,0));
//		Work work2 = new Work(LocalTime.of(12,0),LocalTime.of(10,0));
//		Interruption interruption = new Interruption(work1.getEnd(),work2.getBegin());
//				
//		ArrayList<Timespann> list = new ArrayList<Timespann>();
//		addInputToList(list, work1, work2, interruption);
//		InputValidationController inputValidationController = new InputValidationController(
//				list, 					//list
//				Duration.ZERO, 			//legalBreak
//				Duration.ofHours(2), 	//totalWorkingTime
//				Duration.ZERO, 			//timeAtBreak
//				LocalTime.of(10,0), 	//workBegin
//				LocalTime.of(12,0), 	//workEnd
//				LocalDate.now(), 		// selectedDay
//				LocalTime.of(13,0));  	//workEndYesterday
//		ArrayList<String> expecteValidations = new ArrayList<String>();
//		addExpectations(expecteValidations, VALID, VALID, NOT_VALID_START_IS_AFTER_END, VALID, VALID, VALID, VALID, VALID, VALID, VALID);
//		ArrayList<ValidationState> validationAll = inputValidationController.validation();	
//		
//		assertEquals(expecteValidations.toString(),validationAll);
//	}
	
	//---------
	// end before date issue fehlt einfach
	//---------
	
//--------	
	@Test
	void breakIsNotInWorkTime_Fail(){
		Work work1 = new Work(LocalTime.of(10,0),LocalTime.of(15,0));
		Break break1 = new Break(LocalTime.of(8,0),LocalTime.of(9,0));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, break1, work1);
		InputValidationController inputValidationController = new InputValidationController(
				list, 					//list
				Duration.ZERO, //legalBreak
				Duration.ofHours(5), 	//totalWorkingTime
				Duration.ofHours(1), 	//timeAtBreak
				LocalTime.of(10,0), 	//workBegin
				LocalTime.of(15,0), 	//workEnd
				LocalDate.now(), 		// selectedDay
				LocalTime.of(13,0));  	//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, VALID, VALID, VALID, NOT_VALID_BREAK_IS_NOT_IN_WORKTIME, VALID, VALID, VALID, VALID, VALID, VALID);
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}
	
	@Test
	void breakIsatWorkBegin_Fail(){
		Work work1 = new Work(LocalTime.of(10,0),LocalTime.of(15,0));
		Break break1 = new Break(LocalTime.of(10,0),LocalTime.of(11,0));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, break1, work1);
		InputValidationController inputValidationController = new InputValidationController(
				list, 					//list
				Duration.ZERO, 			//legalBreak
				Duration.ofHours(5), 	//totalWorkingTime
				Duration.ofHours(1), 	//timeAtBreak
				LocalTime.of(10,0), 	//workBegin
				LocalTime.of(15,0), 	//workEnd
				LocalDate.now(), 		// selectedDay
				LocalTime.of(13,0));  	//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, VALID, VALID, VALID, NOT_VALID_BREAK_CANNOT_BE_AT_WORKING_BEGIN_OR_END, VALID, VALID, VALID, VALID, VALID, VALID);
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}
	
//	@Test
//	void breakIsatWorkEnd_Fail(){
//		Work work1 = new Work(LocalTime.of(10,0),LocalTime.of(15,0));
//		Break break1 = new Break(LocalTime.of(14,0),LocalTime.of(15,0));
//		ArrayList<Timespann> list = new ArrayList<Timespann>();
//		addInputToList(list, break1, work1);
//		InputValidationController inputValidationController = new InputValidationController(
//				list, 					//list
//				Duration.ZERO, 			//legalBreak
//				Duration.ofHours(5), 	//totalWorkingTime
//				Duration.ofHours(1), 	//timeAtBreak
//				LocalTime.of(10,0), 	//workBegin
//				LocalTime.of(15,0), 	//workEnd
//				LocalDate.now(), 		// selectedDay
//				LocalTime.of(13,0));  	//workEndYesterday
//		ArrayList<String> expecteValidations = new ArrayList<String>();
//		addExpectations(expecteValidations, VALID, VALID, VALID, NOT_VALID_BREAK_CANNOT_BE_AT_WORKING_BEGIN_OR_END, VALID, VALID, VALID, VALID, VALID, VALID);
//		String validationAll = inputValidationController.validation().toString();
//		
//		assertEquals(expecteValidations.toString(),validationAll);
//	}

	
//-------- TotalBreak
	@Test
	void notTotalBreakCompliantForLegalBreakRequirementOf30Minutes_alsoSingleBreakError_legalBreakIsHigherThanActualBreak_Fail(){
		Work work1 = new Work(LocalTime.of(10,0),LocalTime.of(17,0));
		Break break1 = new Break(LocalTime.of(11,0),LocalTime.of(11,29));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, break1, work1);
		InputValidationController inputValidationController = new InputValidationController(
				list, 					//list
				Duration.ofMinutes(30), //legalBreak
				Duration.ofHours(5), 	//totalWorkingTime
				Duration.ofMinutes(29), //timeAtBreak
				LocalTime.of(10,0), 	//workBegin
				LocalTime.of(17,0), 	//workEnd
				LocalDate.now(), 		// selectedDay
				LocalTime.of(13,0));  	//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, 
				VALID, // checkDurationBetweenWorkingDays
				VALID, // checkInputExists
				VALID, // checkTimeOrderIsChronological
				VALID, // checkBreaksInWorkTime
				NOT_VALID_TOTAL_BREAK_ERROR, // checkTotalBreakCompliance
				NOT_VALID_SINGLE_BREAK_DURATIONS_ERROR, // checkSingleBreakDurationCompliance
				VALID, // checkDatepickerCompliance
				VALID, // checkWorkTimeLimits
				VALID, // checkTotalWorkingTimeOverTenHours
				VALID); // checkWorkingTimeOverSixHoursWithoutBreak);
		
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}
	
	@Test
	void notTotalBreakCompliantForLegalBreakRequirementOf45Minutes_alsoSingleBreakError_legalBreakIsHigherThanActualBreak_Fail(){
		Work work1 = new Work(LocalTime.of(10,0),LocalTime.of(17,0));
		Break break1 = new Break(LocalTime.of(11,0),LocalTime.of(11,44));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, break1, work1);
		InputValidationController inputValidationController = new InputValidationController(
				list, 					//list
				Duration.ofMinutes(45), //legalBreak
				Duration.ofHours(5), 	//totalWorkingTime
				Duration.ofMinutes(44), //timeAtBreak
				LocalTime.of(10,0), 	//workBegin
				LocalTime.of(17,0), 	//workEnd
				LocalDate.now(), 		// selectedDay
				LocalTime.of(13,0));  	//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, 
				VALID, // checkDurationBetweenWorkingDays
				VALID, // checkInputExists
				VALID, // checkTimeOrderIsChronological
				VALID, // checkBreaksInWorkTime
				NOT_VALID_TOTAL_BREAK_ERROR, // checkTotalBreakCompliance
				NOT_VALID_SINGLE_BREAK_DURATIONS_ERROR, // checkSingleBreakDurationCompliance
				VALID, // checkDatepickerCompliance
				VALID, // checkWorkTimeLimits
				VALID, // checkTotalWorkingTimeOverTenHours
				VALID); // checkWorkingTimeOverSixHoursWithoutBreak
		
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}
//-------- SingleBreak	
	@Test
	void SingleBreakErrorForLegalBreak45Minutes_TotalBreakOverLegalBreakButAtLeastOneSingleBreakUnder15MinutesToReachLegalBreakRequirement_Fail(){
		Work work1 = new Work(LocalTime.of(10,0),LocalTime.of(17,0));
		Break break1 = new Break(LocalTime.of(11,0),LocalTime.of(11,15));
		Break break2 = new Break(LocalTime.of(12,0),LocalTime.of(12,25));
		Break break3 = new Break(LocalTime.of(13,0),LocalTime.of(13,5));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, break1, break2, break3, work1);
		InputValidationController inputValidationController = new InputValidationController(
				list, 					//list
				Duration.ofMinutes(45), //legalBreak
				Duration.parse("PT6H15M"), 	//totalWorkingTime
				Duration.ofMinutes(45),//timeAtBreak
				LocalTime.of(10,0), 	//workBegin
				LocalTime.of(17,0), 	//workEnd
				LocalDate.now(), 		// selectedDay
				LocalTime.of(13,0));  	//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, 
				VALID, // checkDurationBetweenWorkingDays
				VALID, // checkInputExists
				VALID, // checkTimeOrderIsChronological
				VALID, // checkBreaksInWorkTime
				VALID, // checkTotalBreakCompliance
				VALID, // checkSingleBreakDurationCompliance
				VALID, // checkDatepickerCompliance
				VALID, // checkWorkTimeLimits
				VALID, // checkTotalWorkingTimeOverTenHours
				VALID); // checkWorkingTimeOverSixHoursWithoutBreak
		
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}
	
	@Test
	void SingleBreakErrorForLegalBreak30Minutes_TotalBreakOverLegalBreakButAtLeastOneSingleBreakUnder15MinutesToReachLegalBreakRequirement_Fail(){
		Work work1 = new Work(LocalTime.of(10,0),LocalTime.of(17,0));
		Break break1 = new Break(LocalTime.of(11,0),LocalTime.of(11,15));
		Break break2 = new Break(LocalTime.of(12,0),LocalTime.of(12,10));
		Break break3 = new Break(LocalTime.of(13,0),LocalTime.of(13,5));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, break1, break2, break3, work1);
		InputValidationController inputValidationController = new InputValidationController(
				list, 						//list
				Duration.ofMinutes(30), 	//legalBreak
				Duration.parse("PT6H30M"), 	//totalWorkingTime
				Duration.ofMinutes(30),		//timeAtBreak
				LocalTime.of(10,0), 		//workBegin
				LocalTime.of(17,0), 		//workEnd
				LocalDate.now(), 			// selectedDay
				LocalTime.of(13,0));  		//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, 
				VALID, // checkDurationBetweenWorkingDays
				VALID, // checkInputExists
				VALID, // checkTimeOrderIsChronological
				VALID, // checkBreaksInWorkTime
				VALID, // checkTotalBreakCompliance
				NOT_VALID_SINGLE_BREAK_DURATIONS_ERROR, // checkSingleBreakDurationCompliance
				VALID, // checkDatepickerCompliance
				VALID, // checkWorkTimeLimits
				VALID, // checkTotalWorkingTimeOverTenHours
				VALID); // checkWorkingTimeOverSixHoursWithoutBreak
		
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}
	
//-------- Datepicker
	
	@Test
	void selectedDateIs1DayInTheFuture_Fail(){
		Work work1 = new Work(LocalTime.of(10,0),LocalTime.of(17,0));
		Break break1 = new Break(LocalTime.of(11,0),LocalTime.of(11,30));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, break1, work1);
		InputValidationController inputValidationController = new InputValidationController(
				list, 					//list
				Duration.ofMinutes(30), //legalBreak
				Duration.parse("PT6H30M"), 	//totalWorkingTime
				Duration.ofMinutes(30),//timeAtBreak
				LocalTime.of(10,0), 	//workBegin
				LocalTime.of(17,0), 	//workEnd
				LocalDate.now().plusDays(1), 		// selectedDay
				LocalTime.of(13,0));  	//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, 
				VALID, // checkDurationBetweenWorkingDays
				VALID, // checkInputExists
				VALID, // checkTimeOrderIsChronological
				VALID, // checkBreaksInWorkTime
				VALID, // checkTotalBreakCompliance
				VALID, // checkSingleBreakDurationCompliance
				NOT_VALID_SELECTED_DAY_IS_IN_THE_FUTURE, // checkDatepickerCompliance
				VALID, // checkWorkTimeLimits
				VALID, // checkTotalWorkingTimeOverTenHours
				VALID); // checkWorkingTimeOverSixHoursWithoutBreak
		
		String validationAll = inputValidationController.validation().toString();
	
		assertEquals(expecteValidations.toString(),validationAll);

	}
	
	@Test
	void selectedDateIs32DayInThePast_Fail(){
		Work work1 = new Work(LocalTime.of(10,0),LocalTime.of(17,0));
		Break break1 = new Break(LocalTime.of(11,0),LocalTime.of(11,30));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, break1, work1);
		InputValidationController inputValidationController = new InputValidationController(
				list, 					//list
				Duration.ofMinutes(30), //legalBreak
				Duration.parse("PT6H30M"), 	//totalWorkingTime
				Duration.ofMinutes(30),//timeAtBreak
				LocalTime.of(10,0), 	//workBegin
				LocalTime.of(17,0), 	//workEnd
				LocalDate.now().minusDays(32), 		// selectedDay
				LocalTime.of(13,0));  	//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, 
				VALID, // checkDurationBetweenWorkingDays
				VALID, // checkInputExists
				VALID, // checkTimeOrderIsChronological
				VALID, // checkBreaksInWorkTime
				VALID, // checkTotalBreakCompliance
				VALID, // checkSingleBreakDurationCompliance
				NOT_VALID_SELECTED_DAY_IS_TO_FAR_IN_THE_PAST, // checkDatepickerCompliance
				VALID, // checkWorkTimeLimits
				VALID, // checkTotalWorkingTimeOverTenHours
				VALID); // checkWorkingTimeOverSixHoursWithoutBreak
		
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}
	
//-------- WorkTimeLimits
	@Test
	void workBeginExceedsWorkTimeLimits_Fail(){
		Work work1 = new Work(LocalTime.of(5,0),LocalTime.of(10,0));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, work1);
		InputValidationController inputValidationController = new InputValidationController(
				list, 					//list
				Duration.ZERO, 			//legalBreak
				Duration.ofHours(5), 	//totalWorkingTime
				Duration.ZERO, 			//timeAtBreak
				LocalTime.of(5,0), 		//workBegin
				LocalTime.of(10,0), 	//workEnd
				LocalDate.now(), 		// selectedDay
				LocalTime.of(13,0));  	//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, 
				VALID, // checkDurationBetweenWorkingDays
				VALID, // checkInputExists
				VALID, // checkTimeOrderIsChronological
				VALID, // checkBreaksInWorkTime
				VALID, // checkTotalBreakCompliance
				VALID, // checkSingleBreakDurationCompliance
				VALID, // checkDatepickerCompliance
				VALID_WORKBEGIN_IS_BEFORE_6_00, // checkWorkTimeLimits
				VALID, // checkTotalWorkingTimeOverTenHours
				VALID); // checkWorkingTimeOverSixHoursWithoutBreak
		
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}
	
	@Test
	void workEndExceedsWorkTimeLimits_Fail(){
		Work work1 = new Work(LocalTime.of(15,0),LocalTime.of(20,0));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, work1);
		InputValidationController inputValidationController = new InputValidationController(
				list, 					//list
				Duration.ZERO, 			//legalBreak
				Duration.ofHours(5), 	//totalWorkingTime
				Duration.ZERO, 			//timeAtBreak
				LocalTime.of(15,0), 	//workBegin
				LocalTime.of(20,0), 	//workEnd
				LocalDate.now(), 		// selectedDay
				LocalTime.of(13,0));  	//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, 			
				VALID, // checkDurationBetweenWorkingDays
				VALID, // checkInputExists
				VALID, // checkTimeOrderIsChronological
				VALID, // checkBreaksInWorkTime
				VALID, // checkTotalBreakCompliance
				VALID, // checkSingleBreakDurationCompliance
				VALID, // checkDatepickerCompliance
				VALID_WORKEND_IS_AFTER_19_30, // checkWorkTimeLimits
				VALID, // checkTotalWorkingTimeOverTenHours
				VALID); // checkWorkingTimeOverSixHoursWithoutBreak
		
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}

//-------- WorkTimeOver10Hours
	@Test
	void TotalWorkingTimeOver10Hours_Fail(){
		Work work1 = new Work(LocalTime.of(8,0),LocalTime.of(18,45));
		Break break1 = new Break(LocalTime.of(13,0), LocalTime.of(13,30));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, work1, break1);
		InputValidationController inputValidationController = new InputValidationController(
				list, 						//list
				Duration.ofMinutes(30), 	//legalBreak
				Duration.parse("PT10H15M"), //totalWorkingTime
				Duration.ofMinutes(30), 	//timeAtBreak
				LocalTime.of(8,0), 			//workBegin
				LocalTime.of(18,45), 		//workEnd
				LocalDate.now(), 			// selectedDay
				LocalTime.of(13,0));  		//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, 
				VALID, // checkDurationBetweenWorkingDays
				VALID, // checkInputExists
				VALID, // checkTimeOrderIsChronological
				VALID, // checkBreaksInWorkTime
				VALID, // checkTotalBreakCompliance
				VALID, // checkSingleBreakDurationCompliance
				VALID, // checkDatepickerCompliance
				VALID, // checkWorkTimeLimits
				NOT_VALID_WORKING_TIME_OVER_TEN_HOURS, // checkTotalWorkingTimeOverTenHours
				VALID); // checkWorkingTimeOverSixHoursWithoutBreak
		
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}

//-------- WorkingTimeOver6HoursWithoutBreak	
	@Test
	void WorkingTimeOver6HoursWithoutBreakAtWorkBeginPart_workingSegmentOfWorkInputAtBeginToLong_Fail(){
		Work work1 = new Work(LocalTime.of(8,0),LocalTime.of(18,0));
		Break break1 = new Break(LocalTime.of(15,0), LocalTime.of(15,30));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, work1, break1);
		InputValidationController inputValidationController = new InputValidationController(
				list, 						//list
				Duration.ofMinutes(30), 	//legalBreak
				Duration.parse("PT9H30M"), 	//totalWorkingTime
				Duration.ofMinutes(30), 	//timeAtBreak
				LocalTime.of(8,0), 			//workBegin
				LocalTime.of(18,0), 		//workEnd
				LocalDate.now(), 			// selectedDay
				LocalTime.of(13,0));  		//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, 
				VALID, // checkDurationBetweenWorkingDays
				VALID, // checkInputExists
				VALID, // checkTimeOrderIsChronological
				VALID, // checkBreaksInWorkTime
				VALID, // checkTotalBreakCompliance
				VALID, // checkSingleBreakDurationCompliance
				VALID, // checkDatepickerCompliance
				VALID, // checkWorkTimeLimits
				VALID, // checkTotalWorkingTimeOverTenHours
				NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR); // checkWorkingTimeOverSixHoursWithoutBreak
		
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}
	
	@Test
	void WorkingTimeOver6HoursWithoutBreakAtWorkEndPart_workingSegmentOfWorkInputAtEndToLong_Fail(){
		Work work1 = new Work(LocalTime.of(8,0),LocalTime.of(18,0));
		Break break1 = new Break(LocalTime.of(9,0), LocalTime.of(9,30));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, work1, break1);
		InputValidationController inputValidationController = new InputValidationController(
				list, 						//list
				Duration.ofMinutes(30), 	//legalBreak
				Duration.parse("PT9H30M"), 	//totalWorkingTime
				Duration.ofMinutes(30), 	//timeAtBreak
				LocalTime.of(8,0), 			//workBegin
				LocalTime.of(18,0), 		//workEnd
				LocalDate.now(), 			// selectedDay
				LocalTime.of(13,0));  		//workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, 
				VALID, // checkDurationBetweenWorkingDays
				VALID, // checkInputExists
				VALID, // checkTimeOrderIsChronological
				VALID, // checkBreaksInWorkTime
				VALID, // checkTotalBreakCompliance
				VALID, // checkSingleBreakDurationCompliance
				VALID, // checkDatepickerCompliance
				VALID, // checkWorkTimeLimits
				VALID, // checkTotalWorkingTimeOverTenHours
				NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR); // checkWorkingTimeOverSixHoursWithoutBreak
		
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}
	
	@Test
	void workingTimeOver6HoursWithoutBreak_workingSegmentOfWorkInputAtEndToLong_Fail(){
		Work work1 = new Work(LocalTime.of(8,0),LocalTime.of(18,0));
		Break break1 = new Break(LocalTime.of(9,0), LocalTime.of(9,30));
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, work1, break1);
		InputValidationController inputValidationController = new InputValidationController(
				list, 						// list
				Duration.ofMinutes(30), 	// legalBreak
				Duration.parse("PT9H30M"), 	// totalWorkingTime
				Duration.ofMinutes(30), 	// timeAtBreak
				LocalTime.of(8,0), 			// workBegin
				LocalTime.of(18,0), 		// workEnd
				LocalDate.now(), 			// selectedDay
				LocalTime.of(13,0));  		// workEndYesterday
		ArrayList<String> expecteValidations = new ArrayList<String>();
		addExpectations(expecteValidations, 
				VALID, // checkDurationBetweenWorkingDays
				VALID, // checkInputExists
				VALID, // checkTimeOrderIsChronological
				VALID, // checkBreaksInWorkTime
				VALID, // checkTotalBreakCompliance
				VALID, // checkSingleBreakDurationCompliance
				VALID, // checkDatepickerCompliance
				VALID, // checkWorkTimeLimits
				VALID, // checkTotalWorkingTimeOverTenHours
				NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR); // checkWorkingTimeOverSixHoursWithoutBreak
		
		String validationAll = inputValidationController.validation().toString();
		
		assertEquals(expecteValidations.toString(),validationAll);
	}
		
}
