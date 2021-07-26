package controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import models.Break;
import models.BreakInterruption;
import models.Interruption;
import models.Timespann;
import models.ValidationState;
import models.Work;

/**
 * Ein Programm zur Validierung der Nutzereingaben einer Arbeitszeiterfassungsanwendung.
 * 
 * @author Tom Weißflog
 * @author Josephine Luksch
 * @version 1.0
 */

public class InputValidationController {

	private ArrayList<Timespann> inputList;
	private Duration legalBreak;
	private Duration timeAtBreak;
	private Duration totalWorkingTime;
	private LocalTime workBegin;
	private LocalTime workEnd;
	private LocalTime workEndYesterday;
	private LocalDate selectedDay;

	private static final Duration MIN_REQUIRED_DURATION_BETWEEN_WORKING_DAYS = Duration.ofHours(11);
	private static final Duration MAX_DAILY_WORKING_TIME = Duration.ofHours(10);
	private static final Duration MAX_WORKING_TIME_WITHOUT_BREAK = Duration.ofHours(6);
	private static final LocalTime WORKING_LIMIT_BEGIN = LocalTime.of(6, 00);
	private static final LocalTime WORKING_LIMIT_END = LocalTime.of(19, 30);
	private static final long DAYS_FOR_REVISION_RELIABILITY = 31;

	/**
	 * Konstruktor
	 * Legt einen InputValidationController an
	 * 
	 * @param input ist eine Liste mit Arbeitszeiten, Pausenzeiten, Arbeistzeitunterbrechungen und 
	 * Pausenzeitunterbrechungen im Format Timespann
	 * @param legalBreak ist die gesetzliche festgelegte Pausenzeit aufgrund der Arbeitszeit im Format hh:mm
	 * @param totalWorkingTime ist die Gesamtarbeitzeit im Format hh:mm
	 * @param timeAtBreak ist die Gesamtpausenzeit im Format hh:mm
	 * @param workBegin ist der Beginn der Arbeitszeit am Arbeitstag im Format hh:mm
	 * @param workEnd ist das Ende der Arbeitszeit am Arbeitstag im Format hh:mm
	 * @param selectedDay ist das Datum des ausgewählten Arbeitstages im Format yyyy.mm.dd
	 * @param workEndYesterday ist das Datum des Vortags des ausgewählten Arbeitstags im Format yyyy.mm.dd
	 * 
	 * @see Timespann
	 * @see checkBreaksInWorkTime()
	 * @see checkDatepickerCompliance()
	 * @see checkDurationBetweenWorkingDays()
	 * @see checkInputExists()
	 * @see checkSingleBreakDurationCompliance()
	 * @see checkTimeOrderIsChronological()
	 * @see checkTotalBreakCompliance()
	 * @see checkTotalWorkingTimeOverTenHours()
	 * @see checkWorkingTimeOverSixHoursWithoutBreak()
	 * @see checkWorkTimeLimits()
	 */
	
	public InputValidationController(ArrayList<Timespann> input, Duration legalBreak, 
			Duration totalWorkingTime, Duration timeAtBreak, LocalTime workBegin, LocalTime workEnd,
			LocalDate selectedDay, LocalTime workEndYesterday) {
		this.inputList = input;
		this.legalBreak = legalBreak;
		this.totalWorkingTime = totalWorkingTime;
		this.timeAtBreak = timeAtBreak;
		this.workBegin = workBegin;
		this.workEnd = workEnd;
		this.selectedDay = selectedDay;
		this.workEndYesterday = workEndYesterday;
	}

	/**
	 * Führt die einzelnen Validierungsberechnungen aus
	 * 
	 * @ return [Rückmeldung]
	 * @ throws NullPointerException
	 */
	public ArrayList<ValidationState> validation() {
		var validation = new ArrayList<ValidationState>();
		try {
			var workBeginToday = LocalDateTime.of(selectedDay, workBegin);
			var workEndYesterdayLDT = LocalDateTime.of(selectedDay.minusDays(1), workEndYesterday);
			validation.add(checkDurationBetweenWorkingDays(workEndYesterdayLDT, workBeginToday));
		} catch (NullPointerException e) {
			validation.add(ValidationState.NOT_VALID_NO_DATE_SELECTED);
		}
		validation.add(checkInputExists(inputList));
		validation.add(checkTimeOrderIsChronological(inputList));
		validation.add(checkBreaksInWorkTime(inputList, workBegin, workEnd));
		validation.add(checkTotalBreakCompliance(timeAtBreak, legalBreak));
		validation.add(checkSingleBreakDurationCompliance(inputList, legalBreak));
		validation.add(checkDatepickerCompliance(selectedDay));
		validation.add(checkWorkTimeLimits(workBegin, workEnd));
		validation.add(checkTotalWorkingTimeOverTenHours(totalWorkingTime));
		
		//checkWorkingTimeOverSixHoursWithoutBreak only works with valid input
		var areAllPreviousValidationsValid = validation.stream().allMatch(elm -> elm.equals(ValidationState.VALID));
		if(areAllPreviousValidationsValid)
			validation.add(checkWorkingTimeOverSixHoursWithoutBreak(inputList));

		return validation;

	}

