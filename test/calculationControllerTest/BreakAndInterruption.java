package test.calculationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import controller.CalculationController;
import models.Break;
import models.Interruption;
import models.Timespann;
import models.Work;

class BreakAndInterruption {

	private final Interruption INTERRUPPTION_12_TO_13_Is1 = new Interruption(LocalTime.of(12,0), LocalTime.of(13,0));
	private final Interruption INTERRUPPTION_10_TO_13_Is6 = new Interruption(LocalTime.of(10,0), LocalTime.of(13,0));
	private final Interruption INTERRUPPTION_16_TO_16H15_is0H15M = new Interruption(LocalTime.of(16,0), LocalTime.of(16,15));
	private final Interruption INTERRUPPTION_16_TO_16H10_is0H10M = new Interruption(LocalTime.of(16,0), LocalTime.of(16,10));
	
		
	private final Break BREAK_13_TO_15HM30_IS_2H30M = new Break(LocalTime.of(13,0), LocalTime.of(15,30));
	private final Break BREAK_10_TO_0H10M_IS_0H10M = new Break(LocalTime.of(10,0), LocalTime.of(10,10));
	private final Break BREAK_8H15_TO_8H30M_IS_0H15M = new Break(LocalTime.of(8,15), LocalTime.of(8,30));
	
	
	// Test for pickWorkBeginFromInput() --> private weil im Konstruktor
	@Test
	void singleBreakInput_BREAK_8H15_TO_8H30M_BreakIs0M15M() {
		ArrayList<Timespann> list = new ArrayList<Timespann>();
		addInputToList(list, BREAK_8H15_TO_8H30M_IS_0H15M,BREAK_8H15_TO_8H30M_IS_0H15M);
		CalculationController calculationController = new CalculationController(list);
		
		Duration actualBegin = calculationController.calculateBreakAndInterruptionDuration();
		
		assertEquals(Duration.ofMinutes(15), actualBegin);
	}
	
	
	
	
	
	// Test for pickWorkEndFromInput()

	
	
	
	
	private ArrayList<Timespann> addInputToList(ArrayList<Timespann> list, Timespann... timespann) {
	for (Timespann element : timespann) {
		list.add(element);
	}
	return list;
	}

}
