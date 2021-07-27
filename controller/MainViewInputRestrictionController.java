package controller;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;

/**
 * This provides rules to restrict the possible key input (includes Strg+V or Mouse copy) in Textfields
 * Restrictions: only Numbers, only hours (0-23)m only minutes(0-59)
 * 
 * @author Tom Weißflog
 * @version 1.0
 */
public class MainViewInputRestrictionController {
	
	private TextField[] textfieldHour;
	private TextField[] textfieldMinute;
	
	// restriction rules only Numbers
	private UnaryOperator<Change> numbers = change -> {
		if (!change.isContentChange())
			return change;

		String text = change.getControlNewText();

		return text.matches("[0-9]*") || text.equals("") ? change : null;
	};

	private UnaryOperator<Change> hours = change -> {
		if (!change.isContentChange())
			return change;
		
		String text = change.getControlNewText();

		return text.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3])$") || text.equals("") ? change : null;
	};

	private UnaryOperator<Change> minutes = change -> {
		if (!change.isContentChange())
			return change;

		String text = change.getControlNewText();

		return text.matches("^([0-9]|[0-5][0-9])$") || text.equals("") ? change : null;
	};

	public TextField[] getTextfieldHour() {
		return textfieldHour;
	}

	public void setTextfieldHour(TextField[] textfieldHour) {
		this.textfieldHour = textfieldHour;
	}

	public TextField[] getTextfieldMinute() {
		return textfieldMinute;
	}
	
	public void setTextFormatterNumbers(TextField textfield) {
		textfield.setTextFormatter(new TextFormatter<String>(numbers));
	}

	public void setTextfieldMinute(TextField[] textfieldMinute) {
		this.textfieldMinute = textfieldMinute;
	}

	public void setTextFormatter(TextField[] textfieldHour, TextField[] textfieldMinute) {
		for (TextField hour : textfieldHour)
			hour.setTextFormatter(new TextFormatter<String>(hours));

		for (TextField minute : textfieldMinute)
			minute.setTextFormatter(new TextFormatter<String>(minutes));
	}

}
