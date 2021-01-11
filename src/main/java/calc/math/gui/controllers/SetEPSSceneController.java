package calc.math.gui.controllers;

import calc.math.utils.Logging;
import calc.math.utils.GlobalSettings;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * The SetEPSSceneController class controller. Set the epsilon for comparing two doubles (using in
 * calculating math limits). Implemented in 'view/setEPS.fxml'.
 */
public
class SetEPSSceneController
{
/**
 * Epsilon value.
 */
@FXML
private TextField epsilonToCompareData;

/**
 * Initialize the current object.
 */
public
void initialize()
{
	epsilonToCompareData.setText(Double.toString(GlobalSettings.Limits.EPSILON_TO_COMPARE));
}

/**
 * Set a new epsilon value.
 */
@FXML
private
void setEPS()
{
	try {
		GlobalSettings.Limits.EPSILON_TO_COMPARE = Double.parseDouble(epsilonToCompareData.getText());
	} catch(Exception e) {
		Logging.warning(this, "Handled exception: " + e.getMessage());
	}
}
}
