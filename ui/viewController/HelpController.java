package ui.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ui.controller.SwapSceneController;
import ui.interfaces.ISwapSceneController;

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

	/**
	 * Handles user choice to switch window and loads the window of the related fxml.file.
	 * 
	 * @param event button click on "Arbeitszeit erfassen", "Abwesenheit verwalten"
	 *              "Übersicht", "Hilfe" or "Logout" at top menu
	 */
	@FXML
	void switchScene(ActionEvent event) {
		ISwapSceneController swapStageController = new SwapSceneController();
		var pressedBtn = (Button) event.getSource();
		if (pressedBtn == btnWorktimeTop) {
			swapStageController.goTo("/ui/view/WorkTime.fxml");
		} else if (pressedBtn == btnAbsenceTop) {
// swapStageController.goTo("/ui/view/Absence.fxml");
		} else if (pressedBtn == btnOverviewTop) {
			swapStageController.goTo("/ui/view/Overview.fxml");
		} else if (pressedBtn == btnHelpTop) {
			swapStageController.goTo("/ui/view/Menu.fxml");
		} else if (pressedBtn == btnLogout) {
			swapStageController.goTo("/ui/view/Login.fxml");
		}

	}
}
