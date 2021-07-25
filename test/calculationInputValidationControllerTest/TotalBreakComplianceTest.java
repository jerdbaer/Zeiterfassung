package test.calculationInputValidationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import models.ValidationState;

class TotalBreakComplianceTest {
	
	private final Duration TIME_AT_BREAK_0 = Duration.ofMinutes(0);
	private final Duration TIME_AT_BREAK_29 = Duration.ofMinutes(29);
	private final Duration TIME_AT_BREAK_30 = Duration.ofMinutes(31);
	private final Duration TIME_AT_BREAK_31 = Duration.ofMinutes(31);
	private final Duration TIME_AT_BREAK_44 = Duration.ofMinutes(44);	
	private final Duration TIME_AT_BREAK_45 = Duration.ofMinutes(45);
	private final Duration TIME_AT_BREAK_46 = Duration.ofMinutes(46);	
	
	private final Duration LEGAL_BREAK_0 = Duration.ofMinutes(0);
	private final Duration LEGAL_BREAK_30 = Duration.ofMinutes(30);
	private final Duration LEGAL_BREAK_45 = Duration.ofMinutes(45);
	
	
	InputValidationControllerStub dummyInputValidationControllerStub = new InputValidationControllerStub(null,
			null, null, null, null, null, null, null);
	
	@Test
	void TimeAtBreak0AndLegalBreakRequired0_isCompliant() {
		ValidationState validationTotalBreak = dummyInputValidationControllerStub
				.checkTotalBreakCompliance(TIME_AT_BREAK_0, LEGAL_BREAK_0);
		
		assertEquals(ValidationState.VALID, validationTotalBreak);
	}
	
	@Test
	void TimeAtBreakOver0AndLegalBreakRequired0_isCompliant() {
		ValidationState validationTotalBreak = dummyInputValidationControllerStub
				.checkTotalBreakCompliance(TIME_AT_BREAK_30, LEGAL_BREAK_0);
		
		assertEquals(ValidationState.VALID, validationTotalBreak);
	}
	
	@Test
	void TimeAtBreak29AndLegalBreakRequired30_isNotCompliant_breakIsToShort() {
		ValidationState validationTotalBreak = dummyInputValidationControllerStub
				.checkTotalBreakCompliance(TIME_AT_BREAK_29, LEGAL_BREAK_30);
		
		assertEquals(ValidationState.NOT_VALID_TOTAL_BREAK_ERROR, validationTotalBreak);
	}
	
	@Test
	void TimeAtBreak30AndLegalBreakRequired30_isCompliant() {
		ValidationState validationTotalBreak = dummyInputValidationControllerStub
				.checkTotalBreakCompliance(TIME_AT_BREAK_30, LEGAL_BREAK_30);
		
		assertEquals(ValidationState.VALID, validationTotalBreak);
	}
	
	@Test
	void TimeAtBreak31AndLegalBreakRequired30_isCompliant() {
		ValidationState validationTotalBreak = dummyInputValidationControllerStub
				.checkTotalBreakCompliance(TIME_AT_BREAK_31, LEGAL_BREAK_30);
		
		assertEquals(ValidationState.VALID, validationTotalBreak);
	}
	@Test
	void TimeAtBreak44AndLegalBreakRequired45_isNotCompliant_breakIsToShort() {
		ValidationState validationTotalBreak = dummyInputValidationControllerStub
				.checkTotalBreakCompliance(TIME_AT_BREAK_44, LEGAL_BREAK_45);
		
		assertEquals(ValidationState.NOT_VALID_TOTAL_BREAK_ERROR, validationTotalBreak);
	}
	
	@Test
	void TimeAtBreak45AndLegalBreakRequired45_isCompliant() {
		ValidationState validationTotalBreak = dummyInputValidationControllerStub
				.checkTotalBreakCompliance(TIME_AT_BREAK_45, LEGAL_BREAK_45);
		
		assertEquals(ValidationState.VALID, validationTotalBreak);
	}
	
	@Test
	void TimeAtBreak46AndLegalBreakRequired45_isCompliant() {
		ValidationState validationTotalBreak = dummyInputValidationControllerStub
				.checkTotalBreakCompliance(TIME_AT_BREAK_46, LEGAL_BREAK_45);
		
		assertEquals(ValidationState.VALID, validationTotalBreak);
	
	}
}
