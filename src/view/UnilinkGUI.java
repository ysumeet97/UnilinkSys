package view;

import java.sql.SQLException;
import java.util.ArrayList;
import controller.AddPostController;
import controller.AlertController;
import controller.FileController;
import controller.PostDetailsController;
import controller.UnilinkGUIController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import view.UnilinkGUI;
import model.Event;
import model.Job;
import model.Post;
import model.Reply;
import model.Sale;
import model.UniLink;
import model.Database.CreateTables;
import model.Database.TableOperations;
import model.Exceptions.DatabaseException;

/**
 * Unilink Views
 * @author sumeet
 *
 */
public class UnilinkGUI extends Stage{

	private static String userid;
	private static UniLink ul = new UniLink();
	private static ScrollPane scrollPane;
	private static boolean check = false;
	private static String typeValue = "";
	private static String statusValue = "";
	private static String creatorValue = "";
	private static ArrayList<Post> post;
	private static ArrayList<Post> finalPostList = new ArrayList<Post>();
	private static ArrayList<Reply> finalReplyList = new ArrayList<Reply>();
	
	/**
	 * Constructor
	 * 
	 * @param userId
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public UnilinkGUI(String userId) throws ClassNotFoundException, SQLException, DatabaseException{
		userid = userId;
		CreateTables ct = new CreateTables();
		//ct.deleteTables();
		ct.createNewTables();
		try {
			TableOperations op = new TableOperations();	
			post = op.getFilteredPosts("All","All","All");
			finalPostList = op.getFilteredPosts("All","All","All");
		} catch (SQLException e) {
			Alert alertError = AlertController.pushAlerts("ERROR", e.getMessage());
			alertError.show();
		} 
		UnilinkView();
	}
	
	/**
	 * Method to create the Unilink Stage
	 */
	public static void UnilinkView() {
		
		Stage primaryStage = new Stage();
		primaryStage.setTitle("UniLink");
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(5));
		borderPane.setTop(createTopLayout(primaryStage));
		borderPane.setCenter(createCenterLayout());	
		
		Scene mainScene = new Scene(borderPane, 1070, 600);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	/**
	 * Method to display the centre layout of the Centre Layout of UnilinkGUI
	 * @return
	 */
	private static ScrollPane createCenterLayout() {
		scrollPane = new ScrollPane();
		scrollPane.setMaxHeight(1200);
		scrollPane.setMaxWidth(2160);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setContent(postListLayout(typeValue, statusValue, creatorValue));
		scrollPane.setPannable(true);
		return scrollPane;
	}

