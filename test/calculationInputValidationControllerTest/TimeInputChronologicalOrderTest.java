package test.calculationInputValidationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import models.Break;
import models.BreakInterruption;
import models.Interruption;
import models.Timespann;
import models.Work;

class TimeInputChronologicalOrderTest {

	private static final Interruption INTERRUPTION_0_WORKTIME2_STARTS_EXACTLY_WHEN_WORKTIME1_ENDS = 
			new Interruption(LocalTime.of(8, 0), LocalTime.of(8, 0));
	private static final Interruption INTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1 = 
			new Interruption(LocalTime.of(12, 0), LocalTime.of(15, 0));
	private static final Interruption INTERRUPTION_NEG_WORKTIME2_BEFORE_WORKTIME1 = 
			new Interruption(LocalTime.of(15, 0), LocalTime.of(12, 0));
	
	private static final BreakInterruption BREAKINTERRUPTION_0_WORKTIME2_STARTS_EXACTLY_WHEN_WORKTIME1_ENDS = 
			new BreakInterruption(LocalTime.of(0, 0), LocalTime.of(0, 0));
	private static final BreakInterruption BREAKINTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1 = 
			new BreakInterruption(LocalTime.of(10, 30), LocalTime.of(12, 30));
	private static final BreakInterruption BREAKINTERRUPTION_NEG_WORKTIME2_BEFORE_WORKTIME1 = 
			new BreakInterruption(LocalTime.of(20, 0), LocalTime.of(17, 0));
	
	private  static final Break BREAK_0 = new Break(LocalTime.of(0, 0), LocalTime.of(0, 0));
	private static final Break BREAK_VALID = new Break(LocalTime.of(10, 0), LocalTime.of(11, 0));
	private static final Break BREAK_INVALID = new Break(LocalTime.of(11, 0), LocalTime.of(10, 0));
	
	private static final Work WORK_0 = new Work(LocalTime.of(0, 0), LocalTime.of(0, 0));
	private static final Work WORK_VALID = new Work(LocalTime.of(13, 30), LocalTime.of(15, 30));
	private static final Work Work_INVALID = new Work(LocalTime.of(15, 30), LocalTime.of(13, 30));
	
	InputValidationControllerStub dummyInputValidationControllerStub = new InputValidationControllerStub(null,
			null, null, null, null, null, null, null);
	
	private ArrayList<Timespann> addInputToList(ArrayList<Timespann> list, Timespann... timespann) {
		for (Timespann element : timespann) {
			list.add(element);
		}
		return list;
	}
	