	/**
	 * Validiert ob zwischen dem Arbeitszeitende des Vortags und dem Arbeitsbeginn des ausgewählten Arbeitstags 
	 * eine gesetzlich festgelegte Ruhezeit eingehalten wird
	 * Stand 2021: 11 Stunden
	 * 
	 * @param workEndYesterday ist das Ende der Arbeitszeit des Vortages des ausgewählten Tages, welche aus der 
	 * Datenbank gezogen wird im Format yyyy.mm.dd hh:mm:ss
	 * @param workBeginToday ist der Beginn der Arbeitszeit des ausgewählten Tages im Format yyyy.mm.dd hh:mm:ss
	 * 
	 * @return [Rückmeldung]
	 */
	
	private ValidationState checkDurationBetweenWorkingDays(LocalDateTime workEndYesterday,
			LocalDateTime workBeginToday) {
		var duration = Duration.between(workEndYesterday, workBeginToday);

		return duration.minus(MIN_REQUIRED_DURATION_BETWEEN_WORKING_DAYS).isNegative()
				? ValidationState.NOT_VALID_DURATION_BETWEEN_WORKING_DAYS_ERROR
				: ValidationState.VALID;
	}

	/**
	 * Validiert, ob länger als die gesetzlich maximal zulässige Arbeitszeit pro Arbeitstag gearbeitet wurde
	 * Stand 2021: 10 Stunden
	 * 
	 * @param totalWorkingTime ist die Gesamtarbeitszeit eines Arbeitstages im Format hh:mm
	 * 
	 * @return [Rückmeldung]
	 */
	
	private ValidationState checkTotalWorkingTimeOverTenHours(Duration totalWorkingTime) {
		
		return (totalWorkingTime.compareTo(MAX_DAILY_WORKING_TIME) > 0)
				? ValidationState.NOT_VALID_WORKING_TIME_OVER_TEN_HOURS
				: ValidationState.VALID;
	}

	/**
	 * Validiert, der Beginn und das Ende der Arbeitszeit in dem betrieblich festgelegten Arbeitszeitrahmen liegen
	 * 
	 * @param workBegin ist der Beginn der Arbeitszeit im Format hh:00
	 * @param workEnd ist das Ende der Arbeitszeit im Format hh:00
	 * 
	 * @return [Rückmeldung]
	 */
	
	private ValidationState checkWorkTimeLimits(LocalTime workBegin, LocalTime workEnd) {
		if (workBegin.isBefore(WORKING_LIMIT_BEGIN))
			return ValidationState.VALID_WORKBEGIN_IS_BEFORE_6_00;

		if (workEnd.isAfter(WORKING_LIMIT_END))
			return ValidationState.VALID_WORKEND_IS_AFTER_19_30;
		
		return ValidationState.VALID;
	}

	/**
	 * Validiert, ob das ausgewählte Datum in den zulässigen Grenzen liegen:
	 * Es kann keine Arbeitszeit für Tage in der Zukunft erfasst werden und es darf wegen der Bedingung der 
	 * Revisionssicherheit nur eine festgelegte Anzahl an Tagen in der Vergangenheit zum immer aktuellen Tag 
	 * zurückgegangen werden.
	 * 
	 * @param selectedDay ist das Datum des ausgewählten Arbeitstages im Format yyyy.mm.dd
	 * 
	 * @return [Rückmeldung]
	 */
	
	private ValidationState checkDatepickerCompliance(LocalDate selectedDay) {
		try {
			if (selectedDay.isAfter(LocalDate.now()))
				return ValidationState.NOT_VALID_SELECTED_DAY_IS_IN_THE_FUTURE;
			if (selectedDay.isBefore(LocalDate.now().minusDays(DAYS_FOR_REVISION_RELIABILITY)))
				return ValidationState.NOT_VALID_SELECTED_DAY_IS_TO_FAR_IN_THE_PAST;
		} catch (NullPointerException e) {
			return ValidationState.NOT_VALID_NO_DATE_SELECTED;
		}
		
		return ValidationState.VALID;
	}

