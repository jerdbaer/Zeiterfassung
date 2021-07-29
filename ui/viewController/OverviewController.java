package ui.viewController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;

import database.GetOvertime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.Main;
import models.ComparatorOvertime;
import models.Overtime;
import ui.controller.SwapSceneController;
import ui.interfaces.ISwapSceneController;

/**
 * Program to provide an overview about personal work related data of the user.
 * Displays some working time data as overview for current week, current month
 * or earlier month.
 * 
 * @author Tom Weißflog
 * @version 1.0
 *
 */

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
	private Button btnSampleMonth;

	@FXML
	private Label txtFlexiTime;

	@FXML
	private Label txtDate;

	@FXML
	private Label txtWorkTime;

	@FXML
	private Label txtBreak;

	@FXML
	private Button btnOtherMonth;

	@FXML
	private Button btnWorkTime;

	@FXML
	private BarChart<String, Number> chart;

	/**
	 * on show displays core data remark: flexitime, breaktime, working time
	 */
	@FXML
	public void initialize() {
		var MA_ID = Main.dataEntryModel.getMA_ID();
		var dataController = new GetOvertime();
		var data = dataController.getAverageData(MA_ID);
		try {
			displayFlexiTime(MA_ID, dataController);
			displayAverageBreakTime(data);
			displayAverageWorkingTime(MA_ID, dataController, data);
		}catch (NullPointerException noAverageDataFound) {
			
		}
		

	}

	/**
	 * fetches average overtime and planned working time from database as hh:mm:ss
	 * calculates average working time and converts to hh:mm for display
	 * 
	 * @see GetOvertime().getPlannedWorkingTime(String MA_ID);
	 * @see GetOvertime().getAverageData(MA_ID).get("Ueberstunden")
	 * @param MA_ID
	 * @param dataController
	 * @param data
	 */
	private void displayAverageWorkingTime(int MA_ID, GetOvertime dataController, HashMap<String, Integer> data) {

		var averageOvertimeInSeconds = data.get("Ueberstunden");
		var usualWorkingTime = dataController.getPlannedWorkingTime(MA_ID);
		var averageOvertime = LocalTime.parse(usualWorkingTime);
		averageOvertime = averageOvertime.plusSeconds(averageOvertimeInSeconds);
		var averageOverTimePrintable = averageOvertime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
		txtWorkTime.setText(averageOverTimePrintable);
	}

	/**
	 * fetches average break time from database as hh:mm:ss converts to hh:mm for
	 * display
	 * 
	 * @see GetOvertime().getAverageData(MA_ID).get("Pausengesamtzeit")
	 * @param data
	 */
	private void displayAverageBreakTime(HashMap<String, Integer> data) {
		var averageBreakTimeInSeconds = data.get("Pausengesamtzeit");
		var averageBreakTime = LocalTime.MIN.plusSeconds(averageBreakTimeInSeconds);
		// format to hh:mm via FormatStyle.SHORT
		var averageBreakTimePrintable = averageBreakTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
		txtBreak.setText(averageBreakTimePrintable);
	}

	/**
	 * fetches flexitime from database as hh:mm:ss converts to hh:mm for display
	 * 
	 * @see GetOvertime().getSum(String MA_ID)
	 * @param MA_ID
	 * @param dataController
	 */
	private void displayFlexiTime(int MA_ID, GetOvertime dataController) {
		var flexitime = dataController.getSum(MA_ID);
		if (!flexitime.contains("-"))
			flexitime = "+" + flexitime;
		flexitime = flexitime.substring(0, (flexitime.length() - 3));
		txtFlexiTime.setText(flexitime);
	}

	/**
	 * Handles user choice to switch window and loads the window of the related fxml
	 * file.
	 * 
	 * @param event button click on "Arbeitszeit erfassen", "Abwesenheit verwalten"
	 *              "Übersicht", "Hilfe" or "Logout" at top menu
	 */
	@FXML
	void switchScene(ActionEvent event) {
		ISwapSceneController swapStageController = new SwapSceneController();
		var pressedBtn = (Button) event.getSource();
		if (pressedBtn == btnWorktimeTop || pressedBtn == btnWorkTime) {
			swapStageController.goTo("/ui/view/WorkTime.fxml");
		} else if (pressedBtn == btnAbsenceTop) {
			// swapStageController.goTo(Absence);
		} else if (pressedBtn == btnOverviewTop) {
			swapStageController.goTo("/ui/view/Menu.fxml");
		} else if (pressedBtn == btnHelpTop) {
			swapStageController.goTo("/ui/view/Help.fxml");
		} else if (pressedBtn == btnLogout) {
			swapStageController.goTo("/ui/view/Login.fxml");
		}
	}

	/**
	 * Displays this week, month or random working time data to show the chart
	 * function
	 * 
	 * @param event button click "Zufallswerte"
	 * @param event button cick "Aktuelle Woche"
	 * @param event button click "Aktueller Monat"
	 */
	@FXML
	void showRealData(ActionEvent event) {
		var buttons = new ArrayList<Button>();
		buttons.add(btnThisWeek);
		buttons.add(btnThisMonth);
		buttons.add(btnSampleMonth);
		style(buttons, event);
		chart.getData().clear();

		var buttonclicked = (Button) event.getSource();
		Series<String, Number> workTime;
		if (buttonclicked == btnThisMonth) {
			workTime = produceSeries("month");
		} else if (buttonclicked == btnThisWeek) {
			workTime = produceSeries("week");
		} else {
			workTime = new XYChart.Series<String, Number>();
			final int MINIMUM = 6;
			final int MAXIMUM = 10;
			txtDate.setText("Zuf�lliger Monat");

			for (int i = 0; i <= 31; i++) {
				var randomNum = MINIMUM + (int) (Math.random() * (MAXIMUM - MINIMUM));
				workTime.getData().add(new XYChart.Data<String, Number>("" + i, randomNum));
			}

		}

		chart.getData().add(workTime);

	}

	/**
	 * applies button effect to buttons
	 * 
	 * @param buttons "Aktuelle Woche", "Aktueller Monat", "Zufallswerte"
	 * @param event   buttonclick
	 */
	private void style(ArrayList<Button> buttons, ActionEvent buttonclick) {
		var clickedButton = (Button) buttonclick.getSource();
		var otherButtons = buttons;
		var clickedButtonIsInButtons = buttons.stream().anyMatch(button -> button.equals(clickedButton));
		otherButtons.remove(clickedButton);
		if (clickedButtonIsInButtons) {
			clickedButton.setStyle(
					"-fx-background-color: #0f358e;" + " -fx-border-color: #0f358e;" + " -fx-text-fill: #ffffff;");

			for (Button button : otherButtons) {
				button.setStyle("-fx-background-color: #ffffff;" + "	-fx-border-color:  #9dadca;"
						+ "	-fx-text-fill:  #0f358e;");
			}
		}
	}

	/**
	 * creates series from database and displays the timespann of the series
	 * 
	 * @param timespann month or not (week)
	 * @return Series to display in bar-chart
	 */
	private Series<String, Number> produceSeries(String timespann) {
		var MA_ID = Main.dataEntryModel.getMA_ID();
		float usualWorkingTime = fetchPlannedWorkingTime(MA_ID);

		int today;
		if (timespann.equals("month")) {
			today = LocalDate.now().getDayOfMonth();

		} else {
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
					.add(new XYChart.Data<String, Number>(label, usualWorkingTime + ot.getOvertime()));
		}
		return workTime;
	}

	/**
	 * fetches overtime (date and value) from database
	 * converts HashMap(date,overtime) to sorted ArrayList<Overtime>
	 * @param MA_ID
	 * @param beginDate
	 * @param endDate
	 * @return list of Overtime
	 */
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
	/**
	 * fetches planned working time from database
	 * 
	 * @param MA_ID
	 * @return planned working time as float
	 */
	private float fetchPlannedWorkingTime(int MA_ID) {
		var dataController = new GetOvertime();
    	var plannedWorkingtimeString = dataController.getPlannedWorkingTime(MA_ID);
    	var plannedWorkingTime = LocalTime.parse(plannedWorkingtimeString);
    	var plannedWorkingTimeFloat = plannedWorkingTime.getHour() +
    			((float)plannedWorkingTime.getMinute()/60);
    	return plannedWorkingTimeFloat;
	}

	/**
	 * format LocalDate value
	 * 
	 * @param date yyyy-mm-dd
	 * @return dd.mm.yyyy
	 */
	private String formatDate(LocalDate date) {
		return String.format("%02d.%02d.%4d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
	}

}
