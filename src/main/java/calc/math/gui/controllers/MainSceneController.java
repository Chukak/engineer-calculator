package calc.math.gui.controllers;

import calc.math.expression.Expression;

import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * The MainSceneController class controller. Manages all object in the main scene implemented in the
 * file 'view/window.fxml'.
 */
public
class MainSceneController
{
/**
 * The logger.
 */
private static final Logger logger = Logger.getLogger(MainSceneController.class.getName());

/**
 * The reference to TextField object in the main scene.
 */
@FXML
private TextField mathExpressionData;

/**
 * The last caret position.
 */
private int lastCaretPosition = -1;

public
MainSceneController()
{
}

/**
 * Read text from TextField and calculates this expression.
 */
@FXML
private
void calculate()
{
	try {
		mathExpressionData.setText(Double.toString(Expression.calculate(mathExpressionData.getText())));
	} catch(Exception e) {
		logger.log(Level.INFO, e.toString());
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
		logger.log(Level.INFO, "Cast error '" + event + " -> " + Button.class.getName() + ".");
		logger.log(Level.INFO, e.toString());
		return;
	}
	System.out.println(buttonText);

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
		mathExpressionData.insertText(lastCaretPosition, text);
	} else {
		mathExpressionData.appendText(text);
	}
}
}
