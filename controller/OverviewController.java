package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import database.GetOvertime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import models.ComparatorOvertime;
import models.Overtime;

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
    void showRealData(ActionEvent event) {
    	chart.getData().clear();
    	chart.setCreateSymbols(false);
    	
    	var SOLL_ARBEITSZEIT_IN_STUNDEN = 8;
    	var MA_ID = LoginController.MA_Data.getMA_ID();
    	var beginDate = LocalDate.now().minusDays(30).toString();
    	var endDate = LocalDate.now().toString();
    	var overtimeList = fetchData(MA_ID, beginDate, endDate);
    	
    	var workTime = new XYChart.Series<Number, Number>();
    	var solltime = new XYChart.Series<Number, Number>();
    	
    	for(Overtime ot : overtimeList) {
    		solltime.getData().add(new XYChart.Data<Number, Number>(ot.getDate().getDayOfMonth(),SOLL_ARBEITSZEIT_IN_STUNDEN));
    		workTime.getData().add(new XYChart.Data<Number, Number>(ot.getDate().getDayOfMonth(),SOLL_ARBEITSZEIT_IN_STUNDEN + ot.getOvertime()));
    	}
    	
    	chart.getData().add(workTime);
    	chart.getData().add(solltime);
    	
    	solltime.nodeProperty().get().setStyle("-fx-stroke: #0f358e;");
		workTime.nodeProperty().get().setStyle("-fx-stroke: #f92047;");
    	
    	
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
    private ArrayList<Overtime> fetchData(int MA_ID, String beginDate, String endDate){
    	var dataMapController = new GetOvertime();
    	var dataMap = dataMapController.getMultipleDays(MA_ID, beginDate, endDate);
    	ArrayList<Overtime> data = new ArrayList<>();
    	dataMap.keySet().stream().forEach(key 
    			->{
    				var date = key;
    				var hhMmSs = dataMap.get(key);
    				var overtimeModel = new Overtime(date, hhMmSs);
    				data.add(overtimeModel);
    			});
    	
    	data.sort(new ComparatorOvertime());
    	
    	
    	return data;
    }
}


