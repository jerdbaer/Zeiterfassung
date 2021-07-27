package test.calculationInputValidationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import models.Break;
import models.Timespann;
import models.ValidationState;
import models.Work;

class BreakInWorkTimeTest {
	
	private Work WORK_8_TO_18 = new Work(LocalTime.of(8, 0), LocalTime.of(18,0));
	private LocalTime WORK_BEGIN = LocalTime.of(8, 0);
	private LocalTime WORK_END = LocalTime.of(18,0);
	
	private static Break BREAK_8_TO_18 = new Break(LocalTime.of(0, 0), LocalTime.of(0,0));
	private static Break BREAK_IN_8_01_TO_17_59 = new Break(LocalTime.of(8,1), LocalTime.of(17,59));
	private static Break BREAK_OUT_7_59_TO_18_01 = new Break(LocalTime.of(7,59), LocalTime.of(18,01));
	private static Break BREAK_AT_WORKBEGIN = new Break(LocalTime.of(8,0), LocalTime.of(10,0));
	private static Break BREAK_AT_WORKEND = new Break(LocalTime.of(15,0), LocalTime.of(18,0));
	
	private InputValidationControllerStub dummyInputValidationControllerStub = new InputValidationControllerStub(
			null, null, null, null, null, null, null, null, null);
	
	private ArrayList<Timespann> addInputToList(ArrayList<Timespann> list, Timespann... timespann) {
		for (Timespann element : timespann) {
			list.add(element);
		}
		return list;
	}
	
	@Test
	void breakEqualsWork_breakNeedsToStartExclusivelyAfterWorkBeginAndEndExclusivelyBeforeWorkEnd_isNotCompliant() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, BREAK_8_TO_18, WORK_8_TO_18);

		ValidationState validationBreakInWork = dummyInputValidationControllerStub
				.checkBreaksInWorkTime(list, WORK_BEGIN, WORK_END);

		assertEquals(ValidationState.NOT_VALID_BREAK_IS_NOT_IN_WORKTIME, validationBreakInWork);
	}
	
	@Test
	void workFrom8To18AndBreakFrom8H01MTo17H59_breakIsInWork_isCompliant() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, BREAK_IN_8_01_TO_17_59, WORK_8_TO_18);

		ValidationState validationBreakInWork = dummyInputValidationControllerStub
				.checkBreaksInWorkTime(list, WORK_BEGIN, WORK_END);

		assertEquals(ValidationState.VALID, validationBreakInWork);
	}
	
	@Test
	void workFrom8To18AndBreakFrom7H59MTo18H01_breakIsOutOfWork_isNotCompliant() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, BREAK_OUT_7_59_TO_18_01, WORK_8_TO_18);

		ValidationState validationBreakInWork = dummyInputValidationControllerStub
				.checkBreaksInWorkTime(list, WORK_BEGIN, WORK_END);

		assertEquals(ValidationState.NOT_VALID_BREAK_IS_NOT_IN_WORKTIME, validationBreakInWork);
	}
	
	
	@Test
	void workFrom8To18AndBreakAtWorkBegin_breakIsInWorkButCannotBeAtBegin_isNotCompliant() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, BREAK_AT_WORKBEGIN, WORK_8_TO_18);

		ValidationState validationBreakInWork = dummyInputValidationControllerStub
				.checkBreaksInWorkTime(list, WORK_BEGIN, WORK_END);

		assertEquals(ValidationState.NOT_VALID_BREAK_CANNOT_BE_AT_WORKING_BEGIN_OR_END, validationBreakInWork);
	}

	@Test
	void workFrom8To18AndBreakAtWorkEnd_breakIsInWorkButCannotBeAtEnd_isNotCompliant() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, BREAK_AT_WORKEND, WORK_8_TO_18);

		ValidationState validationBreakInWork = dummyInputValidationControllerStub
				.checkBreaksInWorkTime(list, WORK_BEGIN, WORK_END);

		assertEquals(ValidationState.NOT_VALID_BREAK_CANNOT_BE_AT_WORKING_BEGIN_OR_END, validationBreakInWork);
	}
}
