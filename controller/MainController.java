package controller;

import models.Break;
import models.Interruption;
import models.Timespann;
import models.ValidationState;
import models.Work;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.HBox;

public class MainController {

	@FXML
	private DatePicker datepicker;

	@FXML
	private HBox hBoxWork1;

	@FXML
	private HBox hBoxBreak1;

	@FXML
	private TextField txtfieldWorkStart1Hours;
	@FXML
	private TextField txtfieldWorkStart1Minutes;

	@FXML
	private TextField txtfieldWorkEnd1Hours;

	@FXML
	private TextField txtfieldWorkEnd1Minutes;

	@FXML
	private Button btnWorkAdd1;

	@FXML
	private HBox hBoxWork2;

	@FXML
	private TextField txtfieldWorkStart2Hours;

	@FXML
	private TextField txtfieldWorkStart2Minutes;

	@FXML
	private TextField txtfieldWorkEnd2Hours;

	@FXML
	private TextField txtfieldWorkEnd2Minutes;

	@FXML
	private Button btnWorkAdd2;

	@FXML
	private HBox hBoxWork3;

	@FXML
	private TextField txtfieldWorkStart3Hours;

	@FXML
	private TextField txtfieldWorkStart3Minutes;

	@FXML
	private TextField txtfieldWorkEnd3Hours;

	@FXML
	private TextField txtfieldWorkEnd3Minutes;

	@FXML
	private TextField txtfieldBreakStart1Hours;

	@FXML
	private TextField txtfieldBreakStart1Minutes;

	@FXML
	private TextField txtfieldBreakEnd1Hours;

	@FXML
	private TextField txtfieldBreakEnd1Minutes;

	@FXML
	private Button btnBreakAdd1;

	@FXML
	private HBox hBoxBreak2;

	@FXML
	private TextField txtfieldBreakStart2Hours;

	@FXML
	private TextField txtfieldBreakStart2Minutes;

	@FXML
	private TextField txtfieldBreakEnd2Hours;

	@FXML
	private TextField txtfieldBreakEnd2Minutes;

	@FXML
	private Button btnBreakAdd2;

	@FXML
	private HBox hBoxBreak3;

	@FXML
	private TextField txtfieldBreakStart3Hours;

	@FXML
	private TextField txtfieldBreakStart3Minutes;

	@FXML
	private TextField txtfieldBreakEnd3Hours;

	@FXML
	private TextField txtfieldBreakEnd3Minutes;

	@FXML
	private Button btnBreakAdd3;

	@FXML
	private HBox hBoxBreak4;

	@FXML
	private TextField txtfieldBreakStart4Hours;

	@FXML
	private TextField txtfieldBreakStart4Minutes;

	@FXML
	private TextField txtfieldBreakEnd4Hours;

	@FXML
	private TextField txtfieldBreakEnd4Minutes;

	@FXML
	private Button btnBreakAdd4;

	@FXML
	private HBox hBoxBreak5;

	@FXML
	private TextField txtfieldBreakStart5Hours;

	@FXML
	private TextField txtfieldBreakStart5Minutes;

	@FXML
	private TextField txtfieldBreakEnd5Hours;

	@FXML
	private TextField txtfieldBreakEnd5Minutes;

	@FXML
	private Label labelErrortxt;

	@FXML
	private Button btnInputValidation;

	@FXML
	private Button btnDone;

	@FXML
	public void initialize() {

		// restrict Input
		var mainViewInputRestrictionController = new MainViewInputRestrictionController();

		TextField[] textfieldHour = { txtfieldWorkStart1Hours, txtfieldWorkStart2Hours, txtfieldWorkStart3Hours,
				txtfieldWorkEnd1Hours, txtfieldWorkEnd2Hours, txtfieldWorkEnd3Hours,

				txtfieldBreakStart1Hours, txtfieldBreakStart2Hours, txtfieldBreakStart3Hours, txtfieldBreakStart4Hours,
				txtfieldBreakStart5Hours, txtfieldBreakEnd1Hours, txtfieldBreakEnd2Hours, txtfieldBreakEnd3Hours,
				txtfieldBreakEnd4Hours, txtfieldBreakEnd5Hours };

		mainViewInputRestrictionController.setTextfieldHour(textfieldHour);

		TextField[] textfieldMinute = { txtfieldWorkStart1Minutes, txtfieldWorkStart2Minutes, txtfieldWorkStart3Minutes,
				txtfieldWorkEnd1Minutes, txtfieldWorkEnd2Minutes, txtfieldWorkEnd3Minutes,

				txtfieldBreakStart1Minutes, txtfieldBreakStart2Minutes, txtfieldBreakStart3Minutes,
				txtfieldBreakStart4Minutes, txtfieldBreakStart5Minutes, txtfieldBreakEnd1Minutes,
				txtfieldBreakEnd2Minutes, txtfieldBreakEnd3Minutes, txtfieldBreakEnd4Minutes,
				txtfieldBreakEnd5Minutes };

		mainViewInputRestrictionController.setTextfieldMinute(textfieldMinute);

		mainViewInputRestrictionController.setTextFormatter(textfieldHour, textfieldMinute);

	}

