package test.calculationInputValidationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import models.ValidationState;


class WorkingTimeOverTenHoursTest {

	private InputValidationControllerStub dummyInputValidationControllerStub = new InputValidationControllerStub(
			null, null, null, null, null, null, null, null, null);
	
	@Test
	void workingTimeIs10H_isValid() {
		ValidationState validationWorkingTime = dummyInputValidationControllerStub
				.checkTotalWorkingTimeOverTenHours(Duration.ofHours(10));
		
		assertEquals(ValidationState.VALID, validationWorkingTime);
	}
	
	

	@Test
	void workingTimeIs9H59M_isValid() {
		ValidationState validationWorkingTime = dummyInputValidationControllerStub
				.checkTotalWorkingTimeOverTenHours(Duration.parse("PT9H59M"));
		
		assertEquals(ValidationState.VALID, validationWorkingTime);
	}
	
	@Test
	void workingTimeIs10H01M_isNotValid() {
		
		ValidationState validationWorkingTime = dummyInputValidationControllerStub
				.checkTotalWorkingTimeOverTenHours(Duration.parse("PT10H1M"));
		
		assertEquals(ValidationState.NOT_VALID_WORKING_TIME_OVER_TEN_HOURS, validationWorkingTime);
	}
}
