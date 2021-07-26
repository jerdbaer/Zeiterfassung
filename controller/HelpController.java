package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HelpController {

    @FXML
    private Button btnWorktimeTop;

    @FXML
    private Button btnAbsenceTop;

    @FXML
    private Button btnOverviewTop;

    @FXML
    private Button btnHelpTop;

    @FXML
    private Button btnLogout;
    
    @FXML
    void switchScene(ActionEvent event) {
    	var swapStageController = new SwapStageController();
    	var pressedBtn = (Button)event.getSource();
    	if(pressedBtn == btnWorktimeTop) {
    		swapStageController.goTo("/view/WorkTime.fxml");
    	}
    	else if(pressedBtn == btnAbsenceTop) {
 //   		swapStageController.goToAbsence(event);
    	}
    	else if(pressedBtn == btnOverviewTop) {
    		swapStageController.goTo("/view/Overview.fxml");
    	}
    	else if(pressedBtn == btnHelpTop) {
    		swapStageController.goTo("/view/Menu.fxml");
    	}
    	else if(pressedBtn == btnLogout) {
    		swapStageController.goTo("/view/Login.fxml");
    	}

    }
}  
