package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;

import database.GetOvertime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label txtDate;

	@FXML
	private Label txtWorkTime;

	@FXML
	private Label txtBreak;

	@FXML
	private Button btnWorkTime;

	@FXML
	private BarChart<String, Number> chart;

	@FXML
	public void initialize() {
		var MA_ID = LoginController.MA_Data.getMA_ID();
		var dataController = new GetOvertime();
		var data = dataController.getAverageData(MA_ID);
		var averageBreakTimeInSeconds = data.get("Pausengesamtzeit");
		var averageBreakTime = LocalTime.MIN.plusSeconds(averageBreakTimeInSeconds);
		// format to hh:mm via FormatStyle.SHORT
		var averageBreakTimePrintable = averageBreakTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
		txtBreak.setText(averageBreakTimePrintable);

		var averageOvertimeInSeconds = data.get("Ueberstunden");
		var usualWorkingTime = dataController.getUsusalWorkingTime(MA_ID);

		var averageOvertime = LocalTime.parse(usualWorkingTime);

		averageOvertime = averageOvertime.plusSeconds(averageOvertimeInSeconds);
		var averageOverTimePrintable = averageOvertime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
		txtWorkTime.setText(averageOverTimePrintable);
	

	}

	@FXML
	void switchScene(ActionEvent event) {
		var swapStageController = new SwapSceneController();
		var pressedBtn = (Button) event.getSource();
		if (pressedBtn == btnWorktimeTop || pressedBtn == btnWorkTime) {
			swapStageController.goTo("/view/WorkTime.fxml");
		} else if (pressedBtn == btnAbsenceTop) {
			// swapStageController.goTo(Absence);
		} else if (pressedBtn == btnOverviewTop) {
			swapStageController.goTo("/view/Overview.fxml");
		} else if (pressedBtn == btnHelpTop) {
			swapStageController.goTo("/view/Help.fxml");
		} else if (pressedBtn == btnLogout) {
			swapStageController.goTo("/view/Login.fxml");
		}

	}

	@FXML
	void showRealData(ActionEvent event) {
		
		chart.getData().clear();
		
		var buttonclicked = (Button)event.getSource();
		Series<String, Number> workTime;
		if(buttonclicked == btnThisMonth) {
			workTime = thisMonth("month");
		}else if(buttonclicked == btnThisWeek) {
			workTime = thisMonth("week");
		}else {
			workTime = new XYChart.Series<String, Number>();
			final int MINIMUM = 6;
			final int MAXIMUM = 10;
			txtDate.setText("Zufälliger Monat");
			
			for (int i = 0; i <= 31; i++) {
				var randomNum = MINIMUM + (int) (Math.random() * (MAXIMUM - MINIMUM));
				workTime.getData().add(new XYChart.Data<String, Number>(""+ i, randomNum));
			}
			
		}

		

		chart.getData().add(workTime);

	}

	private Series<String, Number> thisMonth(String timespann) {
		var SOLL_ARBEITSZEIT_IN_STUNDEN = 8;
		var MA_ID = LoginController.MA_Data.getMA_ID();
		int today;
		if(timespann.equals("month")) {
			today = LocalDate.now().getDayOfMonth();
			
		}else {
			today = LocalDate.now().getDayOfWeek().getValue();
		}
		var beginDate = LocalDate.now().minusDays(today - 1);
		var endDate = LocalDate.now();
		
		var beginDateLabel = formatDate(beginDate);
		var endDateLabel = formatDate(endDate);
		
		txtDate.setText(beginDateLabel + " - " + endDateLabel);
		
		var overtimeList = fetchData(MA_ID, beginDate.toString(), endDate.toString());

		var workTime = new XYChart.Series<String, Number>();

		for (Overtime ot : overtimeList) {
			var day = ot.getDate().getDayOfMonth();
			var month = ot.getDate().getMonth();
			var label = day + "." + month;

			workTime.getData()
					.add(new XYChart.Data<String, Number>(label, SOLL_ARBEITSZEIT_IN_STUNDEN + ot.getOvertime()));
		}
		return workTime;
	}

	private ArrayList<Overtime> fetchData(int MA_ID, String beginDate, String endDate) {
		var dataMapController = new GetOvertime();
		var dataMap = dataMapController.getMultipleDays(MA_ID, beginDate, endDate);
		ArrayList<Overtime> data = new ArrayList<>();
		dataMap.keySet().stream().forEach(key -> {
			var date = key;
			var hhMmSs = dataMap.get(key);
			var overtimeModel = new Overtime(date, hhMmSs);
			data.add(overtimeModel);
		});

		data.sort(new ComparatorOvertime());

		return data;
	}
	
	private String formatDate(LocalDate date) {
		return String.format("%02d.%02d.%4d" , date.getDayOfMonth(), date.getMonthValue(), date.getYear());
	}
}
