package test.calculationInputValidationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

import models.ValidationState;

//used kind of Stub class because cannot use private methods in tests otherwise
class DurationBetweenWorkingDaysStubTest {
	
	private static LocalDate today = LocalDate.now();
	private static LocalDate yesterday = LocalDate.now().minusDays(1);
		
	private static LocalDateTime workBeginToday_7_01 = LocalDateTime.of(today, LocalTime.of(7,1));
	private static LocalDateTime workBeginToday_7_00 = LocalDateTime.of(today, LocalTime.of(7,0));
	
	private static LocalDateTime workEndYesterday_20_00 = LocalDateTime.of(yesterday, LocalTime.of(20,0));
	private static LocalDateTime workEndYesterday_20_01 = LocalDateTime.of(yesterday, LocalTime.of(20,1));
	
	private InputValidationControllerStub dummyInputValidationControllerStub = new InputValidationControllerStub(null,
			null, null, null, null, null, null, null);
	
	@Test
	void daysDifferenceFromYesterday20_00_ToToday7_00_is11H_isValid() {
		ValidationState validationBetweenDays = (dummyInputValidationControllerStub
				.checkDurationBetweenWorkingDays(workEndYesterday_20_00, workBeginToday_7_00));
		
		assertEquals(ValidationState.VALID, validationBetweenDays);
	}
	
	@Test
	void daysDifferenceFromYesterday20_00_ToToday7_01_is11H01M_isValid() {
		ValidationState validationBetweenDays = (dummyInputValidationControllerStub
				.checkDurationBetweenWorkingDays(workEndYesterday_20_00, workBeginToday_7_01));
		
		assertEquals(ValidationState.VALID, validationBetweenDays);
	}
	
	@Test
	void daysDifferenceFromYesterday20_01_ToToday7_00_is10H59M_isNotValid() {
		ValidationState validationBetweenDays = (dummyInputValidationControllerStub
				.checkDurationBetweenWorkingDays(workEndYesterday_20_01, workBeginToday_7_00));
		
		assertEquals(ValidationState.NOT_VALID_DURATION_BETWEEN_WORKING_DAYS_ERROR, validationBetweenDays);
	}
	
}