	/**
	 * Validiert, ob die gesetzlich festgelegte Pausenzeit auch in Abhängigkeit zur Länge der Einzelpausenlängen
	 * erreicht wird. Um als gesetzliche Pausenlänge zu gelten, muss eine festgelegte Pausenlänge erreicht werden. 
	 * Die Gesamtpausenzeit kann aus Einzelpausen unterhalb, gleich oder oberhalb dieser gesetzlich geforderten 
	 * Pausenlänge liegen. Es ist jedoch notwendig
	 * Stand 2021: 15 Minuten
	 * 
	 * @param formattedInput ist eine Liste mit Arbeitszeiten, Pausenzeiten, Arbeistzeitunterbrechungen und 
	 * Pausenzeitunterbrechungen im Format Timespann
	 * @param legalBreak ist die gesetzliche festgelegte Pausenzeit aufgrund der Arbeitszeit im Format hh:mm
	 * @return [Rückmeldung]
	 */
	
	private ValidationState checkSingleBreakDurationCompliance(ArrayList<Timespann> formattedInput,
			Duration legalBreak) {
		var legalBreakList = new ArrayList<Timespann>();
		Duration compliantBreakDuration = Duration.ZERO;
		formattedInput.stream().filter(elm -> elm instanceof Break).filter(elm -> ((Break) elm).isLegal())
				.forEach(elm -> legalBreakList.add(elm));
		formattedInput.stream().filter(elm -> elm instanceof Interruption).filter(elm -> ((Interruption) elm).isLegal())
				.forEach(elm -> legalBreakList.add(elm));
		for (Timespann breaks : legalBreakList) {
			compliantBreakDuration = compliantBreakDuration.plus(breaks.getDuration());
		}
		
		return compliantBreakDuration.minus(legalBreak).isNegative()
				? ValidationState.NOT_VALID_SINGLE_BREAK_DURATIONS_ERROR
				: ValidationState.VALID;
	}

	/**
	 * Validiert, ob die einzelnen Pausenzeiten innerhalb eines Arbeitsabschnittes liegen und dabei weder am Anfang 
	 * noch am Ende des Arbeitstages liegen. Dafür dürfen Beginn und Ende der Pausenzeiten nicht exakt mit den Zeiten 
	 * des Arbeitsbeginns und Arbeitsendes des Arbeitstages übereinstimmen.
	 * 
	 * @param formattedInput ist eine Liste mit Arbeitszeiten, Pausenzeiten, Arbeistzeitunterbrechungen und 
	 * Pausenzeitunterbrechungen im Format Timespann
	 * @param workBegin ist der Beginn der Arbeitszeit am Arbeitstag im Format hh:mm
	 * @param workEnd ist das Ende der Arbeitszeit am Arbeitstag im Format hh:mm
	 * 
	 * @return [Rückmeldung]
	 */
	private ValidationState checkBreaksInWorkTime(ArrayList<Timespann> formattedInput, LocalTime workBegin,
			LocalTime workEnd) {
		var breakList = new ArrayList<Break>();
		formattedInput.stream().filter(elm -> elm instanceof Break).forEach(abreak -> breakList.add((Break)abreak));
		var workList = new ArrayList<Work>();
		formattedInput.stream().filter(elm -> elm instanceof Work).forEach(work -> workList.add((Work)work));

		for (Break breaks : breakList) {
			if (breaks.getBegin().equals(workBegin) || breaks.getEnd().equals(workEnd))
				return ValidationState.NOT_VALID_BREAK_CANNOT_BE_AT_WORKING_BEGIN_OR_END;

			var isBreakInWorksegmentCheck = workList.stream()
					.filter( work -> work.getBegin().isBefore(breaks.getBegin()) 
							&& work.getEnd().isAfter(breaks.getEnd()))
					.findFirst().isPresent();
			if (!isBreakInWorksegmentCheck)
				return ValidationState.NOT_VALID_BREAK_IS_NOT_IN_WORKTIME;
		}
		
		return ValidationState.VALID;
	}

	/**
	 * Validiert, ob die eingetragenen Arbeitszeiten und Pausenzeiten chronologisch (von früh zu spät) geordnet sind. 
	 * Außerdem wird validiert, ob die Startzeit immer vor der Endzeit eines Zeitabschnitts eingetragen ist.
	 * 
	 * @param formattedInput ist eine Liste mit Arbeitszeiten, Pausenzeiten, Arbeistzeitunterbrechungen und 
	 * Pausenzeitunterbrechungen im Format Timespann
	 * 
	 * @return [Rückmeldung]
	 */
	
