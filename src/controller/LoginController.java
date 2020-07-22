package controller;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Exceptions.DatabaseException;
import view.Login;
import view.UnilinkGUI;

/**
 * Controller class for Login View
 * 
 * @author sumeet
 *
 */
public class LoginController {
	
	/**
	 * Constructor
	 * 
	 * @param loginStage
	 */
	public static void loginView(Stage loginStage) {
		new Login(loginStage);
	}
	
	/**
	 * Event Handler for Login Button
	 */
	public static final EventHandler<ActionEvent> loginHandler = new EventHandler<ActionEvent>()  {
		@Override
		public void handle(ActionEvent event) {
			Button login = (Button)event.getSource();
			HBox hbox = (HBox)login.getParent();
			GridPane grid = (GridPane)hbox.getParent();
			String username = ((TextField)grid.getChildren().get(2)).getText().toUpperCase();
			boolean check = checkUsername(username);
			if(check) {
				Stage loginStage = (Stage)login.getScene().getWindow();
				loginStage.close();
				try {
					new UnilinkGUI(username);
				} catch (ClassNotFoundException | SQLException | DatabaseException e) {
					Alert exceptionAlert = AlertController.pushAlerts("ERROR", e.toString());
					exceptionAlert.show();
				}
			}
		}
	};
	
	/**
	 * This method checks whether the Username is present or not
	 * 
	 * @param username
	 * @return
	 */
	private static boolean checkUsername(String username) {
		if(!username.matches("[s|S][1-9]\\d*") || username == null || username.isEmpty()) {
			Alert invalidUsernameAlert = AlertController.pushAlerts("WARNING", "Invalid User Name!");
			invalidUsernameAlert.show();
			return false;
		}
		else
			return true;
	}
	
}
