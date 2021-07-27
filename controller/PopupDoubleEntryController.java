package controller;

import java.sql.BatchUpdateException;

import database.AddWorkingTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Program to handle double entry of working day record conflicts. Requires user decision how to proceed.
 * 
 * @author Tom Weißflog
 * @version 1.0
 */

public class PopupDoubleEntryController {

	@FXML
	private Label txtDateOld;

	@FXML
	private Label txtWorkBeginOld;

	@FXML
	private Label txtWorkEndOld;

	@FXML
	private Label txtBreakTimeOld;

	@FXML
	private Label txtDateNew;

	@FXML
	private Label txtWorkBeginNew;

	@FXML
	private Label txtWorkEndNew;

	@FXML
	private Label txtBreakTimeNew;

	/**
	 * Initializes popup window content and displays working time, total break and working day date for both, record in
	 * data base and newly created record in application for user check.
     * Requires user decision to save record to database or to go back to time recording window.
	 */
	@FXML
	public void initialize() {

		AddWorkingTime addWorkTime;
		if(PopupValidController.addWorkingTime != null) {
			addWorkTime = PopupValidController.addWorkingTime;
		}else{
			addWorkTime = PopupLimitsController.addWorkingTime;
		}

			
		
		var MA_ID = LoginController.MA_Data.getMA_ID();

		// new
		var calculations = MainController.getCalculationModel();
		var selectedDay = calculations.getSelectedDay();
		txtDateNew.setText(selectedDay);
		var workBeginNew = calculations.getWorkBegin();
		txtWorkBeginNew.setText("Arbeitsbeginn " + workBeginNew);
		var workEndNew = calculations.getWorkEnd();
		txtWorkEndNew.setText("Arbeitsende " + workEndNew);
		var breakTimeNew = calculations.getTotalBreakTime();
		txtBreakTimeNew.setText("Pausenzeit " + breakTimeNew);

		// old
		var controller = addWorkTime;
		var data = controller.getWorkingTime();
		var oldDate = selectedDay;
		txtDateOld.setText(oldDate);
		var workBeginOld = data.get("Arbeitszeit_Beginn");
		txtWorkBeginOld.setText("Arbeitsbeginn " + workBeginOld);
		var workEndOld = data.get("Arbeitszeit_Ende");
		txtWorkEndOld.setText("Arbeitsende " + workEndOld);
		var breakTimeOld = data.get("Pausengesamtzeit_Tag");
		txtBreakTimeOld.setText("Pausenzeit " + breakTimeOld);

	}

	/**
     * Closes popup window and brings user back to time recording window
     * @param event button click "Abbrechen" 
     */
	@FXML
	void abortToWorktime(ActionEvent event) {
		var addWorkTime = PopupValidController.addWorkingTime;
		var popup = ((Button)event.getSource()).getScene().getWindow();
    	addWorkTime.close();
    	popup.hide();
	}

	/**
     * Saves the created working day record for declared user ID to database and 
     * closes popup window afterwards.
     * @param event button click on "Speichern"
     */
	@FXML
	void saveToMenu(ActionEvent event) {
		
		AddWorkingTime addWorkTime;
		if(PopupValidController.addWorkingTime != null) {
			addWorkTime = PopupValidController.addWorkingTime;
		}else{
			addWorkTime = PopupLimitsController.addWorkingTime;
		}
		var data = MainController.getCalculationModel();
		
		var beginTime = data.getWorkBegin();
		var endTime = data.getWorkEnd();
		var totalBreak = data.getTotalBreakTime();
		var overtime = "00:00:00";
		var comment = data.getComment();

		addWorkTime.modifyWorkingTime(beginTime, endTime, totalBreak, overtime, comment);
		var popup = ((Button) event.getSource()).getScene().getWindow();
		addWorkTime.close();
		popup.hide();


	}

}
