package test.calculationInputValidationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import models.Timespann;

//used Stub class because cannot use private methods in tests otherwise
class DurationBetweenWorkingDaysStubTest {
	
	private ArrayList<Timespann> dummyList = new ArrayList<Timespann>();
	private Duration dummyDuration = Duration.ZERO;
	private LocalTime dummyTime = LocalTime.of(0,0);
	private LocalDate today = LocalDate.now();
	private LocalDate yesterday = LocalDate.now().minusDays(1);
		
	private LocalDateTime workBeginToday_0_00 = LocalDateTime.of(today, LocalTime.of(0,0));
	private LocalTime workBeginToday_8_00 = LocalTime.of(8, 0);
	
	private LocalDateTime workEndYesterday_0_00 = LocalDateTime.of(yesterday, LocalTime.of(0,0));
	private LocalTime workEndYesterday_16_30 = LocalTime.of(16,30);
	private LocalTime workEndYesterday_23_59 = LocalTime.of(23,59);
	
	
	private InputValidationControllerStub dummyInputValidationControllerStub = new InputValidationControllerStub(null, 
			null, null, null, null, null, null, null, null);
	
	@Test
	void daysDifferenceFrom_0_00_TO_0_00_is24H_isValid() {
		String validationBetweenDays = (dummyInputValidationControllerStub
				.checkDurationBetweenWorkingDays(workEndYesterday_0_00, workBeginToday_0_00))
				.toString();
		
		assertEquals("VALID", validationBetweenDays);
	}
	
	

	
	private ArrayList<Timespann> addInputToList(ArrayList<Timespann> list, Timespann... timespann) {
	for (Timespann element : timespann) {
		list.add(element);
	}
	return list;
	}
}
