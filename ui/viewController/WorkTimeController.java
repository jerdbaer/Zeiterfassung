package ui.viewController;


import models.ValidationState;
import models.time.Break;
import models.time.BreakInterruption;
import models.time.Interruption;
import models.time.Timespann;
import models.time.Work;
import ui.controller.SwapSceneController;
import ui.controller.ViewInputRestrictionController;
import ui.interfaces.ISwapSceneController;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import controller.CalculationController;
import controller.InputValidationController;
import database.GetOvertime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import main.Main;

//------------------ needs to be refactored is totally confusing that it is not the main controller right? it is the CreateRecortController -----------


/**
 * Program to create a new database entry/record of working day information 
 * 
 * @author Tom Weißflog
 * @version 1.0
 */

public class WorkTimeController {

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
	 * Initializes user input restriction while user is typing and displays user
	 * input at window
	 */
	@FXML
	public void initialize() {

		var mainViewInputRestrictionController = new ViewInputRestrictionController();

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
			btnWorkHide2.setVisible(true);
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
			btnBreakHide2.setVisible(true);
			btnBreakAdd2.setVisible(true);
			TextField[] break3 = { txtfieldBreakStart3Hours, txtfieldBreakStart3Minutes, txtfieldBreakEnd3Hours,
					txtfieldBreakEnd3Minutes };
			clear(break3);

		} else if (buttonpressed == btnBreakHide4) {
			hBoxBreak4.setVisible(false);
			btnBreakHide3.setVisible(true);
			btnBreakAdd3.setVisible(true);
			TextField[] break4 = { txtfieldBreakStart4Hours, txtfieldBreakStart4Minutes, txtfieldBreakEnd4Hours,
					txtfieldBreakEnd4Minutes };
			clear(break4);

		} else if (buttonpressed == btnBreakHide5) {
			hBoxBreak5.setVisible(false);
			btnBreakHide4.setVisible(true);
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
		TextField[] allTextFields = { txtfieldWorkStart1Hours, txtfieldWorkStart1Minutes, txtfieldWorkEnd1Hours, txtfieldWorkEnd1Minutes,
				txtfieldWorkStart2Hours, txtfieldWorkStart2Minutes, txtfieldWorkEnd2Hours,	txtfieldWorkEnd2Minutes,
				txtfieldWorkStart3Hours, txtfieldWorkStart3Minutes, txtfieldWorkEnd3Hours,	txtfieldWorkEnd3Minutes, 
				txtfieldBreakStart1Hours, txtfieldBreakStart1Minutes, txtfieldBreakEnd1Hours, txtfieldBreakEnd1Minutes, 
				txtfieldBreakStart2Hours, txtfieldBreakStart2Minutes, txtfieldBreakEnd2Hours, txtfieldBreakEnd2Minutes, 
				txtfieldBreakStart3Hours, txtfieldBreakStart3Minutes, txtfieldBreakEnd3Hours, txtfieldBreakEnd3Minutes, 
				txtfieldBreakStart4Hours, txtfieldBreakStart4Minutes, txtfieldBreakEnd4Hours, txtfieldBreakEnd4Minutes,
				txtfieldBreakStart5Hours, txtfieldBreakStart5Minutes, txtfieldBreakEnd5Hours, txtfieldBreakEnd5Minutes};

		HBox[] allRetractableInputSegments = { hBoxWork2, hBoxWork3, hBoxBreak2, hBoxBreak3, hBoxBreak4, hBoxBreak5 };

		for (HBox InputSegment : allRetractableInputSegments)
			InputSegment.setVisible(false);

		clear(allTextFields);
		btnWorkAdd1.setVisible(true);
		btnBreakAdd1.setVisible(true);
		
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
			show(hBoxWork2,btnWorkAdd2,btnWorkHide2);
			hide(btnWorkAdd1);
		} else if (buttonpressed == btnWorkAdd2 && !isBlankTextFields(work2)) {
			show(hBoxWork3,btnWorkHide3);
			hide(btnWorkAdd2,btnWorkHide2);
		} else if (buttonpressed == btnBreakAdd1 && !isBlankTextFields(break1)) {
			show(hBoxBreak2,btnBreakAdd2,btnBreakHide2);
			hide(btnBreakAdd1);
		} else if (buttonpressed == btnBreakAdd2 && !isBlankTextFields(break2)) {
			show(hBoxBreak3,btnBreakAdd3,btnBreakHide3);
			hide(btnBreakAdd2,btnBreakHide2);
		} else if (buttonpressed == btnBreakAdd3 && !isBlankTextFields(break3)) {
			show(hBoxBreak4,btnBreakAdd4,btnBreakHide4);
			hide(btnBreakAdd3,btnBreakHide3);
		} else if (buttonpressed == btnBreakAdd4 && !isBlankTextFields(break4)) {
			show(hBoxBreak5,btnBreakHide5);
			hide(btnBreakAdd4,btnBreakHide4);
		}

	}
		
	private void hide(Button btn) {
		btn.setVisible(false);
	}
	
	private void hide(Button btnAdd,Button tbnRemove) {
		btnAdd.setVisible(false);
		tbnRemove.setVisible(false);

	}
	
	private void show(HBox hBox, Button btnAdd, Button btnRemove) {
		btnAdd.setVisible(true);
		btnRemove.setVisible(true);
		hBox.setVisible(true);
	}
	
	private void show(HBox hBox, Button btnRemove) {
		btnRemove.setVisible(true);
		hBox.setVisible(true);
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
		ISwapSceneController swapStageController = new SwapSceneController();
		swapStageController.showPopup("/ui/view/PopupAbort.fxml");
	}

	/**
	 * Starts input validation based on given legal and company requirements.
	 * @param event button click on "Eingabe pr�fen"
	 * 
	 * @see InputValidationController
	 */
	@FXML
	void validateInput(ActionEvent event) {
		
		ISwapSceneController swapStageController = new SwapSceneController();
		var userInput = formatInput();
		var validationResult = validateInput(userInput);
		validateOutputUIResponse(swapStageController, userInput, validationResult);
	}

	/**
	 * response of the UI based on the validated input
	 * 
	 * @param ISwapSceneController, formatInput(), validateInput()
	 */
	private void validateOutputUIResponse(ISwapSceneController swapSceneController, ArrayList<Timespann> userInput,
			ArrayList<ValidationState> validationResult) {
		
		//response validinput
		if (validationResult.stream().allMatch(elm -> elm.equals(ValidationState.VALID))) {
			labelErrortxt.setVisible(false);
			computeInput(userInput);
			swapSceneController.showPopup("/ui/view/PopupValid.fxml");
		}
		//response valid input but out off work time limits
		else if (validationResult.stream().allMatch(elm ->  elm.equals(ValidationState.VALID) 
				|| elm.equals(ValidationState.VALID_WORKBEGIN_IS_BEFORE_6_00)
				|| elm.equals(ValidationState.VALID_WORKEND_IS_AFTER_19_30))) {
			
			var Error = validationResult.stream()
					.filter(elm -> elm.equals(ValidationState.VALID_WORKBEGIN_IS_BEFORE_6_00) || elm.equals(ValidationState.VALID_WORKEND_IS_AFTER_19_30))
					.findFirst().get();
			labelErrortxt.setVisible(true);
			labelErrortxt.setText(Error.toString());

			computeInput(userInput);
			swapSceneController.showPopup("/ui/view/PopupLimits.fxml");

		}
		//response not valid input
		else {
			var Error = validationResult.stream()
					.filter(elm -> (!(elm.equals(ValidationState.VALID)) 
							&& !(elm.equals(ValidationState.VALID_WORKBEGIN_IS_BEFORE_6_00)) && !(elm.equals(ValidationState.VALID_WORKEND_IS_AFTER_19_30)) ))
					.findFirst().get();
			labelErrortxt.setVisible(true);
			labelErrortxt.setText(Error.toString());

		}
	}
	
	/**
	 * calculates all working day record parameters
	 * 
	 * @param work begin, work end, total working time, time at break, legal break, 
	 * selected day, yesterday, work end yesterday, tomorrow, work begin tomorrow,
	 * MA_ID, 
	 * 
	 * @see InputValidationController
	 * @return results of the validations as ArrayList
	 */
	private ArrayList<ValidationState> validateInput(ArrayList<Timespann> formattedInput) {
		ArrayList<ValidationState> validationResult = new ArrayList<ValidationState>();
		
		if (formattedInput.isEmpty()) {
			validationResult.add(ValidationState.NOT_VALID_NO_INPUT_FOUND);
			return validationResult;

		} else {
			var calculationController = new CalculationController(formattedInput);
			var workBegin = calculationController.getWorkBegin();
			var workEnd = calculationController.getWorkEnd();
			var totalWorkingTime = calculationController.calculateTotalWorktime();
			var timeAtBreak = calculationController.calculateBreakAndInterruptionDuration();
			var legalBreak = calculationController.calculateLegalBreak();
			var selectedDay = datepicker.getValue();
			var dataController = new GetOvertime();
			var MA_ID = Main.dataEntryModel.getMA_ID();
			LocalTime workEndYesterdayTime;
			LocalTime workBeginTomorrowTime ;
			try {
				var yesterday = selectedDay.minusDays(1).toString();
				String workEndYesterday = dataController.getWorkEndYesterday(MA_ID, yesterday);
				workEndYesterdayTime =  LocalTime.parse(workEndYesterday);
				
				var tomorrow = selectedDay.plusDays(1).toString();
				String workBeginTomorrow = dataController.getWorkBeginTomorrow(MA_ID, tomorrow);
				workBeginTomorrowTime =  LocalTime.parse(workBeginTomorrow);
			}catch(NullPointerException noDateSelected) {
				validationResult.add(ValidationState.NOT_VALID_NO_DATE_SELECTED);
				return validationResult;
			}
			
			
			
			var inputValidationController = new InputValidationController(formattedInput, legalBreak, 
					totalWorkingTime, timeAtBreak, workBegin, workEnd, 
					selectedDay, workEndYesterdayTime, workBeginTomorrowTime);
			
			validationResult.addAll(inputValidationController.validation());
			
			return validationResult;
		}
	}


	/**
	 * Computes user inputs to working day data 
	 * @param formattedInput list of working times, break times, work interruptions 
	 * and break interruptions in Timespann
	 */
	private void computeInput(ArrayList<Timespann> formattedInput) {
		var calculationController = new CalculationController(formattedInput);
		
		var selectedDay = datepicker.getValue();
		Main.dataEntryModel.setSelectedDay(selectedDay);
		// totalWorkTime
		var totalWorkTime = calculationController.calculateTotalWorktime();
		Main.dataEntryModel.setTotalWorkTime(totalWorkTime);

		// overtime
		var overtime = calculationController.calculateOvertime();
		Main.dataEntryModel.setOvertime(overtime);

		// workbegin
		var begin = calculationController.getWorkBegin();
		Main.dataEntryModel.setWorkBegin(begin);

		// workend
		var end = calculationController.getWorkEnd();
		Main.dataEntryModel.setWorkEnd(end);

		// break&interruptions
		var breakUinterruptionDuration = calculationController.calculateBreakAndInterruptionDuration();
		Main.dataEntryModel.setTotalBreakTime(breakUinterruptionDuration);

		// comment
		var comment = txtComment.getText();
		Main.dataEntryModel.setComment(comment);

	}

	/**
	 * Collects all textfield inputs and creates a final list of all works, breaks, interruptions and break interruptions
	 * sorted chronologigal by type (work1-3, break1-5, interruption1-2, break interruption1-4)
	 * @return list of single timespann periods for further processing
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
				if(value.equals(work1)) {
					TextField[] allTextFields = { txtfieldWorkStart1Hours, txtfieldWorkStart1Minutes, txtfieldWorkEnd1Hours, txtfieldWorkEnd1Minutes,
							txtfieldWorkStart2Hours, txtfieldWorkStart2Minutes, txtfieldWorkEnd2Hours,	txtfieldWorkEnd2Minutes,
							txtfieldWorkStart3Hours, txtfieldWorkStart3Minutes, txtfieldWorkEnd3Hours,	txtfieldWorkEnd3Minutes, 
							txtfieldBreakStart1Hours, txtfieldBreakStart1Minutes, txtfieldBreakEnd1Hours, txtfieldBreakEnd1Minutes, 
							txtfieldBreakStart2Hours, txtfieldBreakStart2Minutes, txtfieldBreakEnd2Hours, txtfieldBreakEnd2Minutes, 
							txtfieldBreakStart3Hours, txtfieldBreakStart3Minutes, txtfieldBreakEnd3Hours, txtfieldBreakEnd3Minutes, 
							txtfieldBreakStart4Hours, txtfieldBreakStart4Minutes, txtfieldBreakEnd4Hours, txtfieldBreakEnd4Minutes,
							txtfieldBreakStart5Hours, txtfieldBreakStart5Minutes, txtfieldBreakEnd5Hours, txtfieldBreakEnd5Minutes};

					HBox[] allRetractableInputSegments = { hBoxWork2, hBoxWork3, hBoxBreak2, hBoxBreak3, hBoxBreak4, hBoxBreak5 };

					for (HBox InputSegment : allRetractableInputSegments)
						InputSegment.setVisible(false);

					clear(allTextFields);
					btnWorkAdd1.setVisible(true);
					btnBreakAdd1.setVisible(true);
				}
				else if(value.equals(work2)) {
					TextField[] allTextFields = { 
							txtfieldWorkStart2Hours, txtfieldWorkStart2Minutes, txtfieldWorkEnd2Hours,	txtfieldWorkEnd2Minutes,
							txtfieldWorkStart3Hours, txtfieldWorkStart3Minutes, txtfieldWorkEnd3Hours,	txtfieldWorkEnd3Minutes};

					HBox[] retractableInputSegments = { hBoxWork2, hBoxWork3};

					for (HBox InputSegment : retractableInputSegments)
						InputSegment.setVisible(false);

					clear(allTextFields);
					btnWorkAdd1.setVisible(true);
				}
				else if(value.equals(work3)) {
					TextField[] work3TextFields = { 
							txtfieldWorkStart3Hours, txtfieldWorkStart3Minutes, txtfieldWorkEnd3Hours,	txtfieldWorkEnd3Minutes};

					hBoxWork3.setVisible(false);
					clear(work3TextFields);
					btnWorkAdd2.setVisible(true);
					
				}
				else if(value.equals(break1)) {
					TextField[] allTextFields = { 
							txtfieldBreakStart1Hours, txtfieldBreakStart1Minutes, txtfieldBreakEnd1Hours, txtfieldBreakEnd1Minutes, 
							txtfieldBreakStart2Hours, txtfieldBreakStart2Minutes, txtfieldBreakEnd2Hours, txtfieldBreakEnd2Minutes, 
							txtfieldBreakStart3Hours, txtfieldBreakStart3Minutes, txtfieldBreakEnd3Hours, txtfieldBreakEnd3Minutes, 
							txtfieldBreakStart4Hours, txtfieldBreakStart4Minutes, txtfieldBreakEnd4Hours, txtfieldBreakEnd4Minutes,
							txtfieldBreakStart5Hours, txtfieldBreakStart5Minutes, txtfieldBreakEnd5Hours, txtfieldBreakEnd5Minutes};


					HBox[] retractableInputSegments = {hBoxBreak2, hBoxBreak3, hBoxBreak4, hBoxBreak5 };

					for (HBox InputSegment : retractableInputSegments)
						InputSegment.setVisible(false);

					clear(allTextFields);
					btnBreakAdd1.setVisible(true);
					
				}else if(value.equals(break2)) {
					TextField[] allTextFields = { 
							txtfieldBreakStart2Hours, txtfieldBreakStart2Minutes, txtfieldBreakEnd2Hours, txtfieldBreakEnd2Minutes, 
							txtfieldBreakStart3Hours, txtfieldBreakStart3Minutes, txtfieldBreakEnd3Hours, txtfieldBreakEnd3Minutes, 
							txtfieldBreakStart4Hours, txtfieldBreakStart4Minutes, txtfieldBreakEnd4Hours, txtfieldBreakEnd4Minutes,
							txtfieldBreakStart5Hours, txtfieldBreakStart5Minutes, txtfieldBreakEnd5Hours, txtfieldBreakEnd5Minutes};

					HBox[] retractableInputSegments = {hBoxBreak2, hBoxBreak3, hBoxBreak4, hBoxBreak5 };

					for (HBox InputSegment : retractableInputSegments)
						InputSegment.setVisible(false);

					clear(allTextFields);
					btnBreakAdd1.setVisible(true);
					
				}else if(value.equals(break3)) {
					TextField[] allTextFields = { 
							txtfieldBreakStart3Hours, txtfieldBreakStart3Minutes, txtfieldBreakEnd3Hours, txtfieldBreakEnd3Minutes, 
							txtfieldBreakStart4Hours, txtfieldBreakStart4Minutes, txtfieldBreakEnd4Hours, txtfieldBreakEnd4Minutes,
							txtfieldBreakStart5Hours, txtfieldBreakStart5Minutes, txtfieldBreakEnd5Hours, txtfieldBreakEnd5Minutes};

					HBox[] retractableInputSegments = {hBoxBreak3, hBoxBreak4, hBoxBreak5 };

					for (HBox InputSegment : retractableInputSegments)
						InputSegment.setVisible(false);

					clear(allTextFields);
					btnBreakAdd2.setVisible(true);
					
				}
				
				else if(value.equals(break4)) {
					TextField[] allTextFields = { 
							txtfieldBreakStart4Hours, txtfieldBreakStart4Minutes, txtfieldBreakEnd4Hours, txtfieldBreakEnd4Minutes,
							txtfieldBreakStart5Hours, txtfieldBreakStart5Minutes, txtfieldBreakEnd5Hours, txtfieldBreakEnd5Minutes};

					HBox[] retractableInputSegments = { hBoxBreak4, hBoxBreak5};

					for (HBox InputSegment : retractableInputSegments)
						InputSegment.setVisible(false);

					clear(allTextFields);
					btnBreakAdd3.setVisible(true);
					
				}
				else if(value.equals(break5)) {
					TextField[] break5TextFields = { 
							txtfieldBreakStart5Hours, txtfieldBreakStart5Minutes, txtfieldBreakEnd5Hours, txtfieldBreakEnd5Minutes};


					hBoxBreak5.setVisible(false);
					clear(break5TextFields);
					btnBreakAdd4.setVisible(true);
				}
			}
		} // -> produced list with work(s) and break(s)
		return formattedInput;
	}

	

}
