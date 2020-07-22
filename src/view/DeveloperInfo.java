package view;

import controller.MenuBarController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Developer Info View
 * @author sumeet
 *
 */
public class DeveloperInfo extends Stage{
	
	/**
	 * Creates the stage for Developer  Info
	 * @param devStage
	 */
	public DeveloperInfo(Stage devStage) {
		devStage.initModality(Modality.APPLICATION_MODAL);
		devStage.setTitle("Developer Information");
		devStage.setX(500);
		devStage.setY(30);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25));
		
		Scene scene = new Scene(grid, 400, 200);
		devStage.setScene(scene);
		
		Label  name = new Label("Student Name: Sumeet Yedula");
		name.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		grid.add(name, 0, 0);
		
		Label  id = new Label("Student ID: S3797892");
		id.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		grid.add(id, 0, 1);
		
		Button close = new Button("Close");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(close);
		grid.add(hbBtn, 0, 2);
		
		close.setOnMouseClicked(MenuBarController.devInfoHandler);
		
		devStage.showAndWait();
		
	}

}
