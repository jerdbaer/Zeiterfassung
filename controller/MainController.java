package controller;

import java.time.Duration;
import java.time.LocalTime;
import java.util.function.UnaryOperator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.HBox;

public class MainController {
	
	@FXML
    private HBox hBoxWork1;
	
	@FXML
    private HBox hBoxBreak1;

	@FXML
	private TextField txtfieldWorkStart1Hours;
	@FXML
	private TextField txtfieldWorkStart1Minutes;

	@FXML
	private TextField txtfieldWorkEnd1Hours;

	@FXML
	private TextField txtfieldWorkEnd1Minutes;

	@FXML
	private Button btnWorkAdd1;

	@FXML
	private HBox hBoxWork2;

	@FXML
	private TextField txtfieldWorkStart2Hours;

	@FXML
	private TextField txtfieldWorkStart2Minutes;

	@FXML
	private TextField txtfieldWorkEnd2Hours;

	@FXML
	private TextField txtfieldWorkEnd2Minutes;

	@FXML
	private Button btnWorkAdd2;

	@FXML
	private HBox hBoxWork3;

	@FXML
	private TextField txtfieldWorkStart3Hours;

	@FXML
	private TextField txtfieldWorkStart3Minutes;

	@FXML
	private TextField txtfieldWorkEnd3Hours;

	@FXML
	private TextField txtfieldWorkEnd3Minutes;

	@FXML
	private TextField txtfieldBreakStart1Hours;

	@FXML
	private TextField txtfieldBreakStart1Minutes;

	@FXML
	private TextField txtfieldBreakEnd1Hours;

	@FXML
	private TextField txtfieldBreakEnd1Minutes;

	@FXML
	private Button btnBreakAdd1;

	@FXML
	private HBox hBoxBreak2;

	@FXML
	private TextField txtfieldBreakStart2Hours;

	@FXML
	private TextField txtfieldBreakStart2Minutes;

	@FXML
	private TextField txtfieldBreakEnd2Hours;

	@FXML
	private TextField txtfieldBreakEnd2Minutes;

	@FXML
	private Button btnBreakAdd2;

	@FXML
	private HBox hBoxBreak3;

	@FXML
	private TextField txtfieldBreakStart3Hours;

	@FXML
	private TextField txtfieldBreakStart3Minutes;

	@FXML
	private TextField txtfieldBreakEnd3Hours;

	@FXML
	private TextField txtfieldBreakEnd3Minutes;

	@FXML
	private Button btnBreakAdd3;

	@FXML
	private HBox hBoxBreak4;

	@FXML
	private TextField txtfieldBreakStart4Hours;

	@FXML
	private TextField txtfieldBreakStart4Minutes;

	@FXML
	private TextField txtfieldBreakEnd4Hours;

	@FXML
	private TextField txtfieldBreakEnd4Minutes;

	@FXML
	private Button btnBreakAdd4;

	@FXML
	private HBox hBoxBreak5;

	@FXML
	private TextField txtfieldBreakStart5Hours;

	@FXML
	private TextField txtfieldBreakStart5Minutes;

	@FXML
	private TextField txtfieldBreakEnd5Hours;

	@FXML
	private TextField txtfieldBreakEnd5Minutes;

	@FXML
	private Label labelErrortxt;

	@FXML
	private Button btnInputValidation;

	@FXML
	private Button btnDone;

	@FXML
	public void initialize() {

		// restrict Input
		txtfieldWorkStart1Hours.setTextFormatter(new TextFormatter<String>(hours));
		txtfieldWorkStart2Hours.setTextFormatter(new TextFormatter<String>(hours));
		txtfieldWorkStart3Hours.setTextFormatter(new TextFormatter<String>(hours));

		txtfieldWorkEnd1Hours.setTextFormatter(new TextFormatter<String>(hours));
		txtfieldWorkEnd2Hours.setTextFormatter(new TextFormatter<String>(hours));
		txtfieldWorkEnd3Hours.setTextFormatter(new TextFormatter<String>(hours));

		txtfieldBreakStart1Hours.setTextFormatter(new TextFormatter<String>(hours));
		txtfieldBreakStart2Hours.setTextFormatter(new TextFormatter<String>(hours));
		txtfieldBreakStart3Hours.setTextFormatter(new TextFormatter<String>(hours));
		txtfieldBreakStart4Hours.setTextFormatter(new TextFormatter<String>(hours));
		txtfieldBreakStart5Hours.setTextFormatter(new TextFormatter<String>(hours));

		txtfieldBreakEnd1Hours.setTextFormatter(new TextFormatter<String>(hours));
		txtfieldBreakEnd2Hours.setTextFormatter(new TextFormatter<String>(hours));
		txtfieldBreakEnd3Hours.setTextFormatter(new TextFormatter<String>(hours));
		txtfieldBreakEnd4Hours.setTextFormatter(new TextFormatter<String>(hours));
		txtfieldBreakEnd5Hours.setTextFormatter(new TextFormatter<String>(hours));

		txtfieldWorkStart1Minutes.setTextFormatter(new TextFormatter<String>(minutes));
		txtfieldWorkStart2Minutes.setTextFormatter(new TextFormatter<String>(minutes));
		txtfieldWorkStart3Minutes.setTextFormatter(new TextFormatter<String>(minutes));

		txtfieldWorkEnd1Minutes.setTextFormatter(new TextFormatter<String>(minutes));
		txtfieldWorkEnd2Minutes.setTextFormatter(new TextFormatter<String>(minutes));
		txtfieldWorkEnd3Minutes.setTextFormatter(new TextFormatter<String>(minutes));

		txtfieldBreakStart1Minutes.setTextFormatter(new TextFormatter<String>(minutes));
		txtfieldBreakStart2Minutes.setTextFormatter(new TextFormatter<String>(minutes));
		txtfieldBreakStart3Minutes.setTextFormatter(new TextFormatter<String>(minutes));
		txtfieldBreakStart4Minutes.setTextFormatter(new TextFormatter<String>(minutes));
		txtfieldBreakStart5Minutes.setTextFormatter(new TextFormatter<String>(minutes));

		txtfieldBreakEnd1Minutes.setTextFormatter(new TextFormatter<String>(minutes));
		txtfieldBreakEnd2Minutes.setTextFormatter(new TextFormatter<String>(minutes));
		txtfieldBreakEnd3Minutes.setTextFormatter(new TextFormatter<String>(minutes));
		txtfieldBreakEnd4Minutes.setTextFormatter(new TextFormatter<String>(minutes));
		txtfieldBreakEnd5Minutes.setTextFormatter(new TextFormatter<String>(minutes));

	}

