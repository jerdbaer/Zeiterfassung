package controller;

import java.sql.BatchUpdateException;
import java.time.Duration;
import java.time.LocalTime;

import database.AddWorkingTime;
import database.GetOvertime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class PopupLimitsController {
	
	@FXML
    private TextField txtFieldReason;

    @FXML
    private Label txtError;
    
    public static AddWorkingTime addWorkingTime;

    @FXML
    void abortToWorktime(ActionEvent event) {
    	var popup = ((Button)event.getSource()).getScene().getWindow();
    	popup.hide();
    }

    @FXML
    void saveToMenu(ActionEvent event) {
    	if(txtFieldReason.getText().isBlank()) {
    		txtError.setVisible(true);
    	}
    	else {
    		txtError.setVisible(false);
    		
    		var calculations = MainController.getCalculationModel();
        	var MA_ID = LoginController.MA_Data.getMA_ID();
        	var workDate = calculations.getSelectedDay();
        	addWorkingTime = new AddWorkingTime(workDate, MA_ID);
        	var data = MainController.getCalculationModel();
        	var beginTime = data.getWorkBegin();
        	var endTime = data.getWorkEnd();
        	var totalBreak = data.getTotalBreakTime();
        	setOvertime();
        	var overtime = calculations.getOvertime();
        	var comment = data.getComment();
        	try {
        		
        		addWorkingTime.addWorkingTime(beginTime, endTime, totalBreak, overtime, comment);
        		addWorkingTime.close();
        		var popup = ((Button)event.getSource()).getScene().getWindow();
            	popup.hide();
        		
        	}catch(BatchUpdateException e) {
        		var swapSceneController = new SwapSceneController();
        		var popup = ((Button)event.getSource()).getScene().getWindow();
        		swapSceneController.showPopup("/view/PopupDoubleEntry.fxml");
            	popup.hide();
            	
        	}
    	}

    }
    
    private void setOvertime() {
    	var calculations = MainController.getCalculationModel();
    	var totalWorkingTimeString = calculations.getTotalWorkTime();
    	var totalWorkingTime = LocalTime.parse(totalWorkingTimeString);
    	var MA_ID = LoginController.MA_Data.getMA_ID();
    	var dataController = new GetOvertime();
    	var plannedWorkingtimeString = dataController.getPlannedWorkingTime(MA_ID);
    	var plannedWorkingTime = LocalTime.parse(plannedWorkingtimeString);
    	var overtime = Duration.between(plannedWorkingTime, totalWorkingTime);
    	calculations.setOvertime(overtime);
    }

}
