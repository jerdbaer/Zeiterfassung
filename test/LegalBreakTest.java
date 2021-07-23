package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import josi.calculation.LegalBreak;

class LegalBreakTest {
//	
//	private static final LocalTime NO_BREAK = LocalTime.of(0,0);
//	private static final LocalTime LEGAL_BREAK_30 = LocalTime.of(0,30);
//	private static final LocalTime LEGAL_BREAK_45 = LocalTime.of(0,45);
//	
//	@Test
//	public void TotalTimeAtWorkEquals0Hours_LegalBreakIs0Minutes() 
//	{
//		LegalBreak legalBreak = new LegalBreak();
//		
//		LocalTime calculatedBreak = legalBreak.calculateLegalBreak(LocalTime.of(0,0));
//		
//		assertEquals(NO_BREAK, calculatedBreak);
//	}
//		
//	@Test
//	public void TotalTimeAtWorkUnder6Hours_LegalBreakIs0Minutes() 
//	{	
//		LegalBreak legalBreak = new LegalBreak();
//			
//		LocalTime calculatedBreak = legalBreak.calculateLegalBreak(LocalTime.of(5,59));
//		
//		assertEquals(NO_BREAK, calculatedBreak);
//	}
//	
//	@Test
//	public void TotalTimeAtWorkEquals6Hours_LegalBreakIs30Minutes() 
//	{
//		LegalBreak legalBreak = new LegalBreak();
//		
//		LocalTime calculatedBreak = legalBreak.calculateLegalBreak(LocalTime.of(6,0));
//		
//		assertEquals(LEGAL_BREAK_30, calculatedBreak);
//
//	}
//
//	@Test
//	public void TotalTimeAtWorkOver6HoursUnder9Hours_LegalBreakIs30Minutes() 
//	{
//		LegalBreak legalBreak = new LegalBreak();
//	
//		LocalTime calculatedBreak = legalBreak.calculateLegalBreak(LocalTime.of(8,59));
//		
//		assertEquals(LEGAL_BREAK_30, calculatedBreak);
//	}
//
//	@Test
//	public void TotalTimeAtWorkEquals9Hours_LegalBreakIs45Minutes() 
//	{
//		LegalBreak legalBreak = new LegalBreak();
//		
//		LocalTime calculatedBreak = legalBreak.calculateLegalBreak(LocalTime.of(9,0));
//		
//		assertEquals(LEGAL_BREAK_45, calculatedBreak);
//	}
//
//	@Test
//	public void TotalTimeAtWorkOver9Hours_LegalBreakIs45Minutes() 
//	{
//		LegalBreak legalBreak = new LegalBreak();
//
//		LocalTime calculatedBreak = legalBreak.calculateLegalBreak(LocalTime.of(9, 45));
//
//		assertEquals(LEGAL_BREAK_45, calculatedBreak);
//	}
}
