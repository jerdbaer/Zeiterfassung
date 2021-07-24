package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {

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
    private Button btnWorktime;

    @FXML
    private Button btnAbsence;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnHelp;
    
    @FXML
    void switchScene(ActionEvent event) {
    	var swapStageController = new SwapStageController();
    	var pressedBtn = (Button)event.getSource();
    	if(pressedBtn == btnWorktimeTop || pressedBtn == btnWorktime) {
    		swapStageController.goTo("/view/WorkTime.fxml");
    	}
    	else if(pressedBtn == btnAbsenceTop || pressedBtn == btnAbsence) {
 //   		swapStageController.goToAbsence(event);
    	}
    	else if(pressedBtn == btnOverviewTop || pressedBtn == btnOverview) {
    		swapStageController.goTo("/view/Overview.fxml");
    	}
    	else if(pressedBtn == btnHelpTop || pressedBtn == btnHelp) {
    		swapStageController.goTo("/view/Help.fxml");
    	}
    	else if(pressedBtn == btnLogout) {
    		swapStageController.goTo("/view/Login.fxml");
    	}

    }



    	
}
