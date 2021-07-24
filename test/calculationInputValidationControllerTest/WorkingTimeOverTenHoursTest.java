package test.calculationInputValidationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.Test;


class WorkingTimeOverTenHoursTest {

	private InputValidationControllerStub dummyInputValidationControllerStub = new InputValidationControllerStub(null,
			null, null, null, null, null, null, null);
	
	@Test
	void workingTimeIs10H_isValid() {
		String validationWorkingTime = dummyInputValidationControllerStub
				.checkTotalWorkingTimeOverTenHours(Duration.ofHours(10))
				.toString();
		
		assertEquals("VALID", validationWorkingTime);
	}
	
	

	@Test
	void workingTimeIs9H59M_isValid() {
		String validationWorkingTime = dummyInputValidationControllerStub
				.checkTotalWorkingTimeOverTenHours(Duration.parse("PT9H59M"))
				.toString();
		
		assertEquals("VALID", validationWorkingTime);
	}
	
	@Test
	void workingTimeIs10H01M_isNotValid() {
		
		String validationWorkingTime = dummyInputValidationControllerStub
				.checkTotalWorkingTimeOverTenHours(Duration.parse("PT10H1M"))
				.toString();
		
		assertEquals("NOT_VALID_WORKING_TIME_OVER_TEN_HOURS", validationWorkingTime);
	}
}