	// Chronological Order Test
	@Test
	void inputSingleInterruption0_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, INTERRUPTION_0_WORKTIME2_STARTS_EXACTLY_WHEN_WORKTIME1_ENDS);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("VALID", validationTimeOrder);
	}
	
	@Test
	void inputSingleInterruptionPositiv_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, INTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("VALID", validationTimeOrder);
	}
	
	@Test
	void inputSingleInterruptionNegativ_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, INTERRUPTION_NEG_WORKTIME2_BEFORE_WORKTIME1);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("NOT_VALID_TIME_INPUT_MUST_BE_IN_CHRONICAL_ORDER", validationTimeOrder);
	}
	
	@Test
	void inputInterruptionPositivAndNegativ_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, INTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1, INTERRUPTION_NEG_WORKTIME2_BEFORE_WORKTIME1);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("NOT_VALID_TIME_INPUT_MUST_BE_IN_CHRONICAL_ORDER", validationTimeOrder);
	}
	
	@Test
	void inputSingleBreakinterruption0_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, BREAKINTERRUPTION_0_WORKTIME2_STARTS_EXACTLY_WHEN_WORKTIME1_ENDS);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("VALID", validationTimeOrder);
	}
	
	@Test
	void inputSingleBreakinterruptionPositiv_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, BREAKINTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("VALID", validationTimeOrder);
	}
	
	@Test
	void inputSingleBreakinterruptionNegativ_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, BREAKINTERRUPTION_NEG_WORKTIME2_BEFORE_WORKTIME1);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("NOT_VALID_TIME_INPUT_MUST_BE_IN_CHRONICAL_ORDER", validationTimeOrder);
	}
	
	@Test
	void inputBreakinterruptionPositivAndNegativ_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, BREAKINTERRUPTION_NEG_WORKTIME2_BEFORE_WORKTIME1, 
				BREAKINTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("NOT_VALID_TIME_INPUT_MUST_BE_IN_CHRONICAL_ORDER", validationTimeOrder);
	}
	
	// TimeInput End before Begin Test
	@Test
	void inputSingleBreak0WithoutBreakinterruption_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, BREAK_0);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("VALID", validationTimeOrder);
	}
	
	@Test
	void inputValidSingleBreakWithoutBreakinterruption_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, BREAK_VALID);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("VALID", validationTimeOrder);
	}
	
	@Test
	void inputInvalidSingleBreakWithoutBreakinterruption_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, BREAK_INVALID);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("NOT_VALID_START_IS_AFTER_END", validationTimeOrder);
	}
	
	@Test
	void inputValidBreaksWithValidBreakTimeOrder_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, BREAKINTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1, BREAK_VALID, BREAK_VALID);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("VALID", validationTimeOrder);
	}
	
	@Test
	void inputInvalidBreaksWithValidBreakTimeOrder_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, BREAKINTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1, BREAK_INVALID, BREAK_INVALID);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("NOT_VALID_START_IS_AFTER_END", validationTimeOrder);
	}
	
	@Test
	void inputInvalidAndValidBreakWithValidBreakTimeOrder_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, BREAKINTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1, BREAK_INVALID, BREAK_VALID);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("NOT_VALID_START_IS_AFTER_END", validationTimeOrder);
	}
	
	/////////////////
	
	@Test
	void inputSingleWork0WithoutInterruption_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_0);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("VALID", validationTimeOrder);
	}
	
	@Test
	void inputValidSingleWorkkWithoutInterruption_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_VALID);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("VALID", validationTimeOrder);
	}
	
	@Test
	void inputInvalidSingleWorkWithoutInterruption_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, Work_INVALID);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("NOT_VALID_START_IS_AFTER_END", validationTimeOrder);
	}
	
	@Test
	void inputValidWorksWithValidWorkTimeOrder_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, INTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1, WORK_VALID, WORK_VALID);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("VALID", validationTimeOrder);
	}
	
	@Test
	void inputInvalidWorksWithValidWorkTimeOrder_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, INTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1, Work_INVALID, Work_INVALID);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("NOT_VALID_START_IS_AFTER_END", validationTimeOrder);
	}
	
	@Test
	void inputInvalidAndValidWorksWithValidBreakTimeOrder_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, INTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1, Work_INVALID, WORK_VALID);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("NOT_VALID_START_IS_AFTER_END", validationTimeOrder);
	}
	
	/////////////////

	@Test
	void inputInvalidWorkAndValidBreaksWithValidTimeOrders_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, INTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1, Work_INVALID, WORK_VALID);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("NOT_VALID_START_IS_AFTER_END", validationTimeOrder);
	}
	
	@Test
	void inputValidWorkAndInvalidBreaksWithValidTimeOrders_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, INTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1, WORK_VALID, WORK_VALID,
				BREAKINTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1, BREAK_INVALID, BREAK_INVALID);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("NOT_VALID_START_IS_AFTER_END", validationTimeOrder);
	}
	
	@Test
	void inputOneInvalidWorkAndMultipleValidBreaksAndSecondValidWorkWithValidTimeOrders_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, INTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1, WORK_VALID, Work_INVALID,
				BREAKINTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1, BREAK_VALID, BREAK_VALID);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("NOT_VALID_START_IS_AFTER_END", validationTimeOrder);
	}
	
	@Test
	void inputOneInvalidBreakAndMultipleValidWorksAndSecondValidBreakWithValidTimeOrders_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, INTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1, WORK_VALID, WORK_VALID,
				BREAKINTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1, BREAK_VALID, BREAK_INVALID);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("NOT_VALID_START_IS_AFTER_END", validationTimeOrder);
	}
	
	@Test
	void inputOnlyValidBreaksAndWorksWithValidTimeOrders_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, INTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1, WORK_VALID, WORK_VALID,
				BREAKINTERRUPTION_POS_WORKTIME2_AFTER_WORKTIME1, BREAK_VALID, BREAK_VALID);

		String validationTimeOrder = dummyInputValidationControllerStub.checkTimeOrderIsChronological(list).toString();

		assertEquals("VALID", validationTimeOrder);
	}
	

}
