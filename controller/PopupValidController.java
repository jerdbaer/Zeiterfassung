package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PopupValidController {

    @FXML
    private Label txtDatum;

    @FXML
    private Label txtTimes;
    
    @FXML
    public void initialize() {
    	var calculations = MainController.getCalculationModel();
    	var selectedDay = calculations.getSelectedDay();
    	txtDatum.setText(selectedDay);
    	var workingTime = calculations.getTotalWorkTime();
    	var totalBreakTime = calculations.getTotalBreakTime();
    	txtTimes.setText("Arbeitszeit " + workingTime + " mit " + totalBreakTime + " Pausenzeit" );
    	
    }

    @FXML
    void abortToWorktime(ActionEvent event) {
    	var popup = ((Button)event.getSource()).getScene().getWindow();
    	popup.hide();

    }

    @FXML
    void saveToMenu(ActionEvent event) {

    }

}
