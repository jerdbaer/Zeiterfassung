package test.calculationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import controller.CalculationController;
import models.Timespann;
import models.Work;

class WorkBeginAndWorkEndTest {

	private final Work WORK_8_TO_17_IS_9 = new Work(LocalTime.of(8, 0), LocalTime.of(17,0));
	private final Work WORK_10_TO_16H30_IS_6H30M = new Work(LocalTime.of(10, 0), LocalTime.of(16,30));
	private final Work WORK_6H30_TO_19H30_IS_13 = new Work(LocalTime.of(6, 30), LocalTime.of(19,30));
	
//	private final Work BREAK_8_TO_9_IS_1 = new Work(LocalTime.of(8,0), LocalTime.of(9,0));
//	private final Work BREAK_11H30M_TO_12_IS_0H30M = new Work(LocalTime.of(11, 30), LocalTime.of(12,0));
//	private final Work BREAK_13_TO_15HM30_IS_2H30M = new Work(LocalTime.of(13,0), LocalTime.of(15,30));
//	private final Work BREAK_10_TO_0H10M_IS_0H10M = new Work(LocalTime.of(10,0), LocalTime.of(10,10));
//	private final Work BREAK_8H15_TO_8H30M_IS_0H15M = new Work(LocalTime.of(8,15), LocalTime.of(8,30));
	
	
	// Test for pickWorkBeginFromInput() --> private weil im Konstruktor
	@Test
	void singleInputWork_WORK_8_TO_17_BeginIs8() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_8_TO_17_IS_9);
		CalculationController calculationController = new CalculationController(list);
		
		LocalTime actualBegin = calculationController.getWorkBegin();
		
		assertEquals(WORK_8_TO_17_IS_9.getBegin(), actualBegin);
	}
	
	@Test
	void doubleInputWorkMixed_WORK_8_TO_17_WORK_6H30_TO_19H30_BeginIs6H30M() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_8_TO_17_IS_9, WORK_6H30_TO_19H30_IS_13);
		CalculationController calculationController = new CalculationController(list);
		
		LocalTime actualBegin = calculationController.getWorkBegin();
		
		assertEquals(WORK_6H30_TO_19H30_IS_13.getBegin(), actualBegin);
	}
	
	@Test
	void doubleInputWorkSorted_WORK_6H30_TO_19H30_WORK_8_TO_17_BeginIsAlso6H30M() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_6H30_TO_19H30_IS_13,WORK_8_TO_17_IS_9);
		CalculationController calculationController = new CalculationController(list);
		
		LocalTime actualBegin = calculationController.getWorkBegin();
		
		assertEquals(WORK_6H30_TO_19H30_IS_13.getBegin(), actualBegin);
	}
	
	@Test
	void doubleInputWorkSameValue_WORK_8_TO_17_IS_9_WORK_8_TO_17_IS_9_BeginIs8WithoutError() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_8_TO_17_IS_9, WORK_8_TO_17_IS_9);
		CalculationController calculationController = new CalculationController(list);
		
		LocalTime actualBegin = calculationController.getWorkBegin();
		
		assertEquals(WORK_8_TO_17_IS_9.getBegin(), actualBegin);
	}
	
	@Test
	void tripleInputWorkMixed_WORK_8_TO_17_WORK_6H30_TO_19H30_WORK_10_TO_16H30_BeginIs6H30M() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_8_TO_17_IS_9, WORK_6H30_TO_19H30_IS_13,WORK_10_TO_16H30_IS_6H30M);
		CalculationController calculationController = new CalculationController(list);
		
		LocalTime actualBegin = calculationController.getWorkBegin();
		
		assertEquals(WORK_6H30_TO_19H30_IS_13.getBegin(), actualBegin);
	}
	
	
	// Test for pickWorkEndFromInput()
	@Test
	void singleInputWork_WORK_8_TO_17_EndIs17() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_8_TO_17_IS_9);
		CalculationController calculationController = new CalculationController(list);
		
		LocalTime actualEnd = calculationController.getWorkEnd();
		
		assertEquals(WORK_8_TO_17_IS_9.getEnd(), actualEnd);
	}
	
	@Test
	void doubleInputWorkMixed_WORK_8_TO_17_WORK_6H30_TO_19H30_EndIs19H30M() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_8_TO_17_IS_9, WORK_6H30_TO_19H30_IS_13);
		CalculationController calculationController = new CalculationController(list);
		
		LocalTime actualEnd = calculationController.getWorkEnd();
		
		assertEquals(WORK_6H30_TO_19H30_IS_13.getEnd(), actualEnd);
	}
	
	@Test
	void doubleInputWorkSorted_WORK_6H30_TO_19H30_WORK_8_TO_17_EndIsAlso19H30M() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_6H30_TO_19H30_IS_13,WORK_8_TO_17_IS_9);
		CalculationController calculationController = new CalculationController(list);
		
		LocalTime actualEnd = calculationController.getWorkEnd();
		
		assertEquals(WORK_6H30_TO_19H30_IS_13.getEnd(), actualEnd);
	}
	
	@Test
	void doubleInputWorkSameValue_WORK_10_TO_16H30_WORK_10_TO_16H30_EndIs16H30MWithoutError() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_8_TO_17_IS_9, WORK_8_TO_17_IS_9);
		CalculationController calculationController = new CalculationController(list);
		
		LocalTime actualEnd = calculationController.getWorkEnd();
		
		assertEquals(WORK_8_TO_17_IS_9.getEnd(), actualEnd);
	}
	
	@Test
	void tripleInputWorkMixed_WORK_8_TO_17_WORK_6H30_TO_19H30_WORK_10_TO_16H30_EndIs19H30M() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, WORK_8_TO_17_IS_9, WORK_6H30_TO_19H30_IS_13,WORK_10_TO_16H30_IS_6H30M);
		CalculationController calculationController = new CalculationController(list);
		
		LocalTime actualEnd = calculationController.getWorkEnd();
		
		assertEquals(WORK_6H30_TO_19H30_IS_13.getEnd(), actualEnd);
	}
	
	
	
	
	private ArrayList<Timespann> addInputToList(ArrayList<Timespann> list, Timespann... timespann) {
	for (Timespann element : timespann) {
		list.add(element);
	}
	return list;
	}
}
