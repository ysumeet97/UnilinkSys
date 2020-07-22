package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import view.ReplyView;
import view.UnilinkGUI;
import model.UniLink;
import model.Exceptions.InvalidOfferPriceException;
import model.Exceptions.InvalidPostOperationException;
import model.Exceptions.PostClosedException;
import model.Exceptions.PostNotFoundException;

/**
 * Controller class for Reply View
 * 
 * @author sumeet
 *
 */
public class ReplyController {
	
	private static UniLink ul = UnilinkGUI.getUniLinkObject();

	/**
	 * Event Handler for submit button
	 */
	public static final EventHandler<ActionEvent> submitHandler = new EventHandler<ActionEvent>()  {
		@Override
		public void handle(ActionEvent event) {
			
			Button submit = (Button)event.getSource();
			HBox hbox = (HBox)submit.getParent();
			GridPane grid = (GridPane)hbox.getParent();
			HBox postDetails = (HBox) grid.getChildren().get(0);
			String postId = ((TextField) postDetails.getChildren().get(1)).getText();
			HBox userDetails = (HBox) grid.getChildren().get(1);
			String userId = ((TextField) userDetails.getChildren().get(1)).getText();
			
			if(!ReplyView.isWindowClosed()) {
					Stage stage = (Stage)submit.getScene().getWindow();
					stage.close();
			}
			
			if(postId.substring(0, 3).equalsIgnoreCase("EVE")) {
				try {
					boolean check = ul.replyPost(postId, 1, userId);
					if(check == true) {
						Alert  alert= AlertController.pushAlerts("INFO", "Your reply has been accepted! You are added to the event " + postId + " !");
						alert.showAndWait();
						if(!alert.isShowing()) {
							UnilinkGUI.refreshContent();
						}
					}
				} catch (PostNotFoundException | InvalidPostOperationException | InvalidOfferPriceException
						| PostClosedException e) {
					Alert exceptionAlert = AlertController.pushAlerts("WARNING", e.toString());
					exceptionAlert.show();
				}
			}
			else if(postId.substring(0, 3).equalsIgnoreCase("JOB")) {
				try {
					HBox amountDetails = (HBox) grid.getChildren().get(2);
					double offerAmount = Double.parseDouble(((TextField) amountDetails.getChildren().get(1)).getText());
					boolean check = ul.replyPost(postId, offerAmount, userId);
					if(check == true) {
						Alert  alert= AlertController.pushAlerts("INFO", "Your reply has been accepted! You are added to the event " + postId + " !");
						alert.showAndWait();
						if(!alert.isShowing()) {
							UnilinkGUI.refreshContent();
						}
					}
				} catch (PostNotFoundException | InvalidPostOperationException | InvalidOfferPriceException
						| PostClosedException e) {
					Alert exceptionAlert = AlertController.pushAlerts("WARNING", e.toString());
					exceptionAlert.show();
				}
			}
			else if(postId.substring(0, 3).equalsIgnoreCase("SAL")) {
				try {
					HBox amountDetails = (HBox) grid.getChildren().get(2);
					double offerAmount = Double.parseDouble(((TextField) amountDetails.getChildren().get(1)).getText());
					boolean check = ul.replyPost(postId, offerAmount, userId);
					if(check == true) {
						Alert  alert= AlertController.pushAlerts("INFO", "Your reply has been accepted! You are added to the event " + postId + " !");
						alert.showAndWait();
						if(!alert.isShowing()) {
							UnilinkGUI.refreshContent();
						}
					}
				} catch (PostNotFoundException | InvalidPostOperationException | InvalidOfferPriceException
						| PostClosedException e) {
					Alert exceptionAlert = AlertController.pushAlerts("WARNING", e.toString());
					exceptionAlert.show();
				}
			}
		}
	};
	
}