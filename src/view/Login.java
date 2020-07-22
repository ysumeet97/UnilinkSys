package view;

import controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * @author sumeet
 *
 */
public class Login extends Stage{
	
	/**
	 * Creates a login View stage
	 * @param loginStage
	 */
	public Login(Stage loginStage) {
		
		loginStage.setTitle("UniLink Login");
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25));
		
		Scene scene = new Scene(grid, 300, 300);
		loginStage.setScene(scene);
		loginStage.centerOnScreen();
		
		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
		grid.add(scenetitle, 0, 0, 2, 1);
		
		Label userName = new Label("User Name:");
		grid.add(userName, 0, 3);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 3);
		
		Button loginButton = new Button("Log in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(loginButton);
		grid.add(hbBtn, 1, 4);
		
		loginButton.setOnAction(LoginController.loginHandler);
		
		loginStage.show();
	}

}
