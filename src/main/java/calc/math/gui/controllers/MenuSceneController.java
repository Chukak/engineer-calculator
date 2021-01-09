package calc.math.gui.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * The MenuSceneController class controller. Manages menu object implemented in the file
 * 'view/menu.fxml'.
 */
public
class MenuSceneController
{

/**
 * Menu items.
 */
@FXML
private ListView<String> listMenu;

/**
 * Menu item names.
 */
private final String[] menuItems = { "Calculator", // 0 index
																		 "Math limits", // 1 index
																		 "Set EPS (epsilon to compare)" // 2 index
};

private final MainSceneController mainScene;

/**
 * Constructor.
 *
 * @param mainSceneObject The parent 'MainSceneController' object.
 */
public
MenuSceneController(MainSceneController mainSceneObject)
{
	mainScene = mainSceneObject;
}

/**
 * Initialize the current object.
 */
public
void initialize()
{
	// fill the listView.
	listMenu.setItems(FXCollections.observableArrayList(menuItems));
}

/**
 * Processes the clicked button and process the selected item from menu.
 *
 * @param event clicked button as ActionEvent
 */
@FXML
private
void processItem(ActionEvent event)
{
	listMenu.requestFocus();

	int selectedIndex = listMenu.getSelectionModel().getSelectedIndex();
	switch(selectedIndex) {
	case 0:
		mainScene.setOperation(MainSceneController.OperationType.Calculator);
		break;
	case 1:
		mainScene.setOperation(MainSceneController.OperationType.Limits);
		break;
	case 2:
		mainScene.setOperation(MainSceneController.OperationType.SetEPS);
		break;
	}
}
}
