package view;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	public static Stage primarystage;
	
	public static void setPrimarystage(Stage primaryStage) {
		primarystage = primaryStage;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			setPrimarystage(primaryStage);
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Beste APP");
			primaryStage.centerOnScreen();
			primaryStage.getIcons().add(new Image("/view/AfSIcon.png"));
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