	// restriction rules for hours
	private UnaryOperator<Change> hours = change -> {
		if (!change.isContentChange())
			return change;

		String text = change.getControlNewText();

		return text.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3])$") || text.equals("") ? change : null;
	};

	// restriction rules for minutes
	private UnaryOperator<Change> minutes = change -> {
		if (!change.isContentChange())
			return change;

		String text = change.getControlNewText();

		return text.matches("^([0-9]|[0-5][0-9])$") || text.equals("") ? change : null;
	};

	@FXML
	void computeInput(ActionEvent event) {
		if (txtfieldWorkStart1Hours.getText() != null && txtfieldWorkStart1Minutes.getText() != null
				&& txtfieldWorkEnd1Hours.getText() != null && txtfieldWorkEnd1Minutes.getText() != null) {

		}
	}

	@FXML
	void showHBox(ActionEvent event) {
		Button buttonpressed = (Button) event.getSource();
		if (buttonpressed == btnWorkAdd1) {
			hBoxWork2.setVisible(true);
			btnWorkAdd1.setVisible(false);
		} else if (buttonpressed == btnWorkAdd2) {
			hBoxWork3.setVisible(true);
			btnWorkAdd2.setVisible(false);
		} else if (buttonpressed == btnBreakAdd1) {
			hBoxBreak2.setVisible(true);
			btnBreakAdd1.setVisible(false);
		} else if (buttonpressed == btnBreakAdd2) {
			hBoxBreak3.setVisible(true);
			btnBreakAdd2.setVisible(false);
		} else if (buttonpressed == btnBreakAdd3) {
			hBoxBreak4.setVisible(true);
			btnBreakAdd3.setVisible(false);
		} else if (buttonpressed == btnBreakAdd4) {
			hBoxBreak5.setVisible(true);
			btnBreakAdd4.setVisible(false);
		}

	}

	@FXML
    void validateInput(ActionEvent event) 
    {
		validateHBox(txtfieldWorkStart1Hours.getText(), 
				txtfieldWorkStart1Minutes.getText(), 
				txtfieldWorkEnd1Hours.getText(), 
				txtfieldWorkEnd1Minutes.getText());
		
		validateHBox(txtfieldWorkStart2Hours.getText(), 
				txtfieldWorkStart2Minutes.getText(), 
				txtfieldWorkEnd2Hours.getText(), 
				txtfieldWorkEnd2Minutes.getText());
		
		validateHBox(txtfieldWorkStart3Hours.getText(), 
				txtfieldWorkStart3Minutes.getText(), 
				txtfieldWorkEnd3Hours.getText(), 
				txtfieldWorkEnd3Minutes.getText());
		
		validateHBox(txtfieldBreakStart1Hours.getText(), 
				txtfieldBreakStart1Minutes.getText(), 
				txtfieldBreakEnd1Hours.getText(), 
				txtfieldBreakEnd1Minutes.getText());
		
		validateHBox(txtfieldBreakStart2Hours.getText(), 
				txtfieldBreakStart2Minutes.getText(), 
				txtfieldBreakEnd2Hours.getText(), 
				txtfieldBreakEnd2Minutes.getText());
		
		validateHBox(txtfieldBreakStart3Hours.getText(), 
				txtfieldBreakStart3Minutes.getText(), 
				txtfieldBreakEnd3Hours.getText(), 
				txtfieldBreakEnd3Minutes.getText());
		
		validateHBox(txtfieldBreakStart4Hours.getText(), 
				txtfieldBreakStart4Minutes.getText(), 
				txtfieldBreakEnd4Hours.getText(), 
				txtfieldBreakEnd4Minutes.getText());
		
		validateHBox(txtfieldBreakStart5Hours.getText(), 
				txtfieldBreakStart5Minutes.getText(), 
				txtfieldBreakEnd5Hours.getText(), 
				txtfieldBreakEnd5Minutes.getText());
    }
	
	private enum ValidationState
	{
		VALID,
		NOT_VALID
	}
	
	private ValidationState validateHBox(String startHour, String startMinute, String endHour, String endMinute)
	{

		if(!(startHour.isBlank() && startMinute.isBlank() && endHour.isBlank() &&  endMinute.isBlank()) && 
				!(!startHour.isBlank() && !startMinute.isBlank() && !endHour.isBlank() &&  !endMinute.isBlank())) 
			return ValidationState.NOT_VALID;
		
		var begin = LocalTime.of(Integer.parseInt(startHour),Integer.parseInt(startMinute));
		var end =LocalTime.of(Integer.parseInt(endHour),Integer.parseInt(endMinute));
		
		if (begin.isAfter(end))
			return ValidationState.NOT_VALID;
		
		return ValidationState.VALID;
	}
	
	private LocalTime format(String hour, String minute)
	{
		return LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute));
	}
}	