	@FXML
	void computeInput(ActionEvent event) {

		var formattedInput = formatInput();

		// total working time
		Duration totalWorkTime = Duration.ZERO;
		for (Timespann segment : formattedInput) {
			if (segment instanceof Work)
				totalWorkTime = totalWorkTime.plus(segment.getDuration());
			else if (segment instanceof Break)
				totalWorkTime = totalWorkTime.minus(segment.getDuration());
		}
		//-------------------------------
		System.out.println(totalWorkTime);
		//-------------------------------
		
		//workbegin
		var beginlist = new ArrayList<LocalTime>();
		formattedInput.stream().filter(elm -> elm instanceof Work).forEach(work -> beginlist.add(work.getBegin()));
		var begin = beginlist.stream().sorted().findFirst().get();
		
		//------------------------
		System.out.println(begin);
		//------------------------
		
		
		//workend
		var endlist = new ArrayList<LocalTime>();
		formattedInput.stream().filter(elm -> elm instanceof Work).forEach(work -> endlist.add(work.getEnd()));
		var end = endlist.stream().sorted().reduce((first, second) -> second).get();
		
		//------------------------
		System.out.println(end);
		//------------------------
				
	}

	@FXML
	void showHBox(ActionEvent event) {
		

		Button buttonpressed = (Button) event.getSource();
		if (buttonpressed == btnWorkAdd1) {
			hBoxWork2.setVisible(true);
			btnWorkAdd1.setVisible(false);
		} else if (buttonpressed == btnWorkAdd2) {
			hBoxWork3.setVisible(true);
			btnWorkAdd2.setVisible(false);
		} else if (buttonpressed == btnBreakAdd1) {
			hBoxBreak2.setVisible(true);
			btnBreakAdd1.setVisible(false);
		} else if (buttonpressed == btnBreakAdd2) {
			hBoxBreak3.setVisible(true);
			btnBreakAdd2.setVisible(false);
		} else if (buttonpressed == btnBreakAdd3) {
			hBoxBreak4.setVisible(true);
			btnBreakAdd3.setVisible(false);
		} else if (buttonpressed == btnBreakAdd4) {
			hBoxBreak5.setVisible(true);
			btnBreakAdd4.setVisible(false);
		}

	}

	@FXML
	void validateInput(ActionEvent event) {

		// testing the UserInput

		var formattedInput = formatInput();
		var worklist = new ArrayList<Work>();
		formattedInput.stream().filter(elm -> elm instanceof Work).forEach(work -> worklist.add((Work) work));
		var breaklist = new ArrayList<Break>();
		formattedInput.stream().filter(elm -> elm instanceof Break).forEach(abreak -> breaklist.add((Break) abreak));
		var validation = new ArrayList<ValidationState>();
		if (formattedInput.stream().noneMatch(elm -> elm instanceof Work))
			validation.add(ValidationState.NOT_VALID_NO_INPUT_FOUND);
		if(formattedInput.stream().filter(elm -> elm instanceof Interruption).anyMatch(elm -> elm.getDuration().isNegative()))
			validation.add(ValidationState.NOT_VALID_WORKSEGMENTS_MUST_BE_IN_CHRONICAL_ORDER);
		else if (formattedInput.stream().anyMatch(elm -> elm.getDuration().isNegative()))
			validation.add(ValidationState.NOT_VALID_START_IS_AFTER_END);

		for (Break breaks : breaklist) {
			var isBreakinbetweenWorksegment = worklist.stream().filter(
					work -> work.getBegin().isBefore(breaks.getBegin()) && work.getEnd().isAfter(breaks.getEnd()))
					.findFirst().isPresent();
			if (!isBreakinbetweenWorksegment)
				validation.add(ValidationState.NOT_VALID_BREAK_IS_NOT_IN_WORKTIME);
		}


		// datepicker
		var selectedDay = datepicker.getValue();
		var days = 31; // how many days are possible to edit till now
		try {
			if (selectedDay.isAfter(LocalDate.now()))
				validation.add(ValidationState.NOT_VALID_SELECTED_DAY_IS_IN_THE_FUTURE);
			if (selectedDay.isBefore(LocalDate.now().minusDays(days)))
				validation.add(ValidationState.NOT_VALID_SELECTED_DAY_IS_TO_FAR_IN_THE_PAST);
		} catch (NullPointerException e) {
			validation.add(ValidationState.NOT_VALID_NO_INPUT_FOUND);
		}

		// If all Validations got passed ? do : else
		if (validation.stream().allMatch(elm -> elm.equals(ValidationState.VALID))) {
			btnDone.setDisable(false);
			labelErrortxt.setText("VALID");
		} else {
			var Error = validation.stream().filter(elm -> !(elm.equals(ValidationState.VALID))).findFirst().get()
					.toString();
			labelErrortxt.setText(Error);
			btnDone.setDisable(true);

		}

	}

