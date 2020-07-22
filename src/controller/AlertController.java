package controller;

import javafx.scene.control.Alert;
import view.AlertDisplay;

/**
 * Controller for Alert View
 * 
 * @author sumeet
 *
 */
public class AlertController {
	private AlertController() {
	}

	/**
	 * This emthod returns the Alert for the given message
	 * 
	 * 
	 * @param alertType
	 * @param message
	 * @return
	 */
	public static Alert pushAlerts(String alertType, String message) {
		return AlertDisplay.showAlertBox(alertType, message);
	}
}
