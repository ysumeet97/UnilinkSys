package main;

import controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the startup class for the application
 * 
 * @author sumeet
 * @version 1.0
 */
public class Startup extends Application{
	
	/**
	 * Main Method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage loginStage) throws Exception {
		LoginController.loginView(loginStage);
	}

}
