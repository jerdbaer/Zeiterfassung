package controller;

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
import view.Main;

public class SwapSceneController{


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

	public void goToLogin() {
    	try {
    		var stage = Main.primarystage;;
        	var loginContent = (BorderPane)FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        	var login = new Scene(loginContent);
        	stage.setScene(login);
    	}catch(IOException e) {
			e.printStackTrace();

    	}
    }

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
