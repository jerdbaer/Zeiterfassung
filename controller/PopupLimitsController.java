package controller;

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

    
    @FXML
    public void initialize() {
    	var calculations = MainController.getCalculationModel();
    }	

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
    	}

    }

}
