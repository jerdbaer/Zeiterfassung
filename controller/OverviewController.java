package controller;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
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
    private BarChart<String, Number> chart;

    @FXML
    void switchScene(ActionEvent event) {
    	var swapStageController = new SwapStageController();
    	var pressedBtn = (Button)event.getSource();
    	if(pressedBtn == btnWorktimeTop || pressedBtn == btnWorkTime) {
    		swapStageController.goTo("/view/WorkTime.fxml");
    	}
    	else if(pressedBtn == btnAbsenceTop) {
 //   		swapStageController.goTo(Absence);
    	}
    	else if(pressedBtn == btnOverviewTop) {
    		swapStageController.goTo("/view/Menu.fxml");
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
    	var buttons = new ArrayList<Button>();
    	buttons.add(btnThisWeek);
    	buttons.add(btnThisMonth);
    	buttons.add(btnOtherMonth);

    	style(buttons, event);
    	chart.getData().clear();
    	
    	final int MINIMUM = 6;
    	final int MAXIMUM = 10;
    	final int SOLL = 8;
    	var workTime = new XYChart.Series<String, Number>();
    	workTime.setName("Arbeitszeit");
    	var sollTime = new XYChart.Series<String, Number>();
    	sollTime.setName("Soll Arbeitszeit");
  
		for (int i=0;i<=31;i++)
		{
			var randomNum = MINIMUM + (int)(Math.random() * (MAXIMUM - MINIMUM));
			sollTime.getData().add(new XYChart.Data<String, Number>(""+i,SOLL));
//			workTime.getData().add(new XYChart.Data<String, Number>(""+i,randomNum));
		}
		chart.getData().add(sollTime);
//		chart.getData().get(0).getNode().setStyle("-fx-bar-fill: #0f358e;");
//		chart.getData().add(workTime);
//		sollTime.nodeProperty.get().setStyle("-fx-bar-fill: -fx-soll-time;");
//		workTime.nodeProperty().get().setStyle("-fx-stroke: #f92047;");
	}
    
    private void style(ArrayList<Button> buttons, ActionEvent buttonclick) {
    	var clickedButton = (Button)buttonclick.getSource();
    	var otherButtons = buttons;
    	var clickedButtonIsInButtons = buttons.stream().anyMatch(button -> button.equals(clickedButton));
    	otherButtons.remove(clickedButton);
    	if(clickedButtonIsInButtons) {
    		clickedButton.setStyle("-fx-background-color: #0f358e; -fx-border-color: #0f358e; -fx-text-fill: #ffffff;");
    		
    		for(Button button : otherButtons) {
    			button.setStyle("-fx-background-color: #ffffff;"
    					+ "	-fx-border-color:  #9dadca;"
    					+ "	-fx-text-fill:  #0f358e;");
    		}

    		


    		
    	}
    }

}
