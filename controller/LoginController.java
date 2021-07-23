package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController {

    @FXML
    private AnchorPane loginscene;

    @FXML
    private TextField userinput;

    @FXML
    private PasswordField passswordinput;

    @FXML
    private Button loginbutton;

    @FXML
    private MenuItem gottoHelpCenter1;
    
    @FXML
    private Label txtError;
    
    @FXML
    void login(ActionEvent event) {
    	var swapStageController = new SwapStageController();
    	if(checkLogin())
    		swapStageController.goToMenu();
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
