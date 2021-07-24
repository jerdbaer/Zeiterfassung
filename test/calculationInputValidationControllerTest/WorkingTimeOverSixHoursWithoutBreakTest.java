package test.calculationInputValidationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import models.Break;
import models.Timespann;
import models.Work;

class OverSixHoursWithoutBreakTest {
	
	private static final Work WORK_ONE_6_TO_19 = new Work(LocalTime.of(6, 0), LocalTime.of(19, 0));
	private static final LocalTime WORK_ONE_BEGIN = LocalTime.of(6,0);
	private static final LocalTime WORK_ONE_END = LocalTime.of(22,0);
	
	//------------
	private static final Work WORK_TWO_6_TO_22 = new Work(LocalTime.of(6, 0), LocalTime.of(22, 0));
	private static final LocalTime WORK_TWO_BEGIN = LocalTime.of(6,0);
	private static final LocalTime WORK_TWO_END = LocalTime.of(22,0);
	
	private static final Work WORK_THREE_6_TO_22 = new Work(LocalTime.of(6, 0), LocalTime.of(22, 0));
	private static final LocalTime WORK_THREE_BEGIN = LocalTime.of(6,0);
	private static final LocalTime WORK_THREE_END = LocalTime.of(22,0);
	//------------
	
	private static final Break BREAK_ONE_12_TO_13 = new Break(LocalTime.of(12, 0), LocalTime.of(22, 0));
	private static final Break BREAK_TWO_12_TO_13 = new Break(LocalTime.of(12, 0), LocalTime.of(22, 0));
	private static final Break BREAK_THREE_12_TO_13 = new Break(LocalTime.of(12, 0), LocalTime.of(22, 0));
	private static final Break BREAK_FOUR_12_TO_13 = new Break(LocalTime.of(12, 0), LocalTime.of(22, 0));
	private static final Break BREAK_FIVE_12_TO_13 = new Break(LocalTime.of(12, 0), LocalTime.of(22, 0));
	
	InputValidationControllerStub dummyInputValidationControllerStub = new InputValidationControllerStub(null,
			null, null, null, null, null, null, null);
	
	private ArrayList<Timespann> addInputToList(ArrayList<Timespann> list, Timespann... timespann) {
		for (Timespann element : timespann) {
			list.add(element);
		}
		return list;
	}
	
	@Test
	void workFrom_6_TO_19_WithBreakFrom_12_TO_13_workingTimePartsEquals6isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_ONE_6_TO_19, BREAK_ONE_12_TO_13);

		String validationTimeOrder = dummyInputValidationControllerStub
				.checkWorkingTimeOverSixHoursWithoutBreak(list, WORK_ONE_BEGIN, WORK_ONE_END)
				.toString();

		assertEquals("VALID", validationTimeOrder);
	}

}
