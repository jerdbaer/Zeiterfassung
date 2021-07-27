package test.calculationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import controller.CalculationController;
import models.Break;
import models.Timespann;
import models.Work;

class TotalWorkTimeTest {

	private final Work WORK_0H = new Work(LocalTime.of(0,0), LocalTime.of(0,0));
	private final Work WORK_3H30M = new Work(LocalTime.of(10,0), LocalTime.of(13,30));
	private final Work WORK_6H = new Work(LocalTime.of(10,0), LocalTime.of(16,00));
		
	private final Break BREAK_0 = new Break(LocalTime.of(0,0), LocalTime.of(0,0));
	private final Break BREAK_8H15_TO_8H30M_IS_0H15M = new Break(LocalTime.of(8,15), LocalTime.of(8,30));	
	private final Break BREAK_13_TO_14_IS_1H = new Break(LocalTime.of(13,0), LocalTime.of(14,00));
	
	private ArrayList<Timespann> addInputToList(ArrayList<Timespann> list, Timespann... timespann) {
		for (Timespann element : timespann) {
			list.add(element);
		}
		return list;
	}
	
	@Test
	void Work0AndBreak0_TotalWorkTimeIs0() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, WORK_0H, BREAK_0);
		CalculationController calculationController = new CalculationController(list);
		
		Duration actualWorkingTime = calculationController.calculateTotalWorktime();
		
		assertEquals(Duration.ZERO, actualWorkingTime);
	}
	
	@Test
	void singleWork0AndSingleBreak1_TotalWorkTimeIsMinus1() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, WORK_0H, BREAK_13_TO_14_IS_1H);
		CalculationController calculationController = new CalculationController(list);
		
		Duration actualWorkingTime = calculationController.calculateTotalWorktime();
		
		assertEquals(Duration.ofHours(-1), actualWorkingTime);
	}
	
	@Test
	void singleWork6AndBreak0_TotalWorkTimeIs6() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, WORK_6H, BREAK_0);
		CalculationController calculationController = new CalculationController(list);
		
		Duration actualWorkingTime = calculationController.calculateTotalWorktime();
		
		assertEquals(Duration.ofHours(6), actualWorkingTime);
	}
	
	@Test
	void allWorkAndBreak0_ALL_WORK_CONSTANTS_TotalWorkTimeIs9H30M() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, WORK_6H, WORK_0H, WORK_3H30M);
		CalculationController calculationController = new CalculationController(list);
		
		Duration actualWorkingTime = calculationController.calculateTotalWorktime();
		
		assertEquals(Duration.parse("PT9H30M"), actualWorkingTime);
	}
	
	@Test
	void allWorkAndallBreak_ALL_CONSTANTS_TotalWorkTimeIs9H30M() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, BREAK_0, BREAK_13_TO_14_IS_1H, WORK_6H, WORK_0H, BREAK_8H15_TO_8H30M_IS_0H15M, WORK_3H30M);
		CalculationController calculationController = new CalculationController(list);
		
		Duration actualWorkingTime = calculationController.calculateTotalWorktime();
		
		assertEquals(Duration.parse("PT8H15M"), actualWorkingTime);
	}
	
}
