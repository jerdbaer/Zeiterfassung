package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

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
    
    @FXML
    void login(ActionEvent event) {
    	var swapStageController = new SwapStageController();
    	if(checkLogin())
    		swapStageController.goTo("/view/Menu.fxml");
    	else {
    		txtError.setVisible(true);
		}
    }
    
    private boolean checkLogin() {
    	var user = userinput.getText();
    	int password = passswordinput.getText().hashCode();	
    	
    	//-----------------------------
    	System.out.println(user);
    	System.out.println(password);
    	//-----------------------------
    	 //---------------------
    	return true;
    	//----------------------
    }
    
    
    
}
