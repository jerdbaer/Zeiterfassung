package test.calculationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import controller.CalculationController;
import models.*;

class BreakAndInterruptionTest {

	private final Interruption INTERRUPPTION_10_TO_13_Is3 = new Interruption(LocalTime.of(10,0), LocalTime.of(13,0));
	private final Interruption INTERRUPPTION_12_TO_13_Is1 = new Interruption(LocalTime.of(12,0), LocalTime.of(13,0));
	private final Interruption INTERRUPPTION_16_TO_16H10_is0H10M = new Interruption(LocalTime.of(16,0), LocalTime.of(16,10));
	private final Interruption INTERRUPPTION_16_TO_16H15_is0H15M = new Interruption(LocalTime.of(16,0), LocalTime.of(16,15));
	
	private final Break BREAK_0 = new Break(LocalTime.of(0,0), LocalTime.of(0,0));
	private final Break BREAK_8H15_TO_8H30M_IS_0H15M = new Break(LocalTime.of(8,15), LocalTime.of(8,30));	
	private final Break BREAK_10_TO_0H10M_IS_0H10M = new Break(LocalTime.of(10,0), LocalTime.of(10,10));
	private final Break BREAK_13_TO_15HM30_IS_2H30M = new Break(LocalTime.of(13,0), LocalTime.of(15,30));
	
	private final Work WORK_8_TO_18_is10 = new Work(LocalTime.of(8, 0), LocalTime.of(18, 0));
	
	private ArrayList<Timespann> addInputToList(ArrayList<Timespann> list, Timespann... timespann) {
		for (Timespann element : timespann) {
			list.add(element);
		}
		return list;
	}
	
	// Test for calculateBreakAndInterruptionDuration() with dummyWorkInput for construction success for 
	@Test
	void singleBreakInputPlusDummyWork_BREAK_0_BreakIs0() {
		ArrayList<Timespann> breakList = new ArrayList<Timespann>();
		breakList = addInputToList(breakList, BREAK_0, WORK_8_TO_18_is10);
		CalculationController calculationController = new CalculationController(breakList);
		
		Duration actualTotalBreak = calculationController.calculateBreakAndInterruptionDuration();
		
		assertEquals(Duration.ZERO, actualTotalBreak);
	}
	
	@Test
	void singleBreakInputPlusDummyWork_BREAK_8H15_TO_8H30M_BreakIs0M15M() {
		ArrayList<Timespann> breakList = new ArrayList<Timespann>();
		breakList = addInputToList(breakList, BREAK_8H15_TO_8H30M_IS_0H15M, WORK_8_TO_18_is10);
		CalculationController calculationController = new CalculationController(breakList);
		
		Duration actualTotalBreak = calculationController.calculateBreakAndInterruptionDuration();
		
		assertEquals(Duration.ofMinutes(15), actualTotalBreak);
	}
	
	
	@Test
	void doubleBreakInputPlusDummyWork_BREAK_8H15_TO_8H30M_BreakIs0M30M() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, BREAK_8H15_TO_8H30M_IS_0H15M, BREAK_8H15_TO_8H30M_IS_0H15M, WORK_8_TO_18_is10);
		CalculationController calculationController = new CalculationController(list);
		Duration actualTotalBreak = calculationController.calculateBreakAndInterruptionDuration();
		
		assertEquals(Duration.ofMinutes(30),actualTotalBreak);
	}
	
	@Test
	void fiveBreaksInputPlusDummyWork_5xBREAK_13_TO_15HM30_BreakIs12H30M() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, BREAK_13_TO_15HM30_IS_2H30M, BREAK_13_TO_15HM30_IS_2H30M,BREAK_13_TO_15HM30_IS_2H30M,
				BREAK_13_TO_15HM30_IS_2H30M,BREAK_13_TO_15HM30_IS_2H30M, WORK_8_TO_18_is10);
		CalculationController calculationController = new CalculationController(list);
		
		Duration actualTotalBreak = calculationController.calculateBreakAndInterruptionDuration();
		
		assertEquals(Duration.parse("PT12H30M"),actualTotalBreak);
	}
	
	@Test
	void fourBreaksAndFourInterruptionsPlusDummyWorkMixed_ALL_CONSTANTS_BreakIs7H20M() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		list = addInputToList(list, INTERRUPPTION_10_TO_13_Is3, BREAK_13_TO_15HM30_IS_2H30M, INTERRUPPTION_12_TO_13_Is1, 
				WORK_8_TO_18_is10, BREAK_8H15_TO_8H30M_IS_0H15M,BREAK_10_TO_0H10M_IS_0H10M,
				INTERRUPPTION_16_TO_16H10_is0H10M, BREAK_0, INTERRUPPTION_16_TO_16H15_is0H15M);
		CalculationController calculationController = new CalculationController(list);
		
		Duration actualTotalBreak = calculationController.calculateBreakAndInterruptionDuration();
		
		assertEquals(Duration.parse("PT7H20M"),actualTotalBreak);
	}
	
}
