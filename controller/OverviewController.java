package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

public class OverviewController {


    @FXML
    private Button btnWorktimeTop;

    @FXML
    private Button btnAbsenceTop;

    @FXML
    private Button btnOverviewTop;

    @FXML
    private Button btnHelpTop;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnThisWeek;

    @FXML
    private Button btnThisMonth;

    @FXML
    private Button btnOtherMonth;

    @FXML
    private Button btnWorkTime;
    
    @FXML
    private LineChart<Number, Number> chart;

    @FXML
    void switchScene(ActionEvent event) {
    	var swapStageController = new SwapSceneController();
    	var pressedBtn = (Button)event.getSource();
    	if(pressedBtn == btnWorktimeTop || pressedBtn == btnWorkTime) {
    		swapStageController.goTo("/view/WorkTime.fxml");
    	}
    	else if(pressedBtn == btnAbsenceTop) {
 //   		swapStageController.goTo(Absence);
    	}
    	else if(pressedBtn == btnOverviewTop) {
    		swapStageController.goTo("/view/Overview.fxml");
    	}
    	else if(pressedBtn == btnHelpTop) {
    		swapStageController.goTo("/view/Help.fxml");
    	}
    	else if(pressedBtn == btnLogout) {
    		swapStageController.goTo("/view/Login.fxml");
    	}

    }
    
    @FXML
    void showSampleData(ActionEvent event) {
    	chart.getData().clear();
    	chart.setCreateSymbols(false);
    	
    	final int MINIMUM = 6;
    	final int MAXIMUM = 10;
    	final int SOLL = 8;
    	var workTime = new XYChart.Series<Number, Number>();
    	workTime.setName("Arbeitszeit");
    	var sollTime = new XYChart.Series<Number, Number>();
    	sollTime.setName("Soll Arbeitszeit");
  
		for (int i=0;i<=31;i++)
		{
			var randomNum = MINIMUM + (int)(Math.random() * (MAXIMUM - MINIMUM));
			sollTime.getData().add(new XYChart.Data<Number, Number>(i,SOLL));
			workTime.getData().add(new XYChart.Data<Number, Number>(i,randomNum));
		}
		chart.getData().add(sollTime);					
		chart.getData().add(workTime);
		sollTime.nodeProperty().get().setStyle("-fx-stroke: #0f358e;");
		workTime.nodeProperty().get().setStyle("-fx-stroke: #f92047;");
	}    

}
