package controller;

import java.sql.BatchUpdateException;

import database.AddWorkingTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Program to open a new popup window for user interaction.
 * Displays valid working day record information to get user decision how to proceed.
 * 
 * @author Tom Weißflog
 * @version 1.0
 *
 */
public class PopupValidController {

    @FXML
    private Label txtDatum;

    @FXML
    private Label txtTimes;
    
    public static AddWorkingTime addWorkingTime;
    
    /**
     * Initializes popup window content and displays working time, total break 
     * and working day date for user check.
     * Requires user decision to save record to database or to go back 
     * to time recording window.
     */
    @FXML
    public void initialize() {
    	var calculations = MainController.getCalculationModel();
    	var selectedDay = calculations.getSelectedDay();
    	txtDatum.setText(selectedDay);
    	var workingTime = calculations.getTotalWorkTime();
    	var totalBreakTime = calculations.getTotalBreakTime();
    	txtTimes.setText("Arbeitszeit " + workingTime + " mit " + totalBreakTime + " Pausenzeit" );
    	
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
     * Saves the created working day record for declared user ID to database and 
     * closes popup window afterwards.
     * @param event button click on "Speichern"
     */
    @FXML
    void saveToMenu(ActionEvent event) {
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
