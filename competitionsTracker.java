import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


// Before you run the competitionsTracker, you have to add JavaFx library and write two lines below this line on VM arguments. 
//--module-path {your path of javafx-sdk} --add-modules javafx.controls
//--add-modules=javafx.web

public class competitionsTracker extends Application {
	

	private static ArrayList<Competition> competitions = new ArrayList<Competition>();
	static TableView<Competition> tableViewC;
	static Button back = new Button("back");
	
	//static Button TrackButton = new Button("Track a new competition");

	public static void main(String[] args) throws Exception {
		
		// Just for test the code:
		CompetitionTB competition = new CompetitionTB("https://ultrahack.org/aiot-hackathon-stc","AIoT Hackathon with stc");
		competition.setDate(2021, 12, 20);
		CompetitionSolo competition2 = new CompetitionSolo("https://twitter.com/CyberhubSa","CyberuHub");
		competition2.setDate(2021, 12, 22);
		Team team = new Team("SuperDevops", 1);
		Team team2 = new Team("StackUnderflow", 2);
		team.addStudent(new Student(222243860, "CS", "Bassel Alqahtani"));
		team.addStudent(new Student(222246560, "SWE", "Naif Essam"));
		team2.addStudent(new Student(222219260, "COE", "Majed Ahmad"));
		team2.addStudent(new Student(222267500, "COE", "Saleh Mohammed"));
		competition.addTeam(team);
		competition.addTeam(team2);
		addCompetition(competition);

		competition2.addStudent(new Student(222256561, "CS", "Ahmad Mohammed"));
		competition2.addStudent(new Student(222256560, "EE", "Abdullah Ali"));
		competition2.addStudent(new Student(222279260, "MIS", "Abdulaziz fawwaz"));
		competition2.addStudent(new Student(222256700, "SWE", "Faris Ahmad"));
		addCompetition(competition2);
	
		launch(args);
		
	}

	public static void addCompetition(CompetitionTB competition) {
		competitions.add(competition);
	}
	
	public static void addCompetition(CompetitionSolo competition) {
		competitions.add(competition);
	}
	public ArrayList<Competition> getCompetitions() {
		return competitions;
	}
	
	//JavaFx
//-------------------------------------------------------------------------------------------
	
	// Done, add comments.
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

		Scene scene = showCompetitions();

		back.setPrefSize(90, 20);
		back.setOnAction(e -> arg0.setScene(scene));

