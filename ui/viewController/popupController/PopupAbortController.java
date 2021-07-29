package ui.viewController.popupController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ui.controller.SwapSceneController;
import ui.interfaces.ISwapSceneController;

/**
 * Program to 
 * 
 * @author Tom Weißflog
 * @version 1.0
 */
public class PopupAbortController {
	
	/**
	 * Closes popup window and brings user back to main menu so that creation of a new working day record is canceled.
	 * @param event button click on "Ja, abbrechen"
	 */
    @FXML
    void abortToMenu(ActionEvent event) {
    	var popup = ((Button)event.getSource()).getScene().getWindow();
    	popup.hide();
    	ISwapSceneController swapStageController = new SwapSceneController();
    	swapStageController.goTo("/ui/view/Menu.fxml");

    }

    /**
     * Closes popup window and brings user back to time recording window
     * @param event button click on "Zurück zur Eingabe"
     */
    @FXML
    void backToWorkTime(ActionEvent event) {
    	var popup = ((Button)event.getSource()).getScene().getWindow();
    	popup.hide();
    }


}
