package calc.math;

import calc.math.expression.Expression;

import java.util.Arrays;
import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public
class Main extends Application
{
@Override
public
void start(Stage stage) throws Exception
{
	Parent root = FXMLLoader.load(getClass().getResource("/view/window.fxml"));
	stage.setTitle("Engineering calculator");
	stage.setScene(new Scene(root));
	stage.show();
}

public static
void main(String[] args)
{
	if(Arrays.asList(args).contains("--non-interactive")) {
		System.out.println("Math expression:");
		Scanner cin = new Scanner(System.in);
		try {
			System.out.println("Result: " + Expression.calculate(cin.nextLine()));
		} catch(Exception e) {
			System.out.println("Handled exception: " + e.toString());
		}
	} else {
		Application.launch();
	}
}
}
