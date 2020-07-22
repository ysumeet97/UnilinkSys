package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.UnilinkGUI;
import model.UniLink;
import view.AddPost;

/**
 * Controller class for Add Post View
 * 
 * @author sumeet
 *
 */
public class AddPostController implements EventHandler<ActionEvent>  {

	private String post;
	private static  String userid;
	private static UniLink ul;
	private static boolean check;
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param post
	 */
	public AddPostController(String id, String post) {
		this.post = post;
		userid = id;
		ul = UnilinkGUI.getUniLinkObject();
	}
	@Override
	public void handle(ActionEvent event) {
		new AddPost(post);
	}
	
	/**
	 * Submit button Event Handler
	 */
	public static final EventHandler<ActionEvent> submitHandler = new EventHandler<ActionEvent>()  {
		@Override
		public void handle(ActionEvent event) {
			Button submit = (Button)event.getSource();
			HBox hbox = (HBox) submit.getParent();
			GridPane grid = (GridPane) hbox.getParent();
			VBox vbox = (VBox) grid.getChildren().get(2);
			
			String post = ((Text)grid.getChildren().get(0)).getText();
			String title = ((TextField)getGrid(vbox, 0).getChildren().get(1)).getText();
			String description = ((TextField)getGrid(vbox, 1).getChildren().get(1)).getText();
			if(post.equalsIgnoreCase("New Event")) {
				String imagePath = "../../Images/event.jpg";
				String venue = ((TextField)getGrid(vbox, 2).getChildren().get(1)).getText();
				String date = ((TextField)getGrid(vbox, 3).getChildren().get(1)).getText();
				int capacity = Integer.parseInt(((TextField)getGrid(vbox, 4).getChildren().get(1)).getText());
				check = ul.newEvent(title, description, userid, imagePath, venue, date, capacity);
			}
			else if(post.equalsIgnoreCase("New Job")) {
				String imagePath = "../../Images/job.jpg";
				double proposedPrice = Double.parseDouble(((TextField)getGrid(vbox, 2).getChildren().get(1)).getText());
				check = ul.newJob(title, description, userid, imagePath, proposedPrice);				
			}
			else {
				String imagePath = "../../Images/sale.jpg";
				double askPrice = Double.parseDouble(((TextField)getGrid(vbox, 2).getChildren().get(1)).getText());
				double minRaise = Double.parseDouble(((TextField)getGrid(vbox, 3).getChildren().get(1)).getText());
				check = ul.newSale(title, description, userid, imagePath, askPrice, minRaise);
			}
			if(check) {
				Stage addPostStage = (Stage)grid.getScene().getWindow();
				addPostStage.close();
				Alert postCreated = AlertController.pushAlerts("INFO", post + " added!");
				postCreated.showAndWait();
				if(!postCreated.isShowing()) {
					UnilinkGUI.refreshContent();
				}
				
			}
			
		}
	};
	
	/**
	 * This method returns the GridPane in which the VBox is present
	 * 
	 * @param vbox
	 * @param pos
	 * @return
	 */
	private static GridPane getGrid(VBox vbox, int pos) {
		GridPane grid = (GridPane) vbox.getChildren().get(pos);
		return grid;
	}

}
