package view;

import java.util.ArrayList;
import java.util.regex.Pattern;
import controller.AlertController;
import controller.PostDetailsController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Event;
import model.Job;
import model.Post;
import model.Reply;
import model.Sale;
import model.Exceptions.InvalidInputException;

/**
 * Post Details View
 * @author sumeet
 *
 */
public class PostDetails extends Stage{

	private static Stage postStage;
	private static PostDetailsController postDetailsController;
	private static String userId;
	private static boolean windowClosed = false;
	private static BorderPane bp;
	
	/**
	 * Creates the stage for Post Details
	 * @param post
	 * @param userid
	 */
	public PostDetails(Post post, String userid) {
		postStage = new Stage();
		postStage.initModality(Modality.APPLICATION_MODAL);
		postDetailsController = new PostDetailsController();
		userId = userid;
		
		bp = new BorderPane();
		bp.setPadding(new Insets(5));
		bp.setTop(showTitle(post));
		bp.setCenter(showPostDetails(post));
		bp.setBottom(showReplyList(post));
		
		Scene mainScene = new Scene(bp, 1000, 600);
		postStage.setScene(mainScene);

		postStage.show();
	}

	/**
	 * Displays the reply List in TableView
	 * 
	 * @param post
	 * @return
	 */
	private VBox showReplyList(Post post) {
		ArrayList<Reply> reply = Post.getReplies(post.getId());
		TableView<Reply> tableView = new TableView<Reply>();
		if(post instanceof Event) {   
		    TableColumn<Reply, String> responder = new TableColumn<Reply, String>("Users Participating in the Event");
		    responder.setCellValueFactory(new PropertyValueFactory<>("responderId"));
		    responder.setMinWidth(500);
		    responder.setStyle("-fx-alignment: CENTER");
		    tableView.getColumns().add(responder);
		    
		    for(int i = 0; i < reply.size(); i++) {
		    	tableView.getItems().add(reply.get(i));
		    }
		}
		else if(post instanceof Job || post instanceof Sale) {
			TableColumn<Reply, String> responder = new TableColumn<Reply, String>("Responder ID");
		    responder.setCellValueFactory(new PropertyValueFactory<>("responderId"));
		    responder.setStyle("-fx-alignment: CENTER");
		    responder.setMinWidth(150);
		    
		    TableColumn<Reply, Double> offerValue = new TableColumn<Reply, Double>("Offer Amount");
		    offerValue.setCellValueFactory(new PropertyValueFactory<>("value"));
		    offerValue.setStyle("-fx-alignment: CENTER");
		    offerValue.setMinWidth(150);
		    
		    tableView.getColumns().add(responder);
		    tableView.getColumns().add(offerValue);
		    for (int i = reply.size() - 1; i >= 0; i--) {
		    	tableView.getItems().add(reply.get(i));
			}
		}
		
		Text replyTitle = new Text("Reply Details");
		replyTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		
		VBox vbox = new VBox(replyTitle, tableView);
		
		return vbox;
	}

