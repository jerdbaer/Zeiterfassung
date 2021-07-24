package test.calculationInputValidationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

class WorkTimeLimitsTest {
	
	private final LocalTime WORK_BEGIN_5_59 = LocalTime.of(5,59);
	private final LocalTime WORK_BEGIN_6_00 = LocalTime.of(6,0);
	private final LocalTime WORK_BEGIN_6_01 = LocalTime.of(6,1);
	
	private final LocalTime WORK_END_19_29 = LocalTime.of(19,29);
	private final LocalTime WORK_END_19_30 = LocalTime.of(19,30);
	private final LocalTime WORK_END_19_31 = LocalTime.of(19,31);
	
	private InputValidationControllerStub dummyInputValidationControllerStub = new InputValidationControllerStub(null,
			null, null, null, null, null, null, null);
	
	@Test
	void beginAndEndInLimitsExcludingEquals_IsWithinLimits() {
		String validationLimits = dummyInputValidationControllerStub
				.checkWorkTimeLimits(WORK_BEGIN_6_01, WORK_END_19_29)
				.toString();
		
		assertEquals("VALID", validationLimits);
	}
	
	@Test
	void beginEqualsBeginLimitAndEndEqualsEndLimit_IsWithinLimits() {
		String validationLimits = dummyInputValidationControllerStub
				.checkWorkTimeLimits(WORK_BEGIN_6_00, WORK_END_19_30)
				.toString();
		
		assertEquals("VALID", validationLimits);
	}
	
	@Test
	void beginInLimitAndEndOutOfLimit_exceedsLimits_WorkendToLate() {
		String validationLimits = dummyInputValidationControllerStub
				.checkWorkTimeLimits(WORK_BEGIN_6_01, WORK_END_19_31)
				.toString();
		
		assertEquals("VALID_WORKEND_IS_AFTER_19_30", validationLimits);
	}
	
	@Test
	void beginOutOfLimitAndEndInLimit_exceedsLimits_WorkBeginToEarly() {
		String validationLimits = dummyInputValidationControllerStub
				.checkWorkTimeLimits(WORK_BEGIN_5_59, WORK_END_19_29)
				.toString();
		
		assertEquals("VALID_WORKBEGIN_IS_BEFORE_6_00", validationLimits);
	}
	
	@Test
	void beginOutOfLimitAndEndOutOfLimit_firstCatchIsWorkBeginToEarly() {
		String validationLimits = dummyInputValidationControllerStub
				.checkWorkTimeLimits(WORK_BEGIN_5_59, WORK_END_19_31)
				.toString();
		
		assertEquals("VALID_WORKBEGIN_IS_BEFORE_6_00", validationLimits);
	}

}
