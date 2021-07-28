package ui.controller;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import ui.interfaces.ISwapSceneController;

/**
 * Program to switch between scenes
 * 
 * @author Tom Weißflog
 * @version 1.0
 *
 */
public class SwapSceneController implements ISwapSceneController{

	/**
	 * Loads window of the submitted filename 
	 * @param filename is the name of the fxml file which should be loaded
	 */
	@Override
	public void goTo(String filename) {
    	try {
    		var stage = Main.primarystage;
        	var menuContent = (BorderPane)FXMLLoader.load(getClass().getResource(filename));
        	var menu = new Scene(menuContent);
        	stage.setScene(menu);
        	stage.show();
    	}catch(IOException e) {
			e.printStackTrace();

    	}
    }

	
	/**
	 * Opens popup window of the submitted filename
	 * @param filename is the name of the fxml file which should be loaded
	 */
	@Override
	public void showPopup(String filename) {
        try {
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			var popupContent = (BorderPane)FXMLLoader.load(getClass().getResource(filename));
			var popup = new Scene(popupContent);
			dialog.initStyle(StageStyle.UNDECORATED);
			dialog.setScene(popup);
			dialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }


}
