package controller;

import models.Break;
import models.BreakInterruption;
import models.CalculationModel;
import models.Interruption;
import models.Timespann;
import models.ValidationState;
import models.Work;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;

import database.GetOvertime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

//------------------ needs to be refactored is totally confusing that it is not the main controller right? it is the CreateRecortController -----------


/**
 * Program to create a new database entry/record of working day information 
 * 
 * @author Tom Weißflog
 * @version 1.0
 */

public class MainController {

	@FXML
	private DatePicker datepicker;

	@FXML
	private HBox hBoxWork1;

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
	private Button btnWorkHide2;

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
	private Button btnWorkHide3;

	@FXML
	private HBox hBoxBreak1;

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
	private Button btnBreakHide2;

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
	private Button btnBreakHide3;

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
	private Button btnBreakHide4;

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
	private TextField txtComment;

	@FXML
	private Button btnBreakHide5;

	@FXML
	private Button btnReset;

	@FXML
	private Label labelErrortxt;

	@FXML
	private Button btnInputValidation;

	/**
	 * Initializes user input validation while user is typing and displays user
	 * input at window
	 */
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
	
	/**
	 * Handles variable user inputs possibilities and hides selected work or 
	 * break input period line.
	 * Remark: User can provide 3 work periods and 5 break periods. A new period can be 
	 * added to the window content if the previous period is fully filled with 
	 * start and end times. User can delete the last period in the work or break 
	 * section if needed, but never the first one.
	 * 
	 * @param event button click on "+" for showing next period line, button 
	 * click on "-" for hiding last period line and clear textfield content
	 * @see showBox()
	 */
	@FXML
	void hideHBox(ActionEvent event) {
		Button buttonpressed = (Button) event.getSource();
		if (buttonpressed == btnWorkHide2) {
			hBoxWork2.setVisible(false);
			btnWorkAdd1.setVisible(true);
			TextField[] work2 = { txtfieldWorkStart2Hours, txtfieldWorkStart2Minutes, txtfieldWorkEnd2Hours,
					txtfieldWorkEnd2Minutes };
			clear(work2);
		} else if (buttonpressed == btnWorkHide3) {
			hBoxWork3.setVisible(false);
			btnWorkAdd2.setVisible(true);
			TextField[] work3 = { txtfieldWorkStart3Hours, txtfieldWorkStart3Minutes, txtfieldWorkEnd3Hours,
					txtfieldWorkEnd3Minutes };
			clear(work3);
		} else if (buttonpressed == btnBreakHide2) {
			hBoxBreak2.setVisible(false);
			btnBreakAdd1.setVisible(true);
			TextField[] break2 = { txtfieldBreakStart2Hours, txtfieldBreakStart2Minutes, txtfieldBreakEnd2Hours,
					txtfieldBreakEnd2Minutes };
			clear(break2);

		} else if (buttonpressed == btnBreakHide3) {
			hBoxBreak3.setVisible(false);
			btnBreakAdd2.setVisible(true);
			TextField[] break3 = { txtfieldBreakStart3Hours, txtfieldBreakStart3Minutes, txtfieldBreakEnd3Hours,
					txtfieldBreakEnd3Minutes };
			clear(break3);

		} else if (buttonpressed == btnBreakHide4) {
			hBoxBreak4.setVisible(false);
			btnBreakAdd3.setVisible(true);
			TextField[] break4 = { txtfieldBreakStart4Hours, txtfieldBreakStart4Minutes, txtfieldBreakEnd4Hours,
					txtfieldBreakEnd4Minutes };
			clear(break4);

		} else if (buttonpressed == btnBreakHide5) {
			hBoxBreak5.setVisible(false);
			btnBreakAdd4.setVisible(true);
			TextField[] break5 = { txtfieldBreakStart5Hours, txtfieldBreakStart5Minutes, txtfieldBreakEnd5Hours,
					txtfieldBreakEnd5Minutes };
			clear(break5);

		}

	}

	/**
	 * Resets all reactable input segments to default, except date picker value.
	 * Date picker will be left at the day selected and can be adjusted by user.
	 * @param event button click on "Zurücksetzen"
	 */
	@FXML
	void reset(ActionEvent event) {
		TextField[] allTextFields = { txtfieldWorkStart1Hours, txtfieldWorkStart1Minutes, txtfieldWorkEnd1Hours,
				txtfieldWorkEnd1Minutes, txtfieldWorkStart2Hours, txtfieldWorkStart2Minutes, txtfieldWorkEnd2Hours,
				txtfieldWorkEnd2Minutes, txtfieldBreakStart1Hours, txtfieldBreakStart1Minutes, txtfieldBreakEnd1Hours,
				txtfieldBreakEnd1Minutes, txtfieldBreakStart2Hours, txtfieldBreakStart2Minutes, txtfieldBreakEnd2Hours,
				txtfieldBreakEnd2Minutes, txtfieldBreakStart3Hours, txtfieldBreakStart3Minutes, txtfieldBreakEnd3Hours,
				txtfieldBreakEnd3Minutes, txtfieldBreakStart4Hours, txtfieldBreakStart4Minutes, txtfieldBreakEnd4Hours,
				txtfieldBreakEnd4Minutes };

		HBox[] allRetractableInputSegments = { hBoxWork2, hBoxWork3, hBoxBreak2, hBoxBreak3, hBoxBreak4, hBoxBreak5 };

		for (HBox InputSegment : allRetractableInputSegments)
			InputSegment.setVisible(false);

		clear(allTextFields);
		btnWorkAdd1.setVisible(true);
		
		txtComment.setText("");
		labelErrortxt.setVisible(false);


	}

	/**
	 * Clears textfield content
	 * @param textfields are an array of textfields to get user inputs
	 */
	private void clear(TextField[] textfields) {
		for (TextField textfield : textfields)
			textfield.clear();
	}

	/**
	 * Handles variable user inputs possibilities and shows additional work or 
	 * break input period line.
	 * Remark: User can provide 3 work periods and 5 break periods. A new period can be 
	 * added to the window content if the previous period is fully filled with 
	 * start and end times. User can delete the last period in the work or break 
	 * section if needed, but never the first one.
	 * 
	 * @param event button click on "+" for showing next period line, button 
	 * click on "-" for hiding last period line and clear textfield content
	 * @see showBox()
	 */
	@FXML
	void showHBox(ActionEvent event) {

		TextField[] work1 = { txtfieldWorkStart1Hours, txtfieldWorkStart1Minutes, txtfieldWorkEnd1Hours,
				txtfieldWorkEnd1Minutes };

		TextField[] work2 = { txtfieldWorkStart2Hours, txtfieldWorkStart2Minutes, txtfieldWorkEnd2Hours,
				txtfieldWorkEnd2Minutes };

		TextField[] break1 = { txtfieldBreakStart1Hours, txtfieldBreakStart1Minutes, txtfieldBreakEnd1Hours,
				txtfieldBreakEnd1Minutes };

		TextField[] break2 = { txtfieldBreakStart2Hours, txtfieldBreakStart2Minutes, txtfieldBreakEnd2Hours,
				txtfieldBreakEnd2Minutes };

		TextField[] break3 = { txtfieldBreakStart3Hours, txtfieldBreakStart3Minutes, txtfieldBreakEnd3Hours,
				txtfieldBreakEnd3Minutes };

		TextField[] break4 = { txtfieldBreakStart4Hours, txtfieldBreakStart4Minutes, txtfieldBreakEnd4Hours,
				txtfieldBreakEnd4Minutes };

		Button buttonpressed = (Button) event.getSource();
		if (buttonpressed == btnWorkAdd1 && !isBlankTextFields(work1)) {
			hBoxWork2.setVisible(true);
			btnWorkAdd1.setVisible(false);
		} else if (buttonpressed == btnWorkAdd2 && !isBlankTextFields(work2)) {
			hBoxWork3.setVisible(true);
			btnWorkAdd2.setVisible(false);
		} else if (buttonpressed == btnBreakAdd1 && !isBlankTextFields(break1)) {
			hBoxBreak2.setVisible(true);
			btnBreakAdd1.setVisible(false);
		} else if (buttonpressed == btnBreakAdd2 && !isBlankTextFields(break2)) {
			hBoxBreak3.setVisible(true);
			btnBreakAdd2.setVisible(false);
		} else if (buttonpressed == btnBreakAdd3 && !isBlankTextFields(break3)) {
			hBoxBreak4.setVisible(true);
			btnBreakAdd3.setVisible(false);
		} else if (buttonpressed == btnBreakAdd4 && !isBlankTextFields(break4)) {
			hBoxBreak5.setVisible(true);
			btnBreakAdd4.setVisible(false);
		}

	}

	/**
	 * Validates if any textfield is blank 
	 * @param textfields
	 * @return boolean 
	 */
	private boolean isBlankTextFields(TextField[] textfields) {
		return Arrays.stream(textfields).anyMatch(textfield -> textfield.getText().isBlank());
	}

