package controller;

import models.ValidationState;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.HBox;

public class MainController {

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

	}

	@FXML
	void showHBox(ActionEvent event) {
		var work1 = new MainViewInputField(txtfieldWorkStart1Hours.getText(), txtfieldWorkStart1Minutes.getText(),
				txtfieldWorkEnd1Hours.getText(), txtfieldWorkEnd1Minutes.getText());
		var work2 = new MainViewInputField(txtfieldWorkStart2Hours.getText(), txtfieldWorkStart2Minutes.getText(),
				txtfieldWorkEnd2Hours.getText(), txtfieldWorkEnd2Minutes.getText());

		Button buttonpressed = (Button) event.getSource();
		if (buttonpressed == btnWorkAdd1) {

			if (work1.exists()) {
				hBoxWork2.setVisible(true);
				btnWorkAdd1.setVisible(false);
			}

		} else if (buttonpressed == btnWorkAdd2) {
			
			if (work2.exists()) {
				hBoxWork3.setVisible(true);
				btnWorkAdd2.setVisible(false);
			}
			
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
		var work1 = new MainViewInputField(txtfieldWorkStart1Hours.getText(), txtfieldWorkStart1Minutes.getText(),
				txtfieldWorkEnd1Hours.getText(), txtfieldWorkEnd1Minutes.getText());
		var work2 = new MainViewInputField(txtfieldWorkStart2Hours.getText(), txtfieldWorkStart2Minutes.getText(),
				txtfieldWorkEnd2Hours.getText(), txtfieldWorkEnd2Minutes.getText());
		var work3 = new MainViewInputField(txtfieldWorkStart3Hours.getText(), txtfieldWorkStart3Minutes.getText(),
				txtfieldWorkEnd3Hours.getText(), txtfieldWorkEnd3Minutes.getText());
		MainViewInputField[] workInput = { work1, work2, work3 };

		var validation = new ArrayList<ValidationState>();
		for (int i = 0; i < workInput.length; i++) {
			var input = workInput[i];

			if (input.equals(work1)) {
				if (!(work1.exists()))
					validation.add(ValidationState.NOT_VALID);
			} else {
				if (input.exists()) {
					input.setPredecessorEnd(workInput[i - 1]);
					input.setSelfStart();
					validation.add(input.isAfterPredecessor());
				}
			}
			validation.add(input.valid());
		}

		if (validation.stream().allMatch(elm -> elm.equals(ValidationState.VALID)))
			btnDone.setDisable(false);
		else
			btnDone.setDisable(true);

//		var workStart1asText = inputFormatToHHMM(txtfieldWorkStart1Hours.getText(), txtfieldWorkStart1Minutes.getText());
//		var workStart1asTime = LocalTime.parse(workStart1asText);
//		
//		var workEnd1asText = inputFormatToHHMM(txtfieldWorkEnd1Hours.getText(), txtfieldWorkEnd1Minutes.getText());
//		var workEnd1asTime = LocalTime.parse(workEnd1asText);
//		
//		var worksegment1 = Duration.between(workStart1asTime, workEnd1asTime);

	}

}