	private ArrayList<Timespann> formatInput() {
		// all TextFields
		TextField[] start1 = { txtfieldWorkStart1Hours, txtfieldWorkStart1Minutes, txtfieldWorkEnd1Hours,
				txtfieldWorkEnd1Minutes };
		
		TextField[] interruption1 = { txtfieldWorkEnd1Hours, txtfieldWorkEnd1Minutes, txtfieldWorkStart2Hours, txtfieldWorkStart2Minutes};

		TextField[] start2 = { txtfieldWorkStart2Hours, txtfieldWorkStart2Minutes, txtfieldWorkEnd2Hours,
				txtfieldWorkEnd2Minutes };
		
		TextField[] interruption2 = { txtfieldWorkEnd2Hours, txtfieldWorkEnd2Minutes, txtfieldWorkStart3Hours, txtfieldWorkStart3Minutes};

		TextField[] start3 = { txtfieldWorkStart3Hours, txtfieldWorkStart3Minutes, txtfieldWorkEnd3Hours,
				txtfieldWorkEnd3Minutes };

		TextField[] break1 = { txtfieldBreakStart1Hours, txtfieldBreakStart1Minutes, txtfieldBreakEnd1Hours,
				txtfieldBreakEnd1Minutes };

		TextField[] break2 = { txtfieldBreakStart2Hours, txtfieldBreakStart2Minutes, txtfieldBreakEnd2Hours,
				txtfieldBreakEnd2Minutes };

		TextField[] break3 = { txtfieldBreakStart3Hours, txtfieldBreakStart3Minutes, txtfieldBreakEnd3Hours,
				txtfieldBreakEnd3Minutes };

		TextField[] break4 = { txtfieldBreakStart4Hours, txtfieldBreakStart4Minutes, txtfieldBreakEnd4Hours,
				txtfieldBreakEnd4Minutes };

		TextField[] break5 = { txtfieldBreakStart5Hours, txtfieldBreakStart5Minutes, txtfieldBreakEnd5Hours,
				txtfieldBreakEnd5Minutes };

		// Textfield grouped
		TextField[][] input = { start1, start2, start3, break1, break2, break3, break4, break5, interruption1, interruption2 };

		ArrayList<Timespann> formattedInput = new ArrayList<Timespann>();

		for (TextField[] value : input) {
			try {
				var startHour = Integer.parseInt(value[0].getText());
				var startMinutes = Integer.parseInt(value[1].getText());
				var endHour = Integer.parseInt(value[2].getText());
				var endMinutes = Integer.parseInt(value[3].getText());

				var start = LocalTime.of(startHour, startMinutes);
				var end = LocalTime.of(endHour, endMinutes);
				Timespann timespann;
				if (value.equals(start1) || value.equals(start2) || value.equals(start3))
					timespann = new Work(start, end);
				else if (value.equals(interruption1) || value.equals(interruption2))
					timespann = new Interruption(start, end);
				else
					timespann = new Break(start, end);
				formattedInput.add(timespann);
			} catch (NumberFormatException e) {

			}
		} // -> produced list with work(s) and break(s)
		return formattedInput;
	}

}
