package test.calculationInputValidationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import models.ValidationState;

class WorkTimeLimitsTest {
	
	private static final LocalTime WORK_BEGIN_5_59 = LocalTime.of(5,59);
	private static final LocalTime WORK_BEGIN_6_00 = LocalTime.of(6,0);
	private static final LocalTime WORK_BEGIN_6_01 = LocalTime.of(6,1);
	
	private static final LocalTime WORK_END_19_29 = LocalTime.of(19,29);
	private static final LocalTime WORK_END_19_30 = LocalTime.of(19,30);
	private static final LocalTime WORK_END_19_31 = LocalTime.of(19,31);
	
	private InputValidationControllerStub dummyInputValidationControllerStub = new InputValidationControllerStub(
			null, null, null, null, null, null, null, null, null);
	
	@Test
	void beginAndEndInLimitsExcludingEquals_IsWithinLimits() {
		ValidationState validationLimits = dummyInputValidationControllerStub
				.checkWorkTimeLimits(WORK_BEGIN_6_01, WORK_END_19_29);
		
		assertEquals(ValidationState.VALID, validationLimits);
	}
	
	@Test
	void beginEqualsBeginLimitAndEndEqualsEndLimit_IsWithinLimits() {
		ValidationState validationLimits = dummyInputValidationControllerStub
				.checkWorkTimeLimits(WORK_BEGIN_6_00, WORK_END_19_30);
		
		assertEquals(ValidationState.VALID, validationLimits);
	}
	
	@Test
	void beginInLimitAndEndOutOfLimit_exceedsLimits_WorkendToLate() {
		ValidationState validationLimits = dummyInputValidationControllerStub
				.checkWorkTimeLimits(WORK_BEGIN_6_01, WORK_END_19_31);

		assertEquals(ValidationState.VALID_WORKEND_IS_AFTER_19_30, validationLimits);
	}
	
	@Test
	void beginOutOfLimitAndEndInLimit_exceedsLimits_WorkBeginToEarly() {
		ValidationState validationLimits = dummyInputValidationControllerStub
				.checkWorkTimeLimits(WORK_BEGIN_5_59, WORK_END_19_29);
	
		assertEquals(ValidationState.VALID_WORKBEGIN_IS_BEFORE_6_00, validationLimits);
	}
	
	@Test
	void beginOutOfLimitAndEndOutOfLimit_firstCatchIsWorkBeginToEarly() {
		ValidationState validationLimits = dummyInputValidationControllerStub
				.checkWorkTimeLimits(WORK_BEGIN_5_59, WORK_END_19_31);

		assertEquals(ValidationState.VALID_WORKBEGIN_IS_BEFORE_6_00, validationLimits);
	}

}
