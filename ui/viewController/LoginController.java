package ui.viewController;

import database.CheckPassword;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import main.Main;
import ui.controller.SwapSceneController;
import ui.controller.ViewInputRestrictionController;
import ui.interfaces.ISwapSceneController;

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

    
    /**
	 * shows Label on top of the Textfields 
	 * if the user tipps an Username or Password
	 */
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
    
    /**
	 * Initializes user input restriction while user is typing and displays user
	 * input at window
	 */
    @FXML
    public void initialize() {
		var limiter = new ViewInputRestrictionController();
		limiter.setTextFormatterNumbers(userinput);
	}
    
    /**
	 * if the login was successful  
	 * switches Scene to MainMenu
	 */
    @FXML
    void login(ActionEvent event) {
    	
    	ISwapSceneController swapStageController = new SwapSceneController();
    	if(checkLogin())
    		swapStageController.goTo("/ui/view/Menu.fxml");
    	else {
    		txtError.setVisible(true);
		}
    }
    
    /**
	 * passe login data to Main.dataEntryModel 
	 * and validates with dataEntry
	 * 
	 * @param textfields userinput, passwordinput
	 * @return boolean 
	 */    
    private boolean checkLogin() {
    	if(!(userinput.getText().isBlank())){
    		Main.dataEntryModel.setMA_ID(Integer.parseInt(userinput.getText()));
        	Main.dataEntryModel.setPassword(passswordinput.getText());
        	
        	var MA_ID = Main.dataEntryModel.getMA_ID();
        	var pw =  Main.dataEntryModel.getPassword();
        	
        	return CheckPassword.checkPW(MA_ID, pw);
    	}
    	
    	return false;
    	
    	}
    
    
    
    
    
}
