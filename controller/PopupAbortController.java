package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class PopupAbortController {

    @FXML
    void abortToMenu(ActionEvent event) {
    	var popup = ((Button)event.getSource()).getScene().getWindow();
    	popup.hide();
    	var swapStageController = new SwapStageController();
    	swapStageController.goTo("/view/Menu.fxml");

    }

    @FXML
    void backToWorkTime(ActionEvent event) {
    	var popup = ((Button)event.getSource()).getScene().getWindow();
    	popup.hide();
    }


}
