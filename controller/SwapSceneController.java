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
	
	
	public void goToMenu() {
    	try {
    		var stage = Main.primarystage;
        	var menuContent = (BorderPane)FXMLLoader.load(getClass().getResource("/view/Menu.fxml"));
        	var menu = new Scene(menuContent);
        	stage.setScene(menu);
        	stage.show();
    	}catch(IOException e) {
			e.printStackTrace();

    	}
    }
	
	
	public void goToWorkTime() {
    	try {
    		var stage = Main.primarystage;
        	var workTimeContent = (BorderPane)FXMLLoader.load(getClass().getResource("/view/WorkTime.fxml"));
        	var workTime = new Scene(workTimeContent);
        	stage.setScene(workTime);
        	
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
	
    public void showPopupAbort() {
        try {
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			var popupContent = (BorderPane)FXMLLoader.load(getClass().getResource("/view/PopupAbort.fxml"));
			var popup = new Scene(popupContent);
			dialog.initStyle(StageStyle.UNDECORATED);
			dialog.setScene(popup);
			dialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void showPopupValid() {
    	try {
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			var popupContent = (BorderPane)FXMLLoader.load(getClass().getResource("/view/PopupValid.fxml"));
			var popup = new Scene(popupContent);
			dialog.initStyle(StageStyle.UNDECORATED);
			dialog.setScene(popup);
			dialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void showPopupLimits() {
    	try {
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			var popupContent = (BorderPane)FXMLLoader.load(getClass().getResource("/view/PopupLimits.fxml"));
			var popup = new Scene(popupContent);
			dialog.initStyle(StageStyle.UNDECORATED);
			dialog.setScene(popup);
			dialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
