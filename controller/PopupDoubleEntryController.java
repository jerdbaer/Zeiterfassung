package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PopupDoubleEntryController {

    @FXML
    private Label txtDatumOld;

    @FXML
    private Label txtWorkBeginOld;

    @FXML
    private Label txtWorkEndOld;

    @FXML
    private Label txtBreakTimeOld;

    @FXML
    private Label txtDateNew;

    @FXML
    private Label txtWorkBeginNew;

    @FXML
    private Label txtWorkEndNew;

    @FXML
    private Label txtBreakTimeNew;
    
    @FXML
    public void initialize() {
    	
    	//new 
    	var calculations = MainController.getCalculationModel();
    	var selectedDay = calculations.getSelectedDay();
    	txtDatumOld.setText(selectedDay);
    	var workBeginNew = calculations.getWorkBegin();
    	txtWorkBeginNew.setText("Arbeitsbeginn " + workBeginNew);
    	var totalBreakTime = calculations.getTotalBreakTime();
    	
    }

    @FXML
    void abortToWorktime(ActionEvent event) {

    }

    @FXML
    void saveToMenu(ActionEvent event) {

    }

}
