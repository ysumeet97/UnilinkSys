package view;

import controller.ReplyController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Event;
import model.Job;
import model.Post;
import model.Sale;

/**
 * Reply View
 * @author sumeet
 *
 */
public class ReplyView extends Stage{
	
	private static Scene scene;
	private static Stage replyStage;
	private static boolean windowClosed = false;
	
	/**
	 * Displays the Reply view
	 * @param post
	 * @param userid
	 */
	public ReplyView(Post post, String userid) {
		
		replyStage = new Stage();
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25));
		
		scene = new Scene(grid, 300, 300);
		replyStage.setScene(scene);
		replyStage.initModality(Modality.APPLICATION_MODAL);
		replyStage.centerOnScreen();
		
		HBox hbox1 = new HBox();
		HBox hbox2 = new HBox();
		
		Label postId = new Label("Post ID: ");
		postId.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 14));
		TextField postIdValue = new TextField();
		postIdValue.setText(post.getId());
		postIdValue.setEditable(false);
		hbox1.getChildren().addAll(postId, postIdValue);
		
		Label responderId = new Label("Your ID: ");
		responderId.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 14));
		TextField responderIdValue = new TextField();
		responderIdValue.setText(userid);
		responderIdValue.setEditable(false);
		hbox2.getChildren().addAll(responderId, responderIdValue);
		
		grid.add(hbox1, 0, 0);
		grid.add(hbox2, 0, 1);
		
		if(post instanceof Event) {
			HBox hbox3 = new HBox();
			
			Button submit = new Button("Submit");
			submit.setOnAction(ReplyController.submitHandler);
			
			Region region1 = new Region();
	        HBox.setHgrow(region1, Priority.ALWAYS);
	        
	        Region region2 = new Region();
	        HBox.setHgrow(region2, Priority.ALWAYS);
	        
			hbox3.getChildren().addAll(region1, submit, region2);
			
			grid.add(hbox3, 0, 2);
			
		}
		else if(post instanceof Job || post instanceof Sale) {
			HBox hbox3 = new HBox();
			HBox hbox4 = new HBox();
			
			Label offerAmount = new Label("Offer Amount: ");
			offerAmount.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 14));
			TextField offerAmountValue = new TextField();
			hbox3.getChildren().addAll(offerAmount, offerAmountValue);
			
			Button submit = new Button("Submit");
			submit.setOnAction(ReplyController.submitHandler);
			
			Region region1 = new Region();
	        HBox.setHgrow(region1, Priority.ALWAYS);
	        
	        Region region2 = new Region();
	        HBox.setHgrow(region2, Priority.ALWAYS);
	        
			hbox4.getChildren().addAll(region1, submit, region2);
			
			grid.add(hbox3, 0, 2);
			grid.add(hbox4, 0, 3);
		}
		replyStage.showAndWait();
	}
	
	/**
	 * Method to fing whether the window is closed
	 * @return
	 */
	public static boolean isWindowClosed() {
		return windowClosed;
	}
	
}