	/**
	 * Handles the user's demand to abort creating a new working day record and
	 * opens the related popup window for user interaction
	 * @param event button click on "Abbrechen"
	 */
	@FXML
	void abort(ActionEvent event) {
		var swapStageController = new SwapSceneController();
		swapStageController.showPopup("/view/PopupAbort.fxml");
	}

	/**
	 * Starts input validation based on given legal and company requirements.
	 * @param event button click on "Eingabe prüfen"
	 * 
	 * @see InputValidationController
	 */
	@FXML
	void validateInput(ActionEvent event) {

		ArrayList<ValidationState> validationResult = new ArrayList<ValidationState>();
		var input = formatInput();
		if (input.isEmpty()) {
			validationResult.add(ValidationState.NOT_VALID_NO_INPUT_FOUND);

		} else {
			var calculationController = new CalculationController(input);
			var workBegin = calculationController.getWorkBegin();
			var workEnd = calculationController.getWorkEnd();
//			var timeAtWork = Duration.between(workBegin, workEnd);
			var totalWorkingTime = calculationController.calculateTotalWorktime();
			var timeAtBreak = calculationController.calculateBreakAndInterruptionDuration();
			var legalBreak = calculationController.calculateLegalBreak();
			var selectedDay = datepicker.getValue();
			// -----------------------------------------
			var datacontroller = new GetOvertime();
			var MA_ID = LoginController.MA_Data.getMA_ID();
			
			var yesterday = selectedDay.minusDays(1).toString();
			String workEndYesterday = datacontroller.getWorkEndYesterday(MA_ID, yesterday);
			LocalTime workEndYesterdayTime =  LocalTime.parse(workEndYesterday);
			
			var tomorrow = selectedDay.plusDays(1).toString();
			String workBeginTomorrow = datacontroller.getWorkBeginTomorrow(MA_ID, tomorrow);
			LocalTime workBeginTomorrowTime =  LocalTime.parse(workBeginTomorrow);
			
			// -------------------------------------------
			var inputValidationController = new InputValidationController(input, legalBreak, 
					totalWorkingTime, timeAtBreak, workBegin, workEnd, selectedDay, workEndYesterdayTime, workBeginTomorrowTime);
			
			validationResult.addAll(inputValidationController.validation());
		}

		var swapStageController = new SwapSceneController();

		if (validationResult.stream().allMatch(elm -> elm.equals(ValidationState.VALID))) {
			computeInput(input);
			swapStageController.showPopup("/view/PopupValid.fxml");
		} else if (validationResult.stream().allMatch(elm ->  elm.equals(ValidationState.VALID) 
				|| elm.equals(ValidationState.VALID_WORKBEGIN_IS_BEFORE_6_00)
				|| elm.equals(ValidationState.VALID_WORKEND_IS_AFTER_19_30))) {
			var Error = validationResult.stream().filter(elm -> !(elm.equals(ValidationState.VALID))).findFirst().get();
			labelErrortxt.setVisible(true);
			labelErrortxt.setText(Error.toString());

			computeInput(input);
			swapStageController.showPopup("/view/PopupLimits.fxml");

		} else {
			var Error = validationResult.stream().filter(elm -> !(elm.equals(ValidationState.VALID))).findFirst().get();
			labelErrortxt.setVisible(true);
			labelErrortxt.setText(Error.toString());
			// -------------------------------
			// switch/case backfire TextDisplayed
			// -------------------------------

		}

	}
/**
 * Class CalculationModel for computing working day data based on unser inputs
 */
	private static CalculationModel calculationModel;

	/**
	 * Gets calculation model
	 * @return calculation model
	 */
	public static CalculationModel getCalculationModel() {
		return calculationModel;
	}

	/**
	 * Computes user inputs to working day data 
	 * @param formattedInput list of working times, break times, work interruptions 
	 * and break interruptions in Timespann
	 */
	private void computeInput(ArrayList<Timespann> formattedInput) {
		var calculationController = new CalculationController(formattedInput);
		calculationModel = new CalculationModel();
		var selectedDay = datepicker.getValue();
		calculationModel.setSelectedDay(selectedDay);
		// totalWorkTime
		var totalWorkTime = calculationController.calculateTotalWorktime();
		calculationModel.setTotalWorkTime(totalWorkTime);

		// overtime
		// needs calculation

		// workbegin
		var begin = calculationController.getWorkBegin();
		calculationModel.setWorkBegin(begin);

		// workend
		var end = calculationController.getWorkEnd();
		calculationModel.setWorkEnd(end);

		// break&interruptions
		var breakUinterruptionDuration = calculationController.calculateBreakAndInterruptionDuration();
		calculationModel.setTotalBreakTime(breakUinterruptionDuration);

		// comment
		var comment = txtComment.getText();
		calculationModel.setComment(comment);

	}

