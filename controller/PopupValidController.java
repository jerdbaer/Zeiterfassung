package controller;

import java.sql.BatchUpdateException;

import database.AddWorkingTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PopupValidController {

    @FXML
    private Label txtDatum;

    @FXML
    private Label txtTimes;
    
    @FXML
    public void initialize() {
    	var calculations = MainController.getCalculationModel();
    	var selectedDay = calculations.getSelectedDay();
    	txtDatum.setText(selectedDay);
    	var workingTime = calculations.getTotalWorkTime();
    	var totalBreakTime = calculations.getTotalBreakTime();
    	txtTimes.setText("Arbeitszeit " + workingTime + " mit " + totalBreakTime + " Pausenzeit" );
    	
    }

    @FXML
    void abortToWorktime(ActionEvent event) {
    	var popup = ((Button)event.getSource()).getScene().getWindow();
    	popup.hide();

    }

    @FXML
    void saveToMenu(ActionEvent event) {
    	var calculations = MainController.getCalculationModel();
    	var MA_ID = LoginController.MA_Data.getMA_ID();
    	var workDate = calculations.getSelectedDay();
    	var addWorkingTime = new AddWorkingTime(workDate, MA_ID);
    	
    	var data = MainController.getCalculationModel();
    	var beginTime = data.getWorkBegin();
    	var endTime = data.getWorkEnd();
    	var totalBreak = data.getTotalBreakTime();
    	var overtime = "00:00:00";
    	var comment = data.getComment();
    	try {
    		
    		addWorkingTime.addWorkingTime(beginTime, endTime, totalBreak, overtime, comment);
    		addWorkingTime.close();
    		var popup = ((Button)event.getSource()).getScene().getWindow();
        	popup.hide();
    		
    	}catch(BatchUpdateException e) {
    		var swapSceneController = new SwapStageController();
    		var popup = ((Button)event.getSource()).getScene().getWindow();
        	popup.hide();
//        	swapSceneController.showPopup("/view/DoubleEntry.fxml");
    	}
    	finally {
			addWorkingTime.close();
		}
    	
    	
    }

}
