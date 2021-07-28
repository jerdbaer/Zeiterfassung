package ui.viewController.popupController;

import java.sql.BatchUpdateException;
import java.time.Duration;
import java.time.LocalTime;

import database.AddWorkingTime;
import database.GetOvertime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.Main;
import ui.controller.SwapSceneController;
import ui.interfaces.ISwapSceneController;
import ui.viewController.LoginController;
import ui.viewController.WorkTimeController;

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
    	var selectedDay = Main.dataEntryModel.getSelectedDay();
    	var workingTime = Main.dataEntryModel.getTotalWorkTime();
    	var totalBreakTime = Main.dataEntryModel.getTotalBreakTime();
    	
    	txtDatum.setText(selectedDay);
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
    	
    	var MA_ID = Main.dataEntryModel.getMA_ID();
    	var workDate = Main.dataEntryModel.getSelectedDay();
    	addWorkingTime = new AddWorkingTime(workDate, MA_ID);
    	var beginTime = Main.dataEntryModel.getWorkBegin();
    	var endTime = Main.dataEntryModel.getWorkEnd();
    	var totalBreak = Main.dataEntryModel.getTotalBreakTime();
    	var overtime = Main.dataEntryModel.getOvertime();
    	var comment = Main.dataEntryModel.getComment();
    	try {
    		
    		addWorkingTime.addWorkingTime(beginTime, endTime, totalBreak, overtime, comment);
    		addWorkingTime.close();
    		var popup = ((Button)event.getSource()).getScene().getWindow();
        	popup.hide();
    		
    	}catch(BatchUpdateException e) {
    		ISwapSceneController swapSceneController = new SwapSceneController();
    		var popup = ((Button)event.getSource()).getScene().getWindow();
    		swapSceneController.showPopup("/ui/view/PopupDoubleEntry.fxml");
        	popup.hide();
        	
    	}	
    }
}
