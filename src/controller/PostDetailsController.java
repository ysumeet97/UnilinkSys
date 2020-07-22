package controller;

import java.io.File;
import java.util.StringTokenizer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.UnilinkGUI;
import model.Event;
import model.Job;
import model.Post;
import model.Sale;
import model.UniLink;
import model.Exceptions.InvalidPostOperationException;
import model.Exceptions.PostNotFoundException;
import view.PostDetails;
import view.ReplyView;

/**
 * Controller class for Post Details View
 * 
 * @author sumeet
 *
 */
public class PostDetailsController {
	
	private static UniLink ul = UnilinkGUI.getUniLinkObject();
	
	/**
	 * Method to handle the event caused by moreDetails Button
	 * 
	 * @param post
	 * @param userId
	 * @return
	 */
	public static EventHandler<ActionEvent> moreDetails(Post post, String userId) {
		return new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				new PostDetails(post, userId);
			}
		};
	}
	
	/**
	 * Method to handle the event caused by the reply button
	 * 
	 * @param post
	 * @param userid
	 * @return
	 */
	public static EventHandler<ActionEvent> reply(Post post, String userid){
		return new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				new ReplyView(post, userid);
			}
		};
	}
	
	/**
	 * Method to handle the event caused by the closePost Button
	 * 
	 * @param userId
	 * @return
	 */
	public static EventHandler<ActionEvent> closePostHandler(String userId){
		return new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				Button button = (Button)event.getSource();
				HBox hbox = (HBox)button.getParent();
				GridPane grid = (GridPane)hbox.getParent();
				VBox vbox = (VBox)grid.getChildren().get(2);
				
				GridPane postDetails = (GridPane)vbox.getChildren().get(0);
				String postId = ((TextField) postDetails.getChildren().get(1)).getText().trim();
			
				try {
					boolean check = ul.closePost(postId, userId);
					if(check == true) {
						Alert  alert= AlertController.pushAlerts("INFO", "The post " + postId + " has been closed!");
						alert.show();
					}
				} catch (PostNotFoundException | InvalidPostOperationException e) {
					Alert exceptionAlert = AlertController.pushAlerts("WARNING", e.toString());
					exceptionAlert.show();
				}
			}
		};
	}
	
	/**
	 * Method to handle the event caused by the deletePost button
	 * 
	 * @param userId
	 * @return
	 */
	public static EventHandler<ActionEvent> deletePostHandler(String userId){
		return new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				Button button = (Button)event.getSource();
				HBox hbox = (HBox)button.getParent();
				GridPane grid = (GridPane)hbox.getParent();
				VBox vbox = (VBox)grid.getChildren().get(2);
				
				GridPane postDetails = (GridPane)vbox.getChildren().get(0);
				String postId = ((TextField) postDetails.getChildren().get(1)).getText().trim();
				
				try {
					boolean check = ul.deletePost(postId, userId);
					if(check == true) {
						Alert  alert= AlertController.pushAlerts("INFO", "The post " + postId + " has been deleted!");
						alert.show();
					}
				} catch (PostNotFoundException | InvalidPostOperationException e) {
					Alert exceptionAlert = AlertController.pushAlerts("WARNING", e.toString());
					exceptionAlert.show();
				}
			}
		};
	}
	
	/**
	 * Method to handle the event caused by the backMenu button
	 * 
	 * @return
	 */
	public static EventHandler<ActionEvent> backMenuhandler(){
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Button back = (Button)event.getSource();
				Stage postDetails = (Stage) back.getScene().getWindow();
				postDetails.close();
				UnilinkGUI.refreshContent();
			}
		};
	}
	
	
	/**
	 * Method to handle the event caused by the savePost Button
	 * 
	 * @param userId
	 * @param post
	 * @return
	 */
	public static EventHandler<ActionEvent> savePostHandler(String userId, Post post){
		return new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				Button save = (Button)event.getSource();
				HBox hbox = (HBox)save.getParent();
				GridPane grid = (GridPane)hbox.getParent();
				VBox vbox = (VBox)grid.getChildren().get(2);
				
				GridPane postDetails = (GridPane)vbox.getChildren().get(0);
				String postId = ((TextField) postDetails.getChildren().get(1)).getText().trim();
				
				GridPane titleDetails = (GridPane)vbox.getChildren().get(1);
				String title = ((TextField) titleDetails.getChildren().get(1)).getText().trim();
				
				GridPane descDetails = (GridPane)vbox.getChildren().get(2);
				String desc = ((TextField) descDetails.getChildren().get(1)).getText().trim();
				
				if(post instanceof Event) {
					GridPane venueDetails = (GridPane)vbox.getChildren().get(5);
					String venue = ((TextField) venueDetails.getChildren().get(1)).getText().trim();
					
					GridPane dateDetails = (GridPane)vbox.getChildren().get(6);
					String date = ((TextField) dateDetails.getChildren().get(1)).getText().trim();
					
					GridPane capDetails = (GridPane)vbox.getChildren().get(7);
					int capacity = Integer.parseInt(((TextField) capDetails.getChildren().get(1)).getText().trim());
					
					boolean check = ul.updateEventDetails(postId, title, desc, venue, date, capacity);
					if(check == true) {
						Alert  alert= AlertController.pushAlerts("INFO", "The post " + postId + " details have been saved");
						alert.showAndWait();
						if(!alert.isShowing()) {
							PostDetails.refreshPostDetails(post);
						}
					}
				}else if(post instanceof Job) {
					GridPane propriceDetails = (GridPane)vbox.getChildren().get(5);
					double proprice = Double.parseDouble(((TextField) propriceDetails.getChildren().get(1)).getText().trim());
					
					boolean check = ul.updateJobDetails(postId, title, desc, proprice);
					if(check == true) {
						Alert  alert= AlertController.pushAlerts("INFO", "The post " + postId + " details have been saved");
						alert.showAndWait();
						if(!alert.isShowing()) {
							PostDetails.refreshPostDetails(post);
						}
					}
				}else if(post instanceof Sale) {
					GridPane askpriceDetails = (GridPane)vbox.getChildren().get(7);
					double askprice = Double.parseDouble(((TextField) askpriceDetails.getChildren().get(1)).getText().trim());
					
					GridPane minraiseDetails = (GridPane)vbox.getChildren().get(6);
					double minraise = Double.parseDouble(((TextField) minraiseDetails.getChildren().get(1)).getText().trim());
					boolean check = ul.updateSaleDetails(postId, title, desc, askprice, minraise);
					if(check == true) {
						Alert  alert= AlertController.pushAlerts("INFO", "The post " + postId + " details have been saved");
						alert.showAndWait();
						if(!alert.isShowing()) {
							PostDetails.refreshPostDetails(post);
						}
					}
				}
			}
		};
	}
	
	/**
	 * Method to handle the event caused b the uploadImage button
	 * 
	 * @param stage
	 * @param post
	 * @return
	 */
	public static EventHandler<ActionEvent> uploadImageHandler(Stage stage, Post post){
		return new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setInitialDirectory(new File("Images"));
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
				fileChooser.getExtensionFilters().add(extFilter);
				File selectedDirectory = fileChooser.showOpenDialog(stage);
				if (selectedDirectory != null && selectedDirectory.exists()) {
					String cwd = System.getProperty("user.dir");
					String path = new File(cwd).toURI().relativize(selectedDirectory.toURI()).getPath();
					if (path != null && !path.isEmpty()) {
						Alert alert = AlertController.pushAlerts("INFO", "Image upload successfully");
						UniLink ul = UnilinkGUI.getUniLinkObject();
						ul.setImagePath(post.getId(), path);
						post.setImagePath(path);
						alert.showAndWait();
						if(!alert.isShowing()) {
							PostDetails.refreshPostDetails(post);							
						}
						
					} else {
						Alert alert = AlertController.pushAlerts("ERROR", "FILE_IMPORT_FAILED_INV_FILE");
						alert.show();
					}
				}
			}
		};
	}

	/**
	 * Method to break the given string into multiple lines
	 * 
	 * @param str
	 * @return
	 */
	public String lineBreaker(String str) {
	    StringTokenizer tok = new StringTokenizer(str, " ");
	    StringBuilder output = new StringBuilder(str.length());
	    int lineLen = 0;
	    while (tok.hasMoreTokens()) {
	        String word = tok.nextToken();

	        if (lineLen + word.length() > 40) {
	            output.append("\n" + "                  ");
	            lineLen = 0;
	        }
	        output.append(word + " ");
	        lineLen += word.length();
	    }
	    return output.toString();
	}
}