	/**
	 * Collects all textfield inputs and creates a final list of all works, breaks, interruptions and break interruptions
	 * sorted chronologigal by type (work1-3, break1-5, interruption1-2, break interruption1-4)
	 * @return list of single work and break periods for further processing
	 */
	private ArrayList<Timespann> formatInput() {
		// all TextFields
		TextField[] work1 = { txtfieldWorkStart1Hours, txtfieldWorkStart1Minutes, txtfieldWorkEnd1Hours,
				txtfieldWorkEnd1Minutes };

		TextField[] interruption1 = { txtfieldWorkEnd1Hours, txtfieldWorkEnd1Minutes, txtfieldWorkStart2Hours,
				txtfieldWorkStart2Minutes };

		TextField[] work2 = { txtfieldWorkStart2Hours, txtfieldWorkStart2Minutes, txtfieldWorkEnd2Hours,
				txtfieldWorkEnd2Minutes };

		TextField[] interruption2 = { txtfieldWorkEnd2Hours, txtfieldWorkEnd2Minutes, txtfieldWorkStart3Hours,
				txtfieldWorkStart3Minutes };

		TextField[] work3 = { txtfieldWorkStart3Hours, txtfieldWorkStart3Minutes, txtfieldWorkEnd3Hours,
				txtfieldWorkEnd3Minutes };

		TextField[] break1 = { txtfieldBreakStart1Hours, txtfieldBreakStart1Minutes, txtfieldBreakEnd1Hours,
				txtfieldBreakEnd1Minutes };

		TextField[] breakInterruption1 = { txtfieldBreakEnd1Hours, txtfieldBreakEnd1Minutes, txtfieldBreakStart2Hours,
				txtfieldBreakStart2Minutes };

		TextField[] break2 = { txtfieldBreakStart2Hours, txtfieldBreakStart2Minutes, txtfieldBreakEnd2Hours,
				txtfieldBreakEnd2Minutes };

		TextField[] breakInterruption2 = { txtfieldBreakEnd2Hours, txtfieldBreakEnd2Minutes, txtfieldBreakStart3Hours,
				txtfieldBreakStart3Minutes };

		TextField[] break3 = { txtfieldBreakStart3Hours, txtfieldBreakStart3Minutes, txtfieldBreakEnd3Hours,
				txtfieldBreakEnd3Minutes };

		TextField[] breakInterruption3 = { txtfieldBreakEnd3Hours, txtfieldBreakEnd3Minutes, txtfieldBreakStart4Hours,
				txtfieldBreakStart4Minutes };

		TextField[] break4 = { txtfieldBreakStart4Hours, txtfieldBreakStart4Minutes, txtfieldBreakEnd4Hours,
				txtfieldBreakEnd4Minutes };

		TextField[] breakInterruption4 = { txtfieldBreakEnd4Hours, txtfieldBreakEnd4Minutes, txtfieldBreakStart5Hours,
				txtfieldBreakStart5Minutes };

		TextField[] break5 = { txtfieldBreakStart5Hours, txtfieldBreakStart5Minutes, txtfieldBreakEnd5Hours,
				txtfieldBreakEnd5Minutes };

		// Textfield grouped
		TextField[][] input = { work1, work2, work3, break1, break2, break3, break4, break5, interruption1,
				interruption2, breakInterruption1, breakInterruption2, breakInterruption3, breakInterruption4 };

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
				if (value.equals(work1) || value.equals(work2) || value.equals(work3))
					timespann = new Work(start, end);
				else if (value.equals(interruption1) || value.equals(interruption2))
					timespann = new Interruption(start, end);
				else if (value.equals(breakInterruption1) || value.equals(breakInterruption2)
						|| value.equals(breakInterruption3) || value.equals(breakInterruption4))
					timespann = new BreakInterruption(start, end);
				else
					timespann = new Break(start, end);
				formattedInput.add(timespann);
			} catch (NumberFormatException e) {

			}
		} // -> produced list with work(s) and break(s)
		return formattedInput;
	}

}
