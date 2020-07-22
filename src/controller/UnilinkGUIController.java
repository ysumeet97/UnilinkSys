package controller;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.UnilinkGUI;

/**
 * Controller class for UnilinkGUI class
 * 
 * @author sumeet
 *
 */
public class UnilinkGUIController {

	/**
	 * Event Handler for Developer Info link
	 */
	public static EventHandler<ActionEvent> devInfoHandler = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
        	Stage devStage = new Stage();
            MenuBarController menuController = new MenuBarController();
            menuController.devInfoView(devStage);
        }
	};
	
	/**
	 * Event handler for quit link
	 */
	public static EventHandler<ActionEvent> quitHandler = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
			Alert alert = AlertController.pushAlerts("CONFIRMATION", "Do you want to quit UniLink?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				System.exit(1);
			}
		}
	};
	
	/**
	 * Event handler for logout link
	 */
	public static EventHandler<ActionEvent> logoutHandler = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
			ScrollPane scrollPane = UnilinkGUI.getScrollPane();
			BorderPane borderPane= (BorderPane) scrollPane.getParent();
			Stage stage = (Stage) borderPane.getScene().getWindow();
			Alert alert = AlertController.pushAlerts("CONFIRMATION", "Do you wish to Logout?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				stage.close();
				LoginController.loginView(new Stage());
			}
		}
	};
	
}
