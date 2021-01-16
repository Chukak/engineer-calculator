package calc.math.gui.controllers;

import calc.math.utils.Logging;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * The HelpSceneController class controller. Displays the help for this calculator.
 */
public
class HelpSceneController
{

/**
 * The FileContent class. Contains some data of *.html files.
 */
private static
class FileContent
{
	public Double Height;
	public Double Width;
	public String Filename;

	public
	FileContent(String filename, double width, double height)
	{
		Filename = filename;
		Width = width;
		Height = height;
	}
}

/**
 * Categories.
 */
@FXML
private ListView<String> listCategories;

/**
 * Text in the format HTML.
 */
// todo: WebView is slow that TextArea
@FXML
private WebView textArea;

/**
 * List categories.
 */
private final String[] categories = { "Usage.", // index 0
																			"Math functions.", // 1
																			"Math constants.", // 2
																			"Math limits calculating." // 3
};

/**
 * File names with the text for each category. Height and width are set depending on each *html
 * file, because we can not call WebEngine.executeScript("document.height"), because this scene is
 * open on the desktop, not browser.
 */
private final HashMap<Integer, FileContent> filesNames = new HashMap<>()
{{
	put(0, new FileContent("/html/usage.html", 800, 800));
	put(1, new FileContent("/html/functions.html", 800, 600));
	put(2, new FileContent("/html/constants.html", 800, 600));
	put(3, new FileContent("/html/mathLimits.html", 800, 600));
}};

/**
 * Initialize current object.
 */
public
void initialize()
{
	// fill the listCategories
	listCategories.setItems(FXCollections.observableArrayList(categories));

	textArea.setCache(true);

	for(Map.Entry<Integer, FileContent> entry : filesNames.entrySet()) {
		entry.getValue().Width = textArea.getPrefWidth();
	}
}

/**
 * Processes the current selected list item and show help for the selected category.
 *
 * @param event clicked mouse button on the list item.
 */
@FXML
private
void handleCategory(MouseEvent event)
{
	listCategories.requestFocus();

	int index = listCategories.getSelectionModel().getSelectedIndex();
	if(index - 1 > filesNames.size()) {
		Logging.warning(this, "Invalid index: " + index);
		return;
	}

	FileContent content = filesNames.get(index);
	if(content != null) {
		try {
			// For java 8. In java 11, we can use readString(...)
			Stream<String> lines = Files
					.lines(new File(getClass().getResource(content.Filename).getPath()).toPath(),
								 StandardCharsets.UTF_8);

			StringBuilder textBuilder = new StringBuilder();
			lines.forEach(line -> textBuilder.append(line));
			textArea.getEngine().loadContent(textBuilder.toString());
			textArea.setPrefSize(content.Width, content.Height);
		} catch(Exception e) {
			Logging.warning(this, "Handled exception: " + e.getMessage() + " type: " + e.toString());
		}
	}
}
}
