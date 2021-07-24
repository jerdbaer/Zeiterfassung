package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.MA_Data;

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
    
    public static MA_Data MA_Data;
    
    @FXML
    public void initialize() {
    	MA_Data = new MA_Data();
		var limiter = new MainViewInputRestrictionController();
		limiter.setTextFormatterNumbers(userinput);
		

	}
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
    	MA_Data.setMA_ID(Integer.parseInt(userinput.getText()));
    	MA_Data.setPassword(passswordinput.getText());	
    	
    	 //---------------------
    	return true;
    	//----------------------
    }
    
    
    
}
