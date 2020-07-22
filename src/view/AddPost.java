package view;

import java.util.regex.Pattern;

import controller.AddPostController;
import controller.AlertController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import model.Exceptions.InvalidInputException;

/**
 * Add Post View
 * 
 * @author sumeet
 *
 */
public class AddPost extends Stage{

	private static Scene scene;
	private static Stage addPostStage;
	
	/**
	 * Generates the view of Add Post
	 * @param post
	 */
	public AddPost(String post) {
		addPostStage = new Stage();
		addPostStage.initModality(Modality.APPLICATION_MODAL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 20, 20));
		
		scene = new Scene(grid, 700, 500);
		addPostStage.setScene(scene);
		addPostStage.centerOnScreen();
		
		VBox details = new VBox();
		details.setSpacing(10);
		details.getChildren().add(getLabel("Title: ","string"));
		details.getChildren().add(getLabel("Description: ", "string"));
		
		if(post.equalsIgnoreCase("Event")) {
			Text title = new Text("New Event");
			title.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
			grid.add(title, 0, 0);
			
			String imagePath = "Images/event.jpg";
			ImageView image = new ImageView(new Image("file:" + imagePath, 300, 300, false, false));
			
			grid.add(image, 0, 1);
			
			details.getChildren().add(getLabel("Venue: ", "string"));
			details.getChildren().add(getLabel("Date: ", "date"));
			details.getChildren().add(getLabel("Capacity: ", "int"));
		}
		else if(post.equalsIgnoreCase("Job")) {
			Text title = new Text("New Job");
			title.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
			grid.add(title, 0, 0);
			
			String imagePath = "Images/job.jpg";
			ImageView image = new ImageView(new Image("file:" + imagePath, 300, 300, false, false));
			grid.add(image, 0, 1);
			
			details.getChildren().add(getLabel("Proposed Price: ", "double"));
		}
		else {
			Text title = new Text("New Sale");
			title.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
			grid.add(title, 0, 0);
			
			String imagePath = "Images/sale.jpg";
			ImageView image = new ImageView(new Image("file:" + imagePath, 300, 300, false, false));
			grid.add(image, 0, 1);
			
			details.getChildren().add(getLabel("Asking Price: ", "double"));
			details.getChildren().add(getLabel("Minimum Raise: ", "double"));
		}
		grid.add(details, 3, 1);
		
		Button submit = new Button("Submit");
		Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
		HBox btn = new HBox(region1, submit, region2);
		btn.setAlignment(Pos.BOTTOM_CENTER);
		grid.add(btn, 3, 2, 10, 1);
		
		submit.setOnAction(AddPostController.submitHandler);
		addPostStage.showAndWait();
		
	}
	
	/**
	 * Creates a label with the given message and value
	 * 
	 * @param message
	 * @param value
	 * @return
	 */
	private GridPane getLabel(String message, String value) {
		GridPane grid = new GridPane();
		Label label = new Label (message);
		label.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 14));
		TextField tf = new TextField();
		tf.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
			if(!newValue) {
				if(value.equalsIgnoreCase("int")) {
					if(!Pattern.matches("^[1-9]\\d*$", tf.getText().trim())) {
						Alert invalidInput = AlertController.pushAlerts("WARNING", new InvalidInputException(" Enter a valid integer!").toString());
						invalidInput.show();
					}
				}
				else if(value.equalsIgnoreCase("date")) {
					if(!Pattern.matches("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$", tf.getText().trim())) {
						Alert invalidInput = AlertController.pushAlerts("WARNING", new InvalidInputException(" Enter a valid date!").toString());
						invalidInput.show();
					}
				}
				else if(value.equalsIgnoreCase("double")) {
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
	
	

}
