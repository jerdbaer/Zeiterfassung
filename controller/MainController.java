package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class MainController {


    @FXML
    private Button btnWorkAdd1;

    @FXML
    private HBox hBoxWork2;

    @FXML
    private Button btnWorkAdd2;

    @FXML
    private HBox hBoxWork3;

    @FXML
    private Button btnBreakAdd1;

    @FXML
    private HBox hBoxBreak2;

    @FXML
    private Button btnBreakAdd2;

    @FXML
    private HBox hBoxBreak3;

    @FXML
    private Button btnBreakAdd3;

    @FXML
    private HBox hBoxBreak4;

    @FXML
    private Button btnBreakAdd4;

    @FXML
    private HBox hBoxBreak5;

    @FXML
    private Button btnBreakAdd5;

    @FXML
    void showHBox(ActionEvent event) 
    {
    	Button buttonpressed = (Button) event.getSource();
    	if(buttonpressed == btnWorkAdd1)
    	{
        	hBoxWork2.setVisible(true);
        	btnWorkAdd1.setVisible(false);
    	}
    	else if(buttonpressed == btnWorkAdd2)
    	{
    		hBoxWork3.setVisible(true);
        	btnWorkAdd2.setVisible(false);
    	}
    	else if(buttonpressed == btnBreakAdd1)
    	{
    		hBoxBreak2.setVisible(true);
    		btnBreakAdd1.setVisible(false);
    	}
    	else if(buttonpressed == btnBreakAdd2)
    	{
    		hBoxBreak3.setVisible(true);
    		btnBreakAdd2.setVisible(false);
    	}
    	else if(buttonpressed == btnBreakAdd3)
    	{
    		hBoxBreak4.setVisible(true);
    		btnBreakAdd3.setVisible(false);
    	}
    	else if(buttonpressed == btnBreakAdd4)
    	{
    		hBoxBreak5.setVisible(true);
    		btnBreakAdd4.setVisible(false);
    	}

    }

}
