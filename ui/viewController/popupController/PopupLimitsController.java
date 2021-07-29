package ui.viewController.popupController;

import java.sql.BatchUpdateException;
import database.AddWorkingTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Main;
import ui.controller.SwapSceneController;
import ui.interfaces.ISwapSceneController;

/**
 * Program to open a new popup window for user interaction.
 * Requires an explanation (user input) if work begin and/or work end exceed 
 * organisational working time frame.
 * 
 * @author Tom Wei√üflog
 * @version 1.0
 *
 */
public class PopupLimitsController {
	
	@FXML
    private TextField txtFieldReason;

    @FXML
    private Label txtError;
    
    public static AddWorkingTime addWorkingTime;	
    
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
     * @param event button click on "Best‰tigen & Speichern"
     */
    @FXML
    void saveToMenu(ActionEvent event) {
    	if(txtFieldReason.getText().isBlank()) {
    		txtError.setVisible(true);
    	}
    	else {
    		txtError.setVisible(false);
    		
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

}
