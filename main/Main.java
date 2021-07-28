package main;

import javafx.application.Application;
import javafx.stage.Stage;
import models.DataEntryModel;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	/**
	 * Stage primarystage is the Mainwindow
	 */
	public static Stage primarystage;
	/**
	 * Class DataEntryModel for computing working day data based on unser inputs
	 */
	public static DataEntryModel dataEntryModel;

	/**
	 * Gets calculation model
	 * 
	 * @return calculation model
	 */
	public static DataEntryModel getCalculationModel() {
		return dataEntryModel;
	}
	

	@Override
	public void start(Stage primaryStage) {
		try {
			Main.dataEntryModel = new DataEntryModel();
			Main.primarystage = primaryStage;
			
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/ui/view/Login.fxml"));
			Scene scene = new Scene(root);

			primaryStage.setTitle("Beste APP");
			primaryStage.centerOnScreen();
			primaryStage.getIcons().add(new Image("/ui/view/AfSIcon.png"));
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
