package calc.math.gui.controllers;

import calc.math.utils.Logging;

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

private final MainSceneController mainScene;

/**
 * Constructor.
 *
 * @param mainSceneObject The parent 'MainSceneController' object.
 */
public
SetEPSSceneController(MainSceneController mainSceneObject)
{
	mainScene = mainSceneObject;
}

/**
 * Initialize the current object.
 */
public
void initialize()
{
	epsilonToCompareData.setText(Double.toString(mainScene.Epsilon)); // todo: global variables
}

/**
 * Set a new epsilon value.
 */
@FXML
private
void setEPS()
{
	try {
		// todo: we really need change the main controller object
		mainScene.Epsilon = Double.parseDouble(epsilonToCompareData.getText());
	} catch(Exception e) {
		Logging.warning(this, "Handled exception: " + e.getMessage());
	}
}
}
