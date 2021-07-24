package test.calculationInputValidationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import models.Timespann;

//used kind of Stub class because cannot use private methods in tests otherwise
class DurationBetweenWorkingDaysStubTest {
	
	private LocalDate today = LocalDate.now();
	private LocalDate yesterday = LocalDate.now().minusDays(1);
		
	private LocalDateTime workBeginToday_7_01 = LocalDateTime.of(today, LocalTime.of(7,1));
	private LocalDateTime workBeginToday_7_00 = LocalDateTime.of(today, LocalTime.of(7,0));
	
	private LocalDateTime workEndYesterday_20_00 = LocalDateTime.of(yesterday, LocalTime.of(20,0));
	private LocalDateTime workEndYesterday_20_01 = LocalDateTime.of(yesterday, LocalTime.of(20,1));
	
	private InputValidationControllerStub dummyInputValidationControllerStub = new InputValidationControllerStub(null,
			null, null, null, null, null, null, null);
	
	@Test
	void daysDifferenceFromYesterday20_00_ToToday7_00_is11H_isValid() {
		String validationBetweenDays = (dummyInputValidationControllerStub
				.checkDurationBetweenWorkingDays(workEndYesterday_20_00, workBeginToday_7_00))
				.toString();
		
		assertEquals("VALID", validationBetweenDays);
	}
	
	@Test
	void daysDifferenceFromYesterday20_00_ToToday7_01_is11H01M_isValid() {
		String validationBetweenDays = (dummyInputValidationControllerStub
				.checkDurationBetweenWorkingDays(workEndYesterday_20_00, workBeginToday_7_01))
				.toString();
		
		assertEquals("VALID", validationBetweenDays);
	}
	
	@Test
	void daysDifferenceFromYesterday20_01_ToToday7_00_is10H59M_isNotValid() {
		String validationBetweenDays = (dummyInputValidationControllerStub
				.checkDurationBetweenWorkingDays(workEndYesterday_20_01, workBeginToday_7_00))
				.toString();
		
		assertEquals("NOT_VALID_DURATION_BETWEEN_WORKING_DAYS_ERROR", validationBetweenDays);
	}
	
}
