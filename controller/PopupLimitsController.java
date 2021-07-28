package controller;

import java.sql.BatchUpdateException;

import database.AddWorkingTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Program to open a new popup window for user interaction.
 * Requires an explanation (user input) if work begin and/or work end exceed 
 * organisational working time frame.
 * 
 * @author Tom Weißflog
 * @version 1.0
 *
 */
public class PopupLimitsController {
	
	@FXML
    private TextField txtFieldReason;

    @FXML
    private Label txtError;
    
    public static AddWorkingTime addWorkingTime;

    
    @FXML
    public void initialize() {
    	var calculations = MainController.getCalculationModel();
    }	
    
    /**
     * Closes popup window and brings user back to time recording window
     * @param event button click "Abbrechen" 
     */
    @FXML
    void abortToWorktime(ActionEvent event) {
    	var popup = ((Button)event.getSource()).getScene().getWindow();
    	popup.hide();
    }
    
    /**
     * Saves the created working day record for declared user ID to database 
     * and closes popup window afterwards.
     * @param event button click on ""
     */
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
        	var overtime = "00:00:00";
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

}
