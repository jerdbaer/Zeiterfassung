package test.calculationInputValidationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import models.Break;
import models.Interruption;
import models.Timespann;

class SingleBreakComplianceTest {

	private Duration LEGAL_BREAK_30 = Duration.ofMinutes(30);
	private Duration LEGAL_BREAK_45 = Duration.ofMinutes(45);

	private static final Break BREAK_0 = new Break(LocalTime.of(0, 0), LocalTime.of(0, 0));
	private static final Break BREAK_29 = new Break(LocalTime.of(18, 0), LocalTime.of(18, 29));
	private static final Break BREAK_30 = new Break(LocalTime.of(8, 0), LocalTime.of(8, 30));
	private static final Break BREAK_31 = new Break(LocalTime.of(10, 0), LocalTime.of(10, 31));
	
	private static final Interruption INTERRUPTION_0 = new Interruption(LocalTime.of(0, 0), LocalTime.of(0, 0));
	private static final Interruption INTERRUPTION_44 = new Interruption(LocalTime.of(18, 0), LocalTime.of(18, 44));
	private static final Interruption INTERRUPTION_45 = new Interruption(LocalTime.of(8, 0), LocalTime.of(8, 45));
	private static final Interruption INTERRUPTION_46 = new Interruption(LocalTime.of(10, 0), LocalTime.of(10, 46));

	private InputValidationControllerStub dummyInputValidationControllerStub = new InputValidationControllerStub(null,
			null, null, null, null, null, null, null);

	private ArrayList<Timespann> addInputToList(ArrayList<Timespann> list, Timespann... timespann) {
		for (Timespann element : timespann) {
			list.add(element);
		}
		return list;
	}
	
	/**
	 * Interruption and Breaks implement both Timespann.class with totally same
	 * characteristics therefore LEGAL_BREAK_30 is tested for Break, LEGAL_BREAK_45
	 * is tested for Interruptions
	 */

	// Breaks
	@Test
	void breakIs0AndLegalBreakIs30_isNotCompliant() {
		ArrayList<Timespann> breakList = new ArrayList<Timespann>();
		addInputToList(breakList, BREAK_0);

		String validationSingleBreak = dummyInputValidationControllerStub
				.checkSingleBreakDurationCompliance(breakList, LEGAL_BREAK_30).toString();

		assertEquals("NOT_VALID_SINGLE_BREAK_DURATIONS_ERROR", validationSingleBreak);
	}

	@Test
	void interruptionIs0AndLegalBreakIs45_isNotCompliant() {
		ArrayList<Timespann> breakList = new ArrayList<Timespann>();
		addInputToList(breakList, INTERRUPTION_0);

		String validationSingleBreak = dummyInputValidationControllerStub
				.checkSingleBreakDurationCompliance(breakList, LEGAL_BREAK_45).toString();

		assertEquals("NOT_VALID_SINGLE_BREAK_DURATIONS_ERROR", validationSingleBreak);
	}

	@Test
	void breakIs30AndLegalBreakIs30_isCompliant() {
		ArrayList<Timespann> breakList = new ArrayList<Timespann>();
		addInputToList(breakList, BREAK_30);

		String validationSingleBreak = dummyInputValidationControllerStub
				.checkSingleBreakDurationCompliance(breakList, LEGAL_BREAK_30).toString();

		assertEquals("VALID", validationSingleBreak);
	}

	@Test
	void interruptionIs45AndLegalBreakIs45_isCompliant() {
		ArrayList<Timespann> breakList = new ArrayList<Timespann>();
		addInputToList(breakList, INTERRUPTION_45);

		String validationSingleBreak = dummyInputValidationControllerStub
				.checkSingleBreakDurationCompliance(breakList, LEGAL_BREAK_45).toString();

		assertEquals("VALID", validationSingleBreak);
	}

	@Test
	void breakIs29AndLegalBreakIs30_isCompliant() {
		ArrayList<Timespann> breakList = new ArrayList<Timespann>();
		addInputToList(breakList, BREAK_29);

		String validationSingleBreak = dummyInputValidationControllerStub
				.checkSingleBreakDurationCompliance(breakList, LEGAL_BREAK_30).toString();

		assertEquals("NOT_VALID_SINGLE_BREAK_DURATIONS_ERROR", validationSingleBreak);
	}

	@Test
	void interruptionIs44AndLegalBreakIs45_isCompliant() {
		ArrayList<Timespann> breakList = new ArrayList<Timespann>();
		addInputToList(breakList, INTERRUPTION_44);

		String validationSingleBreak = dummyInputValidationControllerStub
				.checkSingleBreakDurationCompliance(breakList, LEGAL_BREAK_45).toString();

		assertEquals("NOT_VALID_SINGLE_BREAK_DURATIONS_ERROR", validationSingleBreak);
	}

	@Test
	void breakIs31AndLegalBreakIs30_isCompliant() {
		ArrayList<Timespann> breakList = new ArrayList<Timespann>();
		addInputToList(breakList, BREAK_31);

		String validationSingleBreak = dummyInputValidationControllerStub
				.checkSingleBreakDurationCompliance(breakList, LEGAL_BREAK_30).toString();

		assertEquals("VALID", validationSingleBreak);
	}

	@Test
	void interruptionIs46AndLegalBreakIs45_isCompliant() {
		ArrayList<Timespann> breakList = new ArrayList<Timespann>();
		addInputToList(breakList, INTERRUPTION_46);

		String validationSingleBreak = dummyInputValidationControllerStub
				.checkSingleBreakDurationCompliance(breakList, LEGAL_BREAK_45).toString();

		assertEquals("VALID", validationSingleBreak);
	}

	
}
