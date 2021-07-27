package test.calculationInputValidationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import models.Break;
import models.Timespann;
import models.ValidationState;
import models.Work;

class WorkingTimeOverSixHoursWithoutBreakTest {
	
	private static final Work WORK_ONE_6_TO_19 = new Work(LocalTime.of(6, 0), LocalTime.of(19, 0));
	
	private static final Work WORK_TWO_6_TO_10 = new Work(LocalTime.of(6, 0), LocalTime.of(10, 0));
	private static final Work WORK_THREE_11_TO_18 = new Work(LocalTime.of(11, 0), LocalTime.of(18, 0));
	private static final Work WORK_FOUR_15_TO_23 = new Work(LocalTime.of(15, 0), LocalTime.of(23,0));
	private static final Work WORK_FIVE_11_TO_14 = new Work(LocalTime.of(11, 0), LocalTime.of(14,0));
	

	private static final Break BREAK_ONE_12_TO_13 = new Break(LocalTime.of(12, 0), LocalTime.of(13, 0));
	private static final Break BREAK_TWO_12_01_TO_13 = new Break(LocalTime.of(12, 1), LocalTime.of(13, 0));
	private static final Break BREAK_THREE_11_59_TO_12_59 = new Break(LocalTime.of(11, 59), LocalTime.of(12, 59));
	
	private static final Break BREAK_FOUR_9_TO_10 = new Break(LocalTime.of(9, 0), LocalTime.of(10, 0));
	private static final Break BREAK_Five_15_15_TO_15_30 = new Break(LocalTime.of(15, 15), LocalTime.of(15, 30));
	private static final Break BREAK_Six_16_30_TO_17_00 = new Break(LocalTime.of(16, 30), LocalTime.of(17, 00));

	
	private InputValidationControllerStub dummyInputValidationControllerStub = new InputValidationControllerStub(
			null, null, null, null, null, null, null, null, null);
	
	private ArrayList<Timespann> addInputToList(ArrayList<Timespann> list, Timespann... timespann) {
		for (Timespann element : timespann) {
			list.add(element);
		}
		return list;
	}
	
	// 1 WorkTime
	@Test
	void workFrom_6_TO_10_WithoutBreak_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_TWO_6_TO_10);
		
		ValidationState validationSixHours = dummyInputValidationControllerStub
				.checkWorkingTimeOverSixHoursWithoutBreak(list);
		
		assertEquals(ValidationState.VALID, validationSixHours);
	}
	
	@Test
	void workFrom_6_TO_19_WithBreakFrom_12_TO_13_workingTimePartsEquals6_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_ONE_6_TO_19, BREAK_ONE_12_TO_13);
		
		ValidationState validationSixHours = dummyInputValidationControllerStub
				.checkWorkingTimeOverSixHoursWithoutBreak(list);
		
		assertEquals(ValidationState.VALID, validationSixHours);
	}
	
	@Test
	void workFrom_6_TO_19_WithBreakFrom_11_59_TO_12_59_firstPartIs6H01M_SecondPartIs6H01M_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_ONE_6_TO_19, BREAK_THREE_11_59_TO_12_59);
		
		ValidationState validationSixHours = dummyInputValidationControllerStub
				.checkWorkingTimeOverSixHoursWithoutBreak(list);
		
		assertEquals(ValidationState.NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR, validationSixHours);
	}
	
	@Test
	void workFrom_6_TO_19_WithBreakFrom_12_01_TO_13_firstPartIs6H01M_SecondPartIs5H59M_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_ONE_6_TO_19, BREAK_TWO_12_01_TO_13);
		
		ValidationState validationSixHours = dummyInputValidationControllerStub
				.checkWorkingTimeOverSixHoursWithoutBreak(list);
		
		assertEquals(ValidationState.NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR, validationSixHours);
	}
	
	@Test
	void workFrom_6_TO_19_WithBreaksFrom_9_TO_10_and_11_59_TO_12_59_ThirdPartIs5H59M_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_ONE_6_TO_19, BREAK_FOUR_9_TO_10, BREAK_THREE_11_59_TO_12_59);
		
		ValidationState validationSixHours = dummyInputValidationControllerStub
				.checkWorkingTimeOverSixHoursWithoutBreak(list);
		
		assertEquals(ValidationState.NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR, validationSixHours);
	}
	
	@Test
	void workFrom_6_TO_19_WithBreaksFrom_9_TO_10_and_12_TO_13_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_ONE_6_TO_19, BREAK_FOUR_9_TO_10,BREAK_ONE_12_TO_13);
		
		ValidationState validationSixHours = dummyInputValidationControllerStub
				.checkWorkingTimeOverSixHoursWithoutBreak(list);
		
		assertEquals(ValidationState.VALID, validationSixHours);
	}

	
	// 2 WorkTimes
	@Test
	void workFrom_6_TO_10_and_11_TO_18_WithBreakFrom_12_TO_13_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, BREAK_ONE_12_TO_13, WORK_TWO_6_TO_10, WORK_THREE_11_TO_18);
		
		ValidationState validationSixHours = dummyInputValidationControllerStub
				.checkWorkingTimeOverSixHoursWithoutBreak(list);
		
		assertEquals(ValidationState.VALID, validationSixHours);
	}
	
	@Test
	void workFrom_6_TO_10_and_15_TO_23_WithBreaksFrom_9_TO_10_and_16_30_TO_17_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, BREAK_ONE_12_TO_13, WORK_TWO_6_TO_10, WORK_FOUR_15_TO_23, BREAK_Six_16_30_TO_17_00);
		
		ValidationState validationSixHours = dummyInputValidationControllerStub
				.checkWorkingTimeOverSixHoursWithoutBreak(list);
		
		assertEquals(ValidationState.VALID, validationSixHours);
	}
	
	@Test
	void workFrom_6_TO_10_and_15_TO_23_WithBreaksFrom_9_TO_10_and_15_15_TO_15_30_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, BREAK_ONE_12_TO_13, WORK_TWO_6_TO_10, WORK_FOUR_15_TO_23, BREAK_Five_15_15_TO_15_30);
		
		ValidationState validationSixHours = dummyInputValidationControllerStub
				.checkWorkingTimeOverSixHoursWithoutBreak(list);
		
		assertEquals(ValidationState.NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR, validationSixHours);
	}
	
	
	// 3 Works
		@Test
	void workFrom_6_TO_10_and_11_TO_14_and_15_TO_23_WithBreakFrom_15_15_TO_15_30_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_TWO_6_TO_10, WORK_FIVE_11_TO_14, WORK_FOUR_15_TO_23,BREAK_Five_15_15_TO_15_30);
		
		ValidationState validationSixHours = dummyInputValidationControllerStub
				.checkWorkingTimeOverSixHoursWithoutBreak(list);
		
		assertEquals(ValidationState.NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR, validationSixHours);
	}
	
	@Test
	void workFrom_6_TO_10_and_11_TO_14_and_15_TO_23_WithBreakFrom_16_30_TO_17_30_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_TWO_6_TO_10, WORK_FIVE_11_TO_14, WORK_FOUR_15_TO_23,BREAK_Six_16_30_TO_17_00);
		
		ValidationState validationSixHours = dummyInputValidationControllerStub
				.checkWorkingTimeOverSixHoursWithoutBreak(list);
		
		assertEquals(ValidationState.VALID, validationSixHours);
	}
	
	
}