	private ValidationState checkTimeOrderIsChronological(ArrayList<Timespann> formattedInput) {
		if (formattedInput.stream().filter(elm -> (elm instanceof Interruption) || (elm instanceof BreakInterruption))
				.anyMatch(elm -> elm.getDuration().isNegative()))
			return ValidationState.NOT_VALID_TIME_INPUT_MUST_BE_IN_CHRONICAL_ORDER;
		else if (formattedInput.stream().anyMatch(elm -> elm.getDuration().isNegative()))
			return ValidationState.NOT_VALID_START_IS_AFTER_END;
		
		return ValidationState.VALID;
	}

	/**
	 * Validiert, ob es mindestens einen vollständigen Eintrag eines Arbeitszeitabschnittes gibt, d.h. es muss sowohl
	 * Startzeit (hh:mm) als auch Endzeit (hh:mm) vollständig eingetragen sein)
	 * 
	 * @param formattedInput ist eine Liste mit Arbeitszeiten, Pausenzeiten, Arbeistzeitunterbrechungen und 
	 * Pausenzeitunterbrechungen im Format Timespann
	 * 
	 * @return [Rückmeldung]
	 */
	
	private ValidationState checkInputExists(ArrayList<Timespann> formattedInput) {
		
		return (formattedInput.stream().noneMatch(elm -> elm instanceof Work))
				? ValidationState.NOT_VALID_NO_INPUT_FOUND
				: ValidationState.VALID;
	}

	/**
	 * Validiert, ob die Gesamtpausenzeit die gesetzlich geforderte Pausenzeit erfüllt.
	 * 
	 * @param timeAtBreak ist die Gesamtpausenzeit im Format hh:mm
	 * @param legalBreak ist die gesetzliche festgelegte Pausenzeit aufgrund der Arbeitszeit im Format hh:mm
	 * 
	 * @return [Rückmeldung]
	 */
	
	private ValidationState checkTotalBreakCompliance(Duration timeAtBreak, Duration legalBreak) {
		
		return timeAtBreak.minus(legalBreak).isNegative() 
				? ValidationState.NOT_VALID_TOTAL_BREAK_ERROR
				: ValidationState.VALID;
	}

	/**
	 * Validiert, ob Arbeitsabschnitte ohne Pausen oder Unterbrechungen vorhanden sind, die länger sind als eine 
	 * gesetzlichzulässige Länge.
	 * Stand 2021: max. 6 Stunden ohne Pause
	 * 
	 * @param formattedInput ist eine Liste mit Arbeitszeiten, Pausenzeiten, Arbeistzeitunterbrechungen und 
	 * Pausenzeitunterbrechungen im Format Timespann
	 * @return [Rückmeldung]
	 */
	
	private ValidationState checkWorkingTimeOverSixHoursWithoutBreak(ArrayList<Timespann> formattedInput) {
		var workList = new ArrayList<Work>();
		formattedInput.stream().filter(elm -> elm instanceof Work).forEach(work -> workList.add((Work) work));

		var breakList = new ArrayList<Break>();
		formattedInput.stream().filter(elm -> elm instanceof Break).forEach(breaks -> breakList.add((Break) breaks));

		for (Work work : workList) {
			var listOfBeginAndEndTimesAfterWorkBegin = new ArrayList<LocalTime>();
			formattedInput.stream()
					.filter(elm -> elm.getBegin().isAfter(work.getBegin()) || elm.getEnd().isAfter(work.getBegin()))
					.forEach(other -> {
						listOfBeginAndEndTimesAfterWorkBegin.add(other.getBegin());
						listOfBeginAndEndTimesAfterWorkBegin.add(other.getEnd());
					});
			var endWorkSegment = listOfBeginAndEndTimesAfterWorkBegin.stream()
					.filter(elm -> !(elm.equals(work.getBegin()))).sorted().findFirst().get();

			if ((Duration.between(work.getBegin(), endWorkSegment).compareTo(MAX_WORKING_TIME_WITHOUT_BREAK) > 0))
				return ValidationState.NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR;
		}

		for (Break breaks : breakList) {
			var listOfEndTimesAfterBreakEnd = new ArrayList<LocalTime>();
			formattedInput.stream().filter(elm -> elm.getEnd().isAfter(breaks.getEnd()))
					.forEach(other -> listOfEndTimesAfterBreakEnd.add(other.getEnd()));
			var endWorkSegmentAfterBreak = listOfEndTimesAfterBreakEnd.stream().sorted().findFirst().get();
			if ((Duration.between(breaks.getEnd(), endWorkSegmentAfterBreak)
					.compareTo(MAX_WORKING_TIME_WITHOUT_BREAK) > 0))
				return ValidationState.NOT_VALID_WORKING_TIME_WITHOUT_BREAK_ERROR;
		}

		return ValidationState.VALID;
	}
	
}
