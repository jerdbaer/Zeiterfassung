package controller;

import java.sql.BatchUpdateException;

import database.AddWorkingTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

	@FXML
	public void initialize() {

		var addWorkTime = PopupValidController.addWorkingTime;
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

	@FXML
	void abortToWorktime(ActionEvent event) {
		var addWorkTime = PopupValidController.addWorkingTime;
		var popup = ((Button)event.getSource()).getScene().getWindow();
    	addWorkTime.close();
    	popup.hide();
	}

	@FXML
	void saveToMenu(ActionEvent event) {
		
		var addWorkTime = PopupValidController.addWorkingTime;
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
