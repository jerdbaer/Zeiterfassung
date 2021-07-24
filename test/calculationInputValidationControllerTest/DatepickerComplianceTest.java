package test.calculationInputValidationControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class DatepickerComplianceTest {

	private static final LocalDate TODAY = LocalDate.now();
	private static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
	private static final LocalDate TOMORROW = LocalDate.now().plusDays(1);
	private static final LocalDate DATE_31_DAYS_BACK = LocalDate.now().minusDays(31);
	private static final LocalDate DATE_32_DAYS_BACK = LocalDate.now().minusDays(32);

	private InputValidationControllerStub dummyInputValidationControllerStub = new InputValidationControllerStub(null,
			null, null, null, null, null, null, null);

	@Test
	void inputDateIsToday_isCompliant() {
		String validationDate = dummyInputValidationControllerStub.checkDatepickerCompliance(TODAY).toString();

		assertEquals("VALID", validationDate);
	}

	@Test
	void inputDateIsTomorrow_isNotCompliant_DateInFuture() {
		String validationDate = dummyInputValidationControllerStub.checkDatepickerCompliance(TOMORROW).toString();

		assertEquals("NOT_VALID_SELECTED_DAY_IS_IN_THE_FUTURE", validationDate);
	}

	@Test
	void inputDateIsYesterday_isCompliant() {
		String validationDate = dummyInputValidationControllerStub.checkDatepickerCompliance(YESTERDAY).toString();

		assertEquals("VALID", validationDate);
	}

	@Test
	void inputDateIsPast31Days_isCompliant() {
		String validationDate = dummyInputValidationControllerStub.checkDatepickerCompliance(DATE_31_DAYS_BACK)
				.toString();

		assertEquals("VALID", validationDate);
	}

	@Test
	void inputDateIsPast31Days_isNotCompliant_DateToFarInPast() {
		String validationDate = dummyInputValidationControllerStub.checkDatepickerCompliance(DATE_32_DAYS_BACK)
				.toString();

		assertEquals("NOT_VALID_SELECTED_DAY_IS_TO_FAR_IN_THE_PAST", validationDate);
	}

}