    	arg0.setScene(scene);
    	arg0.show();
		
	}
	
	// Faris, add your comments.
	// Write all competitions on excel file. Add all necessary methods (if there is any).
	// You can use start method to read competitions from excel file.
	@Override
    public void stop() {
       // This method will be executed automatically after competitionsTracker shut down.
    }
	
	// Done
	public Scene showCompetitions() {		
    	
        // To create all needed columns that will contain competitions information.
    	TableColumn<Competition, Date> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Competition, Date> column2 = new TableColumn<>("Date");
        column2.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<Competition, String> column3 = new TableColumn<>("Type");
        column3.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<Competition, Date> column4 = new TableColumn<>("Link");
        column4.setCellValueFactory(new PropertyValueFactory<>("link"));
		
        column1.setPrefWidth(200);
        column2.setPrefWidth(150);
        column3.setPrefWidth(150);
        column4.setPrefWidth(285);
 
 
        // Create Table View.
        tableViewC = new TableView<Competition>();

        tableViewC.getColumns().add(column1);
        tableViewC.getColumns().add(column2);
        tableViewC.getColumns().add(column3);
        tableViewC.getColumns().add(column4);
     
        // To add all competitions to the Table View.
        for(Competition competition : competitions)
        	tableViewC.getItems().add(competition);
        

        Button ShowPar = new Button("Show Participants");
        Button browseButton = new Button("Browse Website");
        Button TrackButton = new Button("Track a new competition");
        
        // Set an action when any row has been Clicked.
        tableViewC.setOnMouseClicked((MouseEvent event) -> {
        	
        	// To get the selectedCompetition from table View.
        	Competition selectedCompetition = tableViewC.getSelectionModel().getSelectedItem(); 
        	
        	// Set an action to "Show Participants" Button.
        	if(selectedCompetition instanceof CompetitionSolo)
        		ShowPar.setOnAction(e -> ShowSoloParticipants(e,selectedCompetition));
        	else if(selectedCompetition instanceof CompetitionTB)
        		ShowPar.setOnAction(e -> showTeams(e,selectedCompetition));
        	
        	browseButton.setOnAction(e -> browseWebsite(e,selectedCompetition));
	        		
        });// End of  tableViewC.setOnMouseClicked.
        TrackButton.setOnAction(e -> TrackCompetition(e));
        // Create HBox, add buttons and set all necessary things.
        HBox hbox = new HBox();
        hbox.getChildren().addAll(ShowPar,browseButton,TrackButton);  
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);
        hbox.setPadding(new Insets(10, 10, 10, 10));
	
        // Create Label and set all necessary things.
        Label label = new Label("Competitions Page");
        label.setFont(new Font("Arial", 20));
        label.setPadding(new Insets(10, 10, 10, 10)); 
        
        // Create BorderPane and set all necessary things.
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(label); 
		borderPane.setCenter(tableViewC);
		borderPane.setBottom(hbox);  
		BorderPane.setMargin(tableViewC, new Insets(0, 10, 0, 10));
		// Create a scene and return it to display.
    	Scene scene = new Scene(borderPane,810, 450);
        return scene;
        
	}// End of showCompetitions Method.
	
	// Done 
	public void ShowSoloParticipants(ActionEvent event,Competition competition) {

    	// To create all needed columns that will contain students information.
        TableColumn<Student, Integer> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Student, String> column2 = new TableColumn<>("Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Student, String> column3 = new TableColumn<>("Rank");
        column3.setCellValueFactory(new PropertyValueFactory<>("rank"));
        column3.setCellFactory(TextFieldTableCell.forTableColumn());
        column3.setOnEditCommit((CellEditEvent<Student, String> t) -> 
            ((Student) t.getTableView().getItems().get(t.getTablePosition().getRow())).setRank(t.getNewValue()));
        TableColumn<Student, String> column4 = new TableColumn<>("Major");
        column4.setCellValueFactory(new PropertyValueFactory<>("major"));

        column1.setPrefWidth(200);
        column2.setPrefWidth(200);
        column3.setPrefWidth(200);
        column4.setPrefWidth(188);
        
        // Create Table View, add all columns and set all necessary things.
        TableView<Student> tableView = new TableView<Student>();
    	tableView.setPrefSize(50, 500);
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column3);
        

        // To add all students to the Table View.
        ArrayList<Student> individuals = ((CompetitionSolo) competition).getIndividuals();
		for(int i = 0;i < individuals.size();++i)
			tableView.getItems().add(individuals.get(i));
			
		// Create Text Field to get typed student information from the user, and set all necessary things.
		TextField addName = new TextField();
		addName.setPromptText("Name");
		addName.setMaxWidth(column1.getPrefWidth()-100);
        TextField addId = new TextField();
        addId.setMaxWidth(column2.getPrefWidth()-100);
        addId.setPromptText("ID");
        TextField setRank = new TextField();
        setRank.setMaxWidth(column3.getPrefWidth()-100);
        setRank.setPromptText("Rank");
        TextField addMajor = new TextField();
        addMajor.setMaxWidth(column4.getPrefWidth()-100);
        addMajor.setPromptText("Major");
		
        // Create Label to add names and all Text Fields.
    	Label  IDView = new Label("ID : ", addId);
    	Label  NameView = new Label("Name : ", addName);
    	Label  MajorView = new Label("Major : ", addMajor);
    	Label  RankView = new Label("Rank : ", setRank);
    	
    	// Set all necessary things.
    	IDView.setContentDisplay(ContentDisplay.RIGHT);
    	NameView.setContentDisplay(ContentDisplay.RIGHT);
    	MajorView.setContentDisplay(ContentDisplay.RIGHT);
    	RankView.setContentDisplay(ContentDisplay.RIGHT);
    	IDView.setPadding(new Insets(5, 5, 5, 5));
    	NameView.setPadding(new Insets(5, 5, 5, 5));
    	MajorView.setPadding(new Insets(5, 5, 5, 5));
    	RankView.setPadding(new Insets(5, 5, 5, 5));
    	
    	// Button that will add the student.
        Button addButton = new Button("Add");
        addButton.setPrefSize(90, 20);
        
        // Create HBox, add buttons and set all necessary things.
        HBox hbox = new HBox();
        hbox.getChildren().addAll(IDView,NameView,MajorView,RankView);
        hbox.setAlignment(Pos.CENTER);
        
        // Create BottomBorderPane and set all necessary things.
    	BorderPane BottomBorderPane = new BorderPane();
    	BottomBorderPane.setPadding(new Insets(10, 10, 10, 10));
    	BottomBorderPane.setCenter(hbox);
    	BottomBorderPane.setLeft(back);
    	BottomBorderPane.setRight(addButton);

    	// Button that will prepare email to a student.
        Button prepareEmail = new Button("Prepare email");
        prepareEmail.setPrefSize(90, 20);
        
        Label label = new Label("Participants");
        label.setFont(new Font("Arial", 20));
        
        // Create topBorder and set all necessary things.
        BorderPane topBorder = new BorderPane();
        topBorder.setPadding(new Insets(10, 10, 10, 10));
        topBorder.setLeft(label);
        topBorder.setRight(prepareEmail);
        
        
        // Create primary borderPane and set all necessary things.
		BorderPane borderPane = new BorderPane();
        borderPane.setBottom(BottomBorderPane);
		borderPane.setCenter(tableView);
		borderPane.setTop(topBorder);
		BorderPane.setMargin(tableView, new Insets(0, 10, 0, 10));
		
		
		// Set an action to "Add" Button, and do all necessary things to add the student to the competition.
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	
            	Alert errorAlert = new Alert(AlertType.ERROR);
		      	errorAlert.setHeaderText("Invalid input");
		      	
            	if(!addId.getText().isBlank() && !addMajor.getText().isBlank() && !addName.getText().isBlank() && !setRank.getText().isBlank()) {
            		try {	
	            		Student student =new Student(Integer.parseInt(addId.getText()),addMajor.getText(),addName.getText());
	            		student.setRank(setRank.getText());
	            		((CompetitionSolo) competition).addStudent(student);
	            		addId.clear();
	            		addMajor.clear();
	            		addName.clear();
	            		setRank.clear();	         
	            		tableView.getItems().add(((CompetitionSolo) competition).getIndividuals().get(((CompetitionSolo) competition).getIndividuals().size()-1));
            		 	}
        		    catch(NumberFormatException er) {
        		    	// To raise error if anything went wrong.
        		    	errorAlert.setContentText("ID should be numbers");
        		    	errorAlert.showAndWait();
        		    	}
            		}	
            	else {
            		// To raise error if anything went wrong.
            		errorAlert.setContentText("Fill all information!");
            		errorAlert.showAndWait();
            		}	
            	}
    		}); // End of addButton.setOnAction.
       
        // Set an action when any row has been Clicked.
        tableView.setOnMouseClicked((MouseEvent ev) -> {	
        	// To get the selectedStudent from table View.
        	Student selectedStudent = tableView.getSelectionModel().getSelectedItem(); 
        	
        	//Set an action to prepareEmail.
        	prepareEmail.setOnAction(e -> {
        	CompetitionManger Manger = new CompetitionManger(competition);	        	
        	try {Manger.prepareEmail(selectedStudent);} 
        	catch (Exception e1) {e1.printStackTrace();}
        	});// End of prepareEmail.setOnAction  
        	
        }); // End of tableView.setOnMouseClicked  
        
        // Create a scene and set it on thisStage to display.
    	Scene scene = new Scene(borderPane,810, 450);
    	Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.setScene(scene);
        thisStage.show();

	}// End of ShowSoloParticipants Method.

	// Done, add comments.
	public void TrackCompetition(ActionEvent event) {
		
		TextField competitionName = new TextField();
		TextField competitionLink = new TextField();
		DatePicker datePicker = new DatePicker();	
		ComboBox<String> CompetitionType = new ComboBox<>();
		
		CompetitionType.setPrefSize(100, 20);
		competitionLink.setPrefSize(300, 15);
		competitionName.setPrefSize(300, 15);
		CompetitionType.getItems().addAll("Solo", "Team Basd");
		CompetitionType.setValue(""); 
		
		Label label = new Label("Track a new competition");
        label.setFont(new Font("Arial", 20));
		Label competitionNameLable = new Label("Competition name:", competitionName);
		Label competitionLinkLable = new Label("Competition link:   ", competitionLink);
		Label typeLable = new Label("Competition type:  ", CompetitionType);
		Label dateLable = new Label("Competition date:  ", datePicker);
		
		typeLable.setContentDisplay(ContentDisplay.RIGHT);
		typeLable.setPadding(new Insets(10, 10, 10, 10));
		
		dateLable.setContentDisplay(ContentDisplay.RIGHT);
		dateLable.setPadding(new Insets(10, 10, 10, 10));

		competitionNameLable.setContentDisplay(ContentDisplay.RIGHT);
		competitionNameLable.setPadding(new Insets(10, 10, 10, 10));
		
		competitionLinkLable.setContentDisplay(ContentDisplay.RIGHT);
		competitionLinkLable.setPadding(new Insets(10, 10, 10, 10));
		

		VBox Vbox = new VBox(15);
		Vbox.getChildren().addAll(label,competitionNameLable, competitionLinkLable, typeLable, dateLable);
		Vbox.setPadding(new Insets(20, 10, 10, 20));

		Button Track = new Button("Track");
		Track.setPrefSize(90, 20);

		BorderPane borderPane1 = new BorderPane();
		borderPane1.setPadding(new Insets(0, 20, 20, 20));

		borderPane1.setLeft(back);
		borderPane1.setRight(Track);
		

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(Vbox);
		borderPane.setBottom(borderPane1);
		
		Track.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
        	
        	Alert errorAlert = new Alert(AlertType.ERROR);
	      	errorAlert.setHeaderText("Invalid input");
		      	
		      	
        	if(!competitionName.getText().isBlank() && !competitionLink.getText().isBlank() && !CompetitionType.getValue().isBlank()) {
        		try {
        			
        			int year = datePicker.getValue().getYear();
            		int month =  datePicker.getValue().getMonth().getValue();
            		int day = datePicker.getValue().getDayOfMonth();

        			if(CompetitionType.getValue().equals("Solo")) {   
        				CompetitionSolo competition = new CompetitionSolo(competitionLink.getText(),competitionName.getText());
        				competition.setDate(year, month, day);
        				addCompetition(competition);
        				tableViewC.getItems().add(competition);}
        			else{
        				CompetitionTB competition = new CompetitionTB(competitionLink.getText(),competitionName.getText());
        				competition.setDate(year, month, day);
        				addCompetition(competition);
        				tableViewC.getItems().add(competition);}
        			
        			competitionAdded(e);
        			} 
	        	catch (Exception e1) {
	        		errorAlert.setContentText("Enter a date!!");
            		errorAlert.showAndWait();}
        		
    			competitionName.clear();
				competitionLink.clear();
				datePicker.setValue(null);
				CompetitionType.setValue("");
        	}
        	else {
        		errorAlert.setContentText("Fill all information");
        		errorAlert.showAndWait();
		      } 	
        }});

		Scene scene = new Scene(borderPane,700, 350);
		Node node = (Node) event.getSource();
	    Stage thisStage = (Stage) node.getScene().getWindow();
	    thisStage.setScene(scene);
	    thisStage.show();
    
	}// End of TrackCompetition Method.
	

	// Done, add comments.
	public void competitionAdded(ActionEvent event) {
		
		BorderPane borderPane = new BorderPane();
		Text text = new Text("The competition has been added successfully");
		text.setFont(Font.font(text.getText(), FontWeight.BOLD, FontPosture.REGULAR, 20));

		borderPane.setBottom(back);
		borderPane.setPadding(new Insets(10, 10, 10, 10));
		borderPane.setCenter(text);
		
		Scene scene = new Scene(borderPane,700, 300);
		Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.setScene(scene);
        thisStage.show();
        
	}// End of competitionAdded Method.
	
	//Abdulaziz
	//Done, add comments.
	public void browseWebsite(ActionEvent event,Competition competition) {

		if(competition != null) {
			
			WebView Webview = new WebView();
            WebEngine webEngine = Webview.getEngine();
            webEngine.load(competition.getLink());
			
			BorderPane borderPane = new BorderPane();
			BorderPane borderPane1 = new BorderPane();
			borderPane1.setPadding(new Insets(10, 10, 10, 10));
			borderPane1.setLeft(back);
			borderPane.setCenter(Webview);
			borderPane.setBottom(borderPane1);
			
		   	Scene scene = new Scene(borderPane,Webview.getPrefWidth(), Webview.getPrefHeight());
			Node node = (Node) event.getSource();
		    Stage thisStage = (Stage) node.getScene().getWindow();
		    thisStage.setScene(scene);
		    thisStage.show();
        }
		else {
			Alert errorAlert = new Alert(AlertType.ERROR);
      		errorAlert.setContentText("Select a competition!");
    		errorAlert.showAndWait();
      		}

	}// End of browseWebsite Method.
	
	//Meshal
	// Add your comments.
	public void showTeams(ActionEvent event,Competition competition) {
		// note : add this Button (back). It is static Button.
		// Look at start method I used (backfromShowTeams.setOnAction).
		
		if(competition != null) {
			// To be completed
		
		
			BorderPane borderPane = new BorderPane();
			// Add to (borderPane)
				
		   	Scene scene = new Scene(borderPane,810, 450);
			Node node = (Node) event.getSource();
		    Stage thisStage = (Stage) node.getScene().getWindow();
		    thisStage.setScene(scene);
		    thisStage.show();
        }
	}// End of showTeams Method.
	
	// Naif
	// Add your comments.
	public void showTeamMembers(ActionEvent event,Competition competition, Team team) {
		
		// To be completed
		
		
		// Add this Button to the screen.
		Button back = new Button("back");
		back.setPrefSize(90, 20);
		back.setOnAction(e -> showTeams(e,competition));
		
		BorderPane borderPane = new BorderPane();
		// Add to (borderPane).
		
		Scene scene = new Scene(borderPane,810, 450);
		Node node = (Node) event.getSource();
	    Stage thisStage = (Stage) node.getScene().getWindow();
	    thisStage.setScene(scene);
	    thisStage.show();
	    
	}// End of showTeamMembers Method.
	
	
	
//-------------------------------------------------------------------------------------------	
} // End of competitionsTracker Class
