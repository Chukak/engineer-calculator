package calc.math.gui.controllers;

import calc.math.expression.Expression;
import calc.math.utils.GlobalSettings;
import calc.math.utils.Logging;

import calc.math.limits.Limit;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The MainSceneController class controller. Manages all object in the main scene implemented in the
 * file 'view/window.fxml'.
 */
public
class MainSceneController
{
/**
 * The reference to TextField object in the main scene.
 */
@FXML
private TextField mathExpressionData;

/**
 * The last caret position.
 */
private int lastCaretPosition = -1;

/**
 * The popup menu.
 */
private Popup popupMenu = new Popup();

/**
 * The current operation.
 */
private OperationType currentOperation = OperationType.Calculator;

/**
 * Operation types
 */
public
enum OperationType
{
	Calculator, //! default calculating
	Limits, //! math limits calculating
	SetEPS //! set EPSILON to comparing double
}

/**
 * Constructor.
 */
public
MainSceneController()
{
	try {
		MenuSceneController menuController = new MenuSceneController(this);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
		loader.setController(menuController);
		popupMenu.getContent().add(loader.load());
	} catch(Exception e) {
		Logging.warning(this, "Handled exception: " + e.getMessage());
		popupMenu = null;
	}
}

/**
 * Read text from TextField and calculates this expression.
 */
@FXML
private
void calculate()
{
	try {
		switch(currentOperation) {
		case Limits: {
			String text = mathExpressionData.getText(), // original text
					variableName = "", // variable name
					approachTo = "", // limit strives for
					patternText = "^lim(.*)->(.*),"; // pattern
			{
				// looking for limits parameters
				Matcher m = Pattern.compile(patternText).matcher(text);
				if(m.find()) {
					variableName = m.group(1).trim();
					approachTo = m.group(2).trim();
				}
				// remove unused words from text
				text = text.substring(m.end());
			}
			mathExpressionData.setText(Double.toString(Limit.calculate(text, variableName,
																																 Double.parseDouble(approachTo),
																																 GlobalSettings.Limits.EPSILON_TO_COMPARE)));
			break;
		}
		case SetEPS:
		default:
			mathExpressionData
					.setText(Double.toString(Expression.calculate(mathExpressionData.getText())));
			break;
		}
	} catch(Exception e) {
		Logging.warning(this, "Handled exception: " + e.getMessage());
	}
}

/**
 * Processes the clicked button. If passed parameter can not cast to Button object, writes error to
 * the logger and returns.
 *
 * @param event clicked button as ActionEvent
 */
@FXML
private
void clickedButton(ActionEvent event)
{
	String buttonText;

	try {
		buttonText = ((Button)event.getSource()).getText();
	} catch(ClassCastException e) {
		Logging.warning(this,
										"Error occurred while converting object from '"  // comment for ignore break line
												+ event.getClass().getName() + "' to '" + Button.class.getName() + "': " + e
												.getMessage());
		return;
	}

	switch(buttonText) {
	case "0":
	case "1":
	case "2":
	case "3":
	case "4":
	case "5":
	case "6":
	case "7":
	case "8":
	case "9":
	case "(":
	case ")":
	case "[":
	case "]":
	case "+":
	case "-":
	case "^":
	case ".":
	case ",":
		insertOrAppend(buttonText);
		break;
	case "mod": // division with remainder
		insertOrAppend("%");
		break;
	case "÷": // decimal utf-8 code "&#247;"
		insertOrAppend("/");
		break;
	case "×": // decimal utf-8 code "&#215;"
		insertOrAppend("*");
		break;
	case "←":  // decimal utf-8 code "&#8592;"
	{
		String text = mathExpressionData.getText();
		mathExpressionData.setText(text.substring(0, text.length() - 1));
		lastCaretPosition = -1;
		break;
	} // scope text variable
	case "AC":
		mathExpressionData.clear();
		lastCaretPosition = -1;
		break;
	case "ℇ": // decimal utf-8 code "&#8455;"
		insertOrAppend(Expression.getConstantName(Math.E));
		break;
	case "π": // decimal utf-8 code "&#960;"
		insertOrAppend(Expression.getConstantName(Math.PI));
		break;
	case "∞": // decimal utf-8 code "&#8734;"
		insertOrAppend(Expression.INF_CONSTANT_NAME);
		break;
	case "√": // decimal utf-8 code "&#8730;"
		buttonText = "sqrt"; // replace function name and continue switching
	case "sin":
	case "cos":
	case "tg":
	case "ctg":
	case "ln":
	case "log": {
		mathExpressionData.requestFocus();
		String selectedText = mathExpressionData.getSelectedText(), text = mathExpressionData.getText(),
				replacement = "";
		if(selectedText.length() > 0) {
			replacement = buttonText + "[" + selectedText + "]";
		} else {
			text = text + buttonText + "[]";
		}
		mathExpressionData.setText(text.replaceAll(selectedText, replacement));
		// reset last caret position and set new caret position
		lastCaretPosition = -1;
		int currentCaretPosition = text.indexOf(replacement) + replacement.length();

		if(selectedText.length() == 0) {
			currentCaretPosition = lastCaretPosition = text.length() - 1;
			// caret inside brackets []
			mathExpressionData.positionCaret(lastCaretPosition);
		}
		mathExpressionData.positionCaret(currentCaretPosition);
		break;
	}
	}
}

/**
 * If the last caret position is not equal -1, insert text to position, otherwise append text.
 *
 * @param text text
 */
private
void insertOrAppend(String text)
{
	if(lastCaretPosition != -1) {
		mathExpressionData.insertText(lastCaretPosition++, text);
	} else {
		mathExpressionData.appendText(text);
	}
}

@FXML
private
void showHelp()
{
}

/**
 * Displays the menu on screen as new scene. If menu already displayed - hide menu.
 */
@FXML
private
void showMenu()
{
	if(popupMenu != null) {
		if(popupMenu.isShowing()) {
			popupMenu.hide();
		} else {
			Window wind = mathExpressionData.getScene().getWindow();
			popupMenu.show(wind, wind.getX(), wind.getY() + wind.getHeight());
		}
	}
}

/**
 * Sets the current operation from menu.
 *
 * @param operationType current operation
 */
public
void setOperation(OperationType operationType)
{
	// skip set epsilon operation
	if(operationType != OperationType.SetEPS) {
		currentOperation = operationType;
	}

	switch(operationType) {
	case Limits: { // math limits calculating
		mathExpressionData.requestFocus();
		mathExpressionData.clear();
		lastCaretPosition = -1;

		insertOrAppend("lim x->0,");
		break;
	}
	case SetEPS: { // just set epsilon for comparing (using in math limits calculating)
		try {
			Stage stage = new Stage();
			stage.setTitle("Set epsilon to compare");
			stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/setEPS.fxml"))));
			stage.show();
		} catch(Exception e) {
			Logging.warning(this, "Handled exception: " + e.getMessage());
		}
		break;
	}
	case Calculator: // default calculator
		lastCaretPosition = -1;
		mathExpressionData.clear();
		break;
	}

	popupMenu.hide();
}
}
