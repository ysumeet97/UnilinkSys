package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.DeveloperInfo;

/**
 * Controller class for MenuBar 
 * 
 * @author sumeet
 *
 */
public class MenuBarController {
	private static boolean windowClosed = false;
	
	/**
	 * This method creates a developer info view
	 * 
	 * @param devStage
	 */
	public void devInfoView(Stage devStage) {
		new DeveloperInfo(devStage);
	}

	/**
	 * Event Handler for Developer Info Button
	 */
	public static final EventHandler<MouseEvent> devInfoHandler = new EventHandler<MouseEvent>()  {
		@Override
		public void handle(MouseEvent event) {
			Button close = (Button)event.getSource();
			Stage devStage = (Stage)close.getScene().getWindow();
			devStage.close();
			windowClosed = true;
		}
	};
	
	public boolean isWindowClosed() {
		return windowClosed;
	}
	
}
