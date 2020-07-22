package view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Alert View
 * 
 * @author sumeet
 *
 */
public class AlertDisplay {
	private static Alert alert;

	/**
	 * Initiates the alert and sets the content
	 * 
	 * @param alertType
	 * @param message
	 * @return
	 */
	public static Alert showAlertBox(String alertType, String message) {
		getAlertBox(alertType);
		if (alert != null) {
			alert.setContentText(message);
		}
		return alert;
	}
	
	/**
	 * Creates different type of alert boxes
	 * @param alertType
	 */
	private static void getAlertBox(String alertType) {
		switch (alertType) {
		case "ERROR":
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			break;
		case "CONFIRMATION":
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Screen");
			break;
		case "INFO":
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Info");
			break;
		case "WARNING":
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			break;
		}
	}
}
