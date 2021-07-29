package ui.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ui.controller.SwapSceneController;
import ui.interfaces.ISwapSceneController;

/**
 * Program to provide user different options how to proceed in application.
 * User can choose from recording working time, managing absence, getting an overview, getting help or logout.
 * 
 * @author Tom Weißflog
 * @version 1.0
 *
 */
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
    
    /**
     * Handles user choice how to proceed in application 
     * and loads the window of the related fxml file.
     * @param event button click on "Arbeitszeit erfassen", "Abwesenheit verwalten" "Übersicht", "Hilfe" or "Logout" at 
     * top menu or main menu
     */
    @FXML
    void switchScene(ActionEvent event) {
    	ISwapSceneController swapStageController = new SwapSceneController();
    	var pressedBtn = (Button)event.getSource();
    	if(pressedBtn == btnWorktimeTop || pressedBtn == btnWorktime) {
    		swapStageController.goTo("/ui/view/WorkTime.fxml");
    	}
    	else if(pressedBtn == btnAbsenceTop || pressedBtn == btnAbsence) {
 //   		swapStageController.goTo("/ui/view/Absence.fxml");
    	}
    	else if(pressedBtn == btnOverviewTop || pressedBtn == btnOverview) {
    		swapStageController.goTo("/ui/view/Overview.fxml");
    	}
    	else if(pressedBtn == btnHelpTop || pressedBtn == btnHelp) {
    		swapStageController.goTo("/ui/view/Help.fxml");
    	}
    	else if(pressedBtn == btnLogout) {
    		swapStageController.goTo("/ui/view/Login.fxml");
    	}

    }



    	
}
