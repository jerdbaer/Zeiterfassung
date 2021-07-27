package controller;

import database.CheckPassword;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import models.MA_Data;

public class LoginController {

    @FXML
    private Label txtUser;

    @FXML
    private TextField userinput;

    @FXML
    private Label txtPassword;

    @FXML
    private PasswordField passswordinput;

    @FXML
    private Label txtError;

    @FXML
    private Button loginbutton;

    @FXML
    void animation(KeyEvent event) {
    	if(userinput.getText().isBlank())
    		txtUser.setVisible(false);
    	else {
			txtUser.setVisible(true);
		}
    	
    	if(passswordinput.getText().isBlank())
    		txtPassword.setVisible(false);
    	else {
			txtPassword.setVisible(true);
		}
    }
    
    public static MA_Data MA_Data;
    
    @FXML
    public void initialize() {
    	MA_Data = new MA_Data();
		var limiter = new MainViewInputRestrictionController();
		limiter.setTextFormatterNumbers(userinput);
		

	}
    @FXML
    void login(ActionEvent event) {
    	
    	var swapStageController = new SwapSceneController();
    	if(checkLogin())
    		swapStageController.goTo("/view/Menu.fxml");
    	else {
    		txtError.setVisible(true);
		}
    }
    
    private boolean checkLogin() {
    	MA_Data.setMA_ID(Integer.parseInt(userinput.getText()));
    	MA_Data.setPassword(passswordinput.getText());
    	
    	var MA_ID = MA_Data.getMA_ID();
    	var pw = MA_Data.getPassword();
    	
    	return CheckPassword.checkPW(MA_ID, pw);
    	
    	}
    
    
    
    
    
}