	/**
	 * Method to display the Post List Layout of the UnilinkGUI
	 * 
	 * @param typeValue
	 * @param statusValue
	 * @param creatorValue
	 * @return
	 */
	private static VBox postListLayout(String typeValue, String statusValue, String creatorValue) {
		VBox postList = new VBox();
		postList.setSpacing(10);
		postList.setPadding(new Insets(5));
		
		Label htitle = new Label("Welcome " + userid + " !");
		htitle.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 25));
		
		Hyperlink logout = new Hyperlink();
		logout.setText("Logout");
		logout.setFont(Font.font("Tahoma", FontWeight.BOLD,25));
		logout.setBorder(Border.EMPTY);
		logout.setUnderline(false);
		logout.setOnAction(UnilinkGUIController.logoutHandler);
		
		Region region3 = new Region();
        HBox.setHgrow(region3, Priority.ALWAYS);
        Region region4 = new Region();
        HBox.setHgrow(region4, Priority.ALWAYS);
		
        HBox header = new HBox(region4, htitle,region3, logout);		
		postList.getChildren().add(header);
		
		if(post == null || post.isEmpty()) {
			if(check == false) {
				ul.newEvent("Birthday", "Birthday party", "S1", "Images/birthday.jpg", "House", "01/05/2020", 30);
				ul.newEvent("Marriage", "Marriage Ceremony", "S2", "Images/marriage.jpg", "Gardens", "02/05/2020", 100);
				ul.newJob("Painting", "Paint a new House", "S3", "Images/painting.jpg", 15623);
				ul.newJob("Cleaning", "Cleaning a new House", "S4", "Images/cleaning.jpg", 25300);
				ul.newSale("Laptop", "Laptop for Sale", "S5", "Images/laptop.jpg", 25000, 1000);
				ul.newSale("Guitar", "Guitar for Sale", "S6", "Images/guitar.jpg", 15000, 235);
				check = true;
			}
		}
		post = getPost(typeValue, statusValue, creatorValue);

		GridPane postBox;
		
		for(int i = 0; i < post.size(); i++) {
			postBox = new GridPane();
			postBox.setMinSize(970, 150);
			postBox.setMaxHeight(250);
			postBox.setPadding(new Insets(20, 20, 20, 20));
			postBox.setVgap(20);
			postBox.setHgap(20);
			
			String imagePath = post.get(i).getImagePath();
			//imagePath.replace("/", "\\");
			if(imagePath.equalsIgnoreCase("none") || imagePath.isEmpty()) {
				imagePath = "../../Images/No_image_available.jpg";
			}
			ImageView image = new ImageView(new Image("file:" + imagePath, 150, 150, false, false));
			
			Button reply = new Button("Reply");
			reply.setOnAction(PostDetailsController.reply(post.get(i), userid));
			
		    Button moreDetails = new Button("MoreDetails");
		    moreDetails.setOnAction(PostDetailsController.moreDetails(post.get(i), userid));
			
		    postBox.add(image,0, 0, 1, 2);	
			postBox.add(getLabel("Post ID: ", post.get(i).getId()), 1, 0);
			postBox.add(getLabel("Title: ", post.get(i).getTitle()), 1, 1);
			postBox.add(getLabel("Description: ", post.get(i).getDescription()), 2, 0);
			postBox.add(getLabel("Creator ID: ", post.get(i).getCid()), 2, 1);
			postBox.add(getLabel("Status: ", post.get(i).getStatus()), 3, 0);
			if(post.get(i) instanceof Event) {
				postBox.add(getLabel("Venue: ", ((Event) post.get(i)).getVenue()), 3, 1);
				postBox.add(getLabel("Date: ", ((Event) post.get(i)).getDate()), 4, 0);
				postBox.add(getLabel("Capacity: ", Integer.toString(((Event) post.get(i)).getCapacity())), 4, 1);
				postBox.add(getLabel("Attendee Count: ", Integer.toString(((Event) post.get(i)).getAttcount())), 5, 0);
				postBox.add(reply, 6, 1);
				if((post.get(i).getCid().equalsIgnoreCase(userid)))
					postBox.add(moreDetails, 7, 1);
				postBox.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));
			}
			else if(post.get(i) instanceof Job) {
				postBox.add(getLabel("Proposed Price: ", Double.toString(((Job) post.get(i)).getProprice())), 3, 1);
				postBox.add(getLabel("Lowest Offer: ", Double.toString(((Job) post.get(i)).getLowoffer())), 4, 0);
				postBox.add(reply, 5, 1);
				if((post.get(i).getCid().equalsIgnoreCase(userid)))
					postBox.add(moreDetails, 6, 1);
				postBox.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
			}
			else if(post.get(i) instanceof Sale){
				postBox.add(getLabel("Highest Offer: ", Double.toString(((Sale) post.get(i)).getHighoffer())), 3, 1);
				postBox.add(getLabel("Minimum Raise: ", Double.toString(((Sale) post.get(i)).getMinraise())), 4, 0);
				if(post.get(i).getCid().equalsIgnoreCase(userid))
					postBox.add(getLabel("Asking Price: ", Double.toString(((Sale) post.get(i)).getAskprice())), 4, 1, 1, 2);
				postBox.add(reply, 5, 1);
				if((post.get(i).getCid().equalsIgnoreCase(userid)))
					postBox.add(moreDetails, 6, 1);
				postBox.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, CornerRadii.EMPTY, Insets.EMPTY)));
			}
			
			final Separator sepHor = new Separator();
			sepHor.setMaxWidth(Double.MAX_VALUE);
			postList.getChildren().addAll(postBox, sepHor);
		}
	
		return postList;
	}

	/**
	 * Method to display the Top Layout of the UnilinkGUI View
	 * 
	 * @param primaryStage
	 * @return
	 */
	private static VBox createTopLayout(Stage primaryStage) {
		VBox topContainer = new VBox();
		
		MenuBar menuBar = new MenuBar();
	    
        Menu unilink = new Menu("UniLink");
        MenuItem devInfo = new MenuItem("Developer Information");
        devInfo.setOnAction(UnilinkGUIController.devInfoHandler);
        
        
        MenuItem quit = new MenuItem("Quit UniLink");
        quit.setOnAction(UnilinkGUIController.quitHandler);
        
        unilink.getItems().addAll(devInfo, quit);
       
        Menu data = new Menu("Data");
        
        MenuItem exportData = new MenuItem("Export Data");
        exportData.setOnAction(FileController.exportDataHandler(primaryStage));
        
        MenuItem importData = new MenuItem("Import Data");
        importData.setOnAction(FileController.importDataHandler(primaryStage));
        
        data.getItems().addAll	(exportData, importData);
        
        menuBar.getMenus().addAll(unilink, data);
        
        ToolBar toolBar = new ToolBar();
        
        Button newEventPost = getNewPostButton("Event");
        Button newSalePost = getNewPostButton("Sale");
        Button newJobPost = getNewPostButton("Job");
        
        Label type = new Label("Type: ");
        ComboBox<String> typeBox = new ComboBox<String>();
        typeBox.getItems().add("All");
        typeBox.getItems().add("Event");
        typeBox.getItems().add("Sale");
        typeBox.getItems().add("Job");
        typeBox.setValue("All");
        typeValue = typeBox.getValue();
        typeBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                typeValue = newValue;
                refreshContent();
            }
        });
        
        Label status = new Label("Status: ");
        ComboBox<String> statusBox = new ComboBox<String>();
        statusBox.getItems().add("All");
        statusBox.getItems().add("Open");
        statusBox.getItems().add("Closed");
        statusBox.setValue("All");
        statusValue = statusBox.getValue();
        statusBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                statusValue = newValue;
                refreshContent();
            }
        });
        
        
        Label creator = new Label("Creator: ");
        ComboBox<String> creatorBox = new ComboBox<String>();
        creatorBox.getItems().add("All");
        creatorBox.getItems().add("My Posts");
        creatorBox.setValue("All");
        if(creatorBox.getValue().equalsIgnoreCase("All"))
        	creatorValue = creatorBox.getValue();
        else
        	creatorValue = userid;
        creatorBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	if(newValue.equalsIgnoreCase("All"))
            		creatorValue = newValue;
            	else
            		creatorValue = userid;
            refreshContent();
            }
        });
        
        toolBar.getItems().addAll(newEventPost, newSalePost, newJobPost, type, typeBox, status, statusBox, creator, creatorBox);
        
        topContainer.getChildren().add(menuBar);
        topContainer.getChildren().add(toolBar);
		return topContainer;
	}
	
	/**Method to create a New Post Button
	 * @param post
	 * @return
	 */
	private static Button getNewPostButton(String post) {
		Button addPostBtn = new Button("New " + post + "Post");
		AddPostController addPostAction = new AddPostController(userid, post);
		addPostBtn.setOnAction(addPostAction);
		return addPostBtn;
	}
	
	/**
	 * Method to cfreate the Label
	 * @param message
	 * @param value
	 * @return
	 */
	private static Label getLabel(String message, String value) {
		Label label = new Label (message + value);
		label.setAlignment(Pos.BOTTOM_CENTER);
		label.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 14));
		return label;
	}
	
	/**
	 * Method to get the ScrollPane object created in UnilinkGUI
	 * @return
	 */
	public static ScrollPane getScrollPane() {
		return scrollPane;
	}
	
	/**
	 * Method to get the Unilink object
	 * @return
	 */
	public static UniLink getUniLinkObject() {
		return ul;
	}

	/**
	 * Method to get the post based on the Filtered Values
	 * 
	 * @param typeValue
	 * @param statusValue
	 * @param creatorValue
	 * @return
	 */
	public static ArrayList<Post> getPost(String typeValue, String statusValue, String creatorValue) {
		ArrayList<Post> post = null;
		try {
			TableOperations op = new TableOperations();	
			post = op.getFilteredPosts(typeValue,statusValue, creatorValue);
		} catch (SQLException | ClassNotFoundException | DatabaseException e) {
			Alert alertError = AlertController.pushAlerts("ERROR", e.getMessage());
			alertError.show();
		} 
		return post;
	}
	
	/**
	 * Method to refresh the content of Unilink View
	 */
	public static void refreshContent() {
		scrollPane.setContent(postListLayout(UnilinkGUI.typeValue, UnilinkGUI.statusValue, UnilinkGUI.creatorValue));
		finalPostList = getPost("ALL", "ALL", "ALL");
		finalReplyList = Post.getReplies("ALL");
	}
	
	/**
	 * Method to return the Final Post List
	 * @return
	 */
	public static ArrayList<Post> getFinalPostList() {
		return finalPostList;
	}
	
	/**
	 * Method to return the Final reply List
	 * @return
	 */
	public static ArrayList<Reply> getFinalReplyList(){
		return finalReplyList;
	}

}
