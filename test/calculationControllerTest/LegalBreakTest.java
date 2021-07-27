package test.calculationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import controller.CalculationController;

import models.Timespann;
import models.Work;

class LegalBreakTest {
	
	private static final Duration NO_BREAK = Duration.ZERO;
	private static final Duration LEGAL_BREAK_30 = Duration.ofMinutes(30);
	private static final Duration LEGAL_BREAK_45 = Duration.ofMinutes(45);
	
	private final Work WORK_0H = new Work(LocalTime.of(0,0), LocalTime.of(0,0));
	private final Work WORK_5H59H = new Work(LocalTime.of(10,0), LocalTime.of(15,59));
	private final Work WORK_6H = new Work(LocalTime.of(10,0), LocalTime.of(16,00));
	private final Work WORK_6H01M = new Work(LocalTime.of(10,0), LocalTime.of(16,01));
	private final Work WORK_8H59M = new Work(LocalTime.of(0,0), LocalTime.of(8,59));
	private final Work WORK_9H = new Work(LocalTime.of(8,0), LocalTime.of(17,0));
	private final Work WORK_9H01M = new Work(LocalTime.of(6,30), LocalTime.of(15,31));
	
	
	@Test
	public void totalWorkingTimeEquals0Hours_LegalBreakIs0Minutes() 
	{	
		ArrayList<Timespann> workList = new ArrayList<Timespann>();
		workList.add(WORK_0H);
		CalculationController calculationController = new CalculationController(workList);
			
		Duration calculatedLegalBreak = calculationController.calculateLegalBreak();
				
		assertEquals(NO_BREAK, calculatedLegalBreak);
	}
	
	@Test
	public void totalWorkingTimeUnder6Hours_LegalBreakIs0Minutes() 
	{	
		ArrayList<Timespann> workList = new ArrayList<Timespann>();
		workList.add(WORK_5H59H);
		CalculationController calculationController = new CalculationController(workList);
			
		Duration calculatedLegalBreak = calculationController.calculateLegalBreak();
				
		assertEquals(NO_BREAK, calculatedLegalBreak);
	}
	
	@Test
	public void totalWorkingTimeEquals6Hours_LegalBreakIs30Minutes() 
	{	
		ArrayList<Timespann> workList = new ArrayList<Timespann>();
		workList.add(WORK_6H);
		CalculationController calculationController = new CalculationController(workList);
			
		Duration calculatedLegalBreak = calculationController.calculateLegalBreak();
				
		assertEquals(LEGAL_BREAK_30, calculatedLegalBreak);
	}
	
	@Test
	public void totalWorkingTimeEquals6H01M_Over6HoursAndUnder9Hours_LegalBreakIs30Minutes() 
	{	
		ArrayList<Timespann> workList = new ArrayList<Timespann>();
		workList.add(WORK_6H01M);
		CalculationController calculationController = new CalculationController(workList);
			
		Duration calculatedLegalBreak = calculationController.calculateLegalBreak();
				
		assertEquals(LEGAL_BREAK_30, calculatedLegalBreak);
	}
	
	@Test
	public void totalWorkingTimeEquals8H59_Over6HoursAndUnder9Hours_LegalBreakIs30Minutes() 
	{	
		ArrayList<Timespann> workList = new ArrayList<Timespann>();
		workList.add(WORK_8H59M);
		CalculationController calculationController = new CalculationController(workList);
			
		Duration calculatedLegalBreak = calculationController.calculateLegalBreak();
				
		assertEquals(LEGAL_BREAK_30, calculatedLegalBreak);
	}
	
	@Test
	public void totalWorkingTimeEquals9Hours_LegalBreakIs30Minutes() 
	{	
		ArrayList<Timespann> workList = new ArrayList<Timespann>();
		workList.add(WORK_9H);
		CalculationController calculationController = new CalculationController(workList);
			
		Duration calculatedLegalBreak = calculationController.calculateLegalBreak();
				
		assertEquals(LEGAL_BREAK_45, calculatedLegalBreak);
	}
	
	@Test
	public void totalWorkingTimeEquals9H01M_Over9Hours_LegalBreakIs30Minutes() 
	{	
		ArrayList<Timespann> workList = new ArrayList<Timespann>();
		workList.add(WORK_9H01M);
		CalculationController calculationController = new CalculationController(workList);
			
		Duration calculatedLegalBreak = calculationController.calculateLegalBreak();
				
		assertEquals(LEGAL_BREAK_45, calculatedLegalBreak);
	}

}
		

