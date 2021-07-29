package ui.interfaces;

public interface ISwapSceneController {

	/**
	 * Loads window of the submitted filename 
	 * @param filename is the name of the fxml file which should be loaded
	 */
	void goTo(String filename);

	/**
	 * Opens popup window of the submitted filename
	 * @param filename is the name of the fxml file which should be loaded
	 */
	void showPopup(String filename);

}