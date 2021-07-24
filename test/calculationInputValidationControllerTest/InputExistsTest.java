package test.calculationInputValidationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import models.Break;
import models.Timespann;
import models.Work;

class InputExistsTest {

	
	
	private Work WORK_8_TO_18 = new Work(LocalTime.of(8, 0), LocalTime.of(18,0));	
	private Break BREAK_8_TO_18 = new Break(LocalTime.of(0, 0), LocalTime.of(0,0));
	
	private InputValidationControllerStub dummyInputValidationControllerStub = new InputValidationControllerStub(null,
			null, null, null, null, null, null, null);
	
	private ArrayList<Timespann> addInputToList(ArrayList<Timespann> list, Timespann... timespann) {
		for (Timespann element : timespann) {
			list.add(element);
		}
		return list;
	}
	
	@Test
	void emptyList_inputDoesNotExit_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		
		String validationInputExists = dummyInputValidationControllerStub.checkInputExists(list).toString();

		assertEquals("NOT_VALID_NO_INPUT_FOUND", validationInputExists);
	}
	
	@Test
	void workInList_inputExits_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_8_TO_18);

		String validationInputExists = dummyInputValidationControllerStub.checkInputExists(list).toString();

		assertEquals("VALID", validationInputExists);
	}
	
	@Test
	void omlyBreakInList_inputDoesNotExit_isNotValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, BREAK_8_TO_18);

		String validationInputExists = dummyInputValidationControllerStub.checkInputExists(list).toString();

		assertEquals("NOT_VALID_NO_INPUT_FOUND", validationInputExists);
	}
	
	@Test
	void workAndBreakInList_inputExits_isValid() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, BREAK_8_TO_18,WORK_8_TO_18);

		String validationInputExists = dummyInputValidationControllerStub.checkInputExists(list).toString();

		assertEquals("VALID", validationInputExists);
	}
	

}