	/**
	 * Displays the Post Details in GridPane
	 * @param post
	 * @return
	 */
	public static GridPane showPostDetails(Post post) {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 20, 20));
		
		String imagePath = post.getImagePath();
		if(imagePath.equalsIgnoreCase("none") || imagePath.isEmpty()) {
			imagePath = "../../Images/No_image_available.jpg";
		}
		ImageView image = new ImageView(new Image("file:" + imagePath, 300, 300, false, false));
		grid.add(image, 0, 0);
		
		Button uploadImage = new Button("Upload Image");
		uploadImage.setOnAction(PostDetailsController.uploadImageHandler(postStage, post));
		HBox hbBtn = new HBox(300);
		hbBtn.getChildren().add(uploadImage);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		grid.add(hbBtn, 0, 1);
		
		VBox details = new VBox();
		details.setSpacing(10);
		
		details.getChildren().add(getLabel("Post ID: ", post.getId(), false, "string"));
		details.getChildren().add(getLabel("Title: ", post.getTitle(), true, "string"));
		details.getChildren().add(getLabel("Description: ", post.getDescription(), true, "string"));
		details.getChildren().add(getLabel("Creator ID: ", post.getCid(), false, "string"));
		details.getChildren().add(getLabel("Status: ", post.getStatus(), false, "string"));
		if(post instanceof Event) {
			details.getChildren().add(getLabel("Venue: ", ((Event) post).getVenue(), true, "string"));
			details.getChildren().add(getLabel("Date: ", ((Event) post).getDate(), true, "date"));
			details.getChildren().add(getLabel("Capacity: ", Integer.toString(((Event) post).getCapacity()), true, "int"));
			details.getChildren().add(getLabel("Attendee Count: ", Integer.toString(((Event) post).getAttcount()), false, "int"));
		}
		else if(post instanceof Job) {
			details.getChildren().add(getLabel("Proposed Price: ", Double.toString(((Job) post).getProprice()), true, "double"));
			details.getChildren().add(getLabel("Lowest Offer: ", Double.toString(((Job) post).getLowoffer()), false, "double"));
		}
		else {
			details.getChildren().add(getLabel("Highest Offer: ", Double.toString(((Sale) post).getHighoffer()), false, "double"));
			details.getChildren().add(getLabel("Minimum Raise: ", Double.toString(((Sale) post).getMinraise()), true, "double"));
			details.getChildren().add(getLabel("Asking Price: ", Double.toString(((Sale) post).getAskprice()), true, "double"));
		}
		grid.add(details, 3, 0);
		
		Button closePost = new Button("Close Post");
		closePost.setOnAction(PostDetailsController.closePostHandler(userId));
		
		Button deletePost = new Button("Delete Post");
		deletePost.setOnAction(PostDetailsController.deletePostHandler(userId));
		
		Button savePost = new Button("Save(after edit)");
		savePost.setOnAction(PostDetailsController.savePostHandler(userId, post));
		
		Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
		HBox btn = new HBox(closePost, region1, deletePost, region2, savePost);
		
		btn.setAlignment(Pos.BOTTOM_CENTER);
		grid.add(btn, 3, 1, 10, 1);
		
		return grid;
	}
	
	/**
	 * Refreshes the content of Post Details View
	 * @param post
	 */
	public static void refreshPostDetails(Post post) {
		bp.setCenter(showPostDetails(post));
	}

	/**
	 * Displays the title pf the Post
	 * 
	 * @param post
	 * @return
	 */
	private HBox showTitle(Post post) {	
		Button backMenu = new Button("Back To Main Window");
		backMenu.setOnAction(PostDetailsController.backMenuhandler());
		
		Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
        
        Text title = new Text(post.getId() + " - " + post.getTitle() + " : Post Details");
        title.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 30));
        
        HBox titleBox = new HBox(backMenu, region1, title, region2);
        
		return titleBox;
	}
	
	/**
	 * Creates a label with given message and Value along with its corresponding TextField
	 * @param message
	 * @param value
	 * @param editable
	 * @param type
	 * @return
	 */
	private static GridPane getLabel(String message, String value, boolean editable, String type) {
		value = postDetailsController.lineBreaker(value);
		GridPane grid = new GridPane();
		Label label = new Label (message);
		label.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 14));
		TextField tf = new TextField();
		tf.setText(value);
		tf.setEditable(editable);
		tf.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
			if(!newValue) {
				if(type.equalsIgnoreCase("int")) {
					if(!Pattern.matches("^[1-9]\\d*$", tf.getText().trim())) {
						Alert invalidInput = AlertController.pushAlerts("WARNING", new InvalidInputException(" Enter a valid integer!").toString());
						invalidInput.show();
					}
				}
				else if(type.equalsIgnoreCase("date")) {
					if(!Pattern.matches("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$", tf.getText().trim())) {
						Alert invalidInput = AlertController.pushAlerts("WARNING", new InvalidInputException(" Enter a valid date!").toString());
						invalidInput.show();
					}
				}
				else if(type.equalsIgnoreCase("double")) {
					if(!Pattern.matches("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$", tf.getText().trim())) {
						Alert invalidInput = AlertController.pushAlerts("WARNING", new InvalidInputException(" Enter a valid amount!").toString());
						invalidInput.show();
					}
				}
			}
        });
		grid.add(label, 0, 0);
		grid.add(tf, 1, 0);
		return grid;
	}
	
	/**
	 * Method to fing whether the window is closed
	 * @return
	 */
	public static boolean isWindowClosed() {
		return windowClosed;
	}

}
