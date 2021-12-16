import java.time.LocalDate;
import java.util.ArrayList;
import javafx.application.Application;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


// Before you run the competitionsTracker, you have to add JavaFx library and write three lines below this line on VM arguments. 
//--module-path {your path of javafx-sdk} --add-modules javafx.controls
//--add-modules=javafx.web
//-Dcom.sun.webkit.useHTTP2Loader=false

public class competitionsTracker extends Application {
	

	private static ArrayList<Competition> competitions = new ArrayList<Competition>();
	static TableView<Competition> tableViewC;
	static Button back = new Button("back");
	
	//static Button TrackButton = new Button("Track a new competition");

	public static void main(String[] args) throws Exception {
		
		// Just for test the code:
		CompetitionTB competition = new CompetitionTB("https://ultrahack.org/aiot-hackathon-stc","AIoT Hackathon with stc");
		competition.setDate(2021, 12, 10);
		CompetitionSolo competition2 = new CompetitionSolo("https://cyberhub.sa","CyberuHub");
		competition2.setDate(2021, 12, 10);
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

		arg0.setTitle("Competitions Tracker");
    	arg0.setScene(scene);
    	arg0.show();
    	showNotification();
    	
		
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
    	TableColumn<Competition, String> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Competition, LocalDate> column2 = new TableColumn<>("Date");
        column2.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        TableColumn<Competition, String> column3 = new TableColumn<>("Type");
        column3.setCellValueFactory(new PropertyValueFactory<>("type"));
        
        TableColumn<Competition, String> column4 = new TableColumn<>("Status");
        column4.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        TableColumn<Competition, String> column5 = new TableColumn<>("Link");
        column5.setCellValueFactory(new PropertyValueFactory<>("link"));
		
        column1.setPrefWidth(150);
        column2.setPrefWidth(150);
        column3.setPrefWidth(100);
        column4.setPrefWidth(100);
        column5.setPrefWidth(288);
 
 
        // Create Table View.
        tableViewC = new TableView<Competition>();

        tableViewC.getColumns().add(column1);
        tableViewC.getColumns().add(column2);
        tableViewC.getColumns().add(column3);
        tableViewC.getColumns().add(column4);
        tableViewC.getColumns().add(column5);
     
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
        	
        	browseButton.setOnAction(e -> browseWebsite(selectedCompetition));
	        		
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
        column3.setEditable(true);
        
        TableColumn<Student, String> column4 = new TableColumn<>("Major");
        column4.setCellValueFactory(new PropertyValueFactory<>("major"));

        column1.setPrefWidth(200);
        column2.setPrefWidth(200);
        column3.setPrefWidth(200);
        column4.setPrefWidth(187);
        
        // Create Table View, add all columns and set all necessary things.
        TableView<Student> tableView = new TableView<Student>();
    	tableView.setPrefSize(50, 500);
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column3);
        tableView.setEditable(true);
        

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
		      	String ContentText = "";
            	if(!addId.getText().isBlank() && !addMajor.getText().isBlank() && !addName.getText().isBlank() && !setRank.getText().isBlank()) {
            		
            		if(addName.getText().matches("[a-zA-Z]+")  && addMajor.getText().matches("[a-zA-Z]+") && addId.getText().matches(".*[0-9].*")  && (setRank.getText().matches(".*[0-9].*") || setRank.getText().equals("-"))) 
            		{
	            		Student student =new Student(Integer.parseInt(addId.getText()),addMajor.getText(),addName.getText());
	            		student.setRank(setRank.getText());
	            		((CompetitionSolo) competition).addStudent(student);	         
	            		tableView.getItems().add(((CompetitionSolo) competition).getIndividuals().get(((CompetitionSolo) competition).getIndividuals().size()-1));
            		}
            		// if name is not a string or rank is a string
					else {
						if(!addId.getText().matches(".*[0-9].*"))
	        		    	ContentText = ContentText + "\nID should be numbers.";
						
						if(!addName.getText().matches("[a-zA-Z]+"))
							ContentText = ContentText + "\nName should be string.";
						
						if(!addMajor.getText().matches("[a-zA-Z]+"))
							ContentText = ContentText + "\nMajor should be string.";
							
						
						if(!setRank.getText().matches(".*[0-9].*") || !setRank.getText().equals("-"))
							ContentText = ContentText + "\nRank should be numbers \nor (-) if the competition is not over due.";	
					}		
    			}	
            	else {
            		// To raise error if anything went wrong.
            		errorAlert.setContentText("Fill all information!");
            		errorAlert.showAndWait();
            		}	

            	if(!ContentText.isEmpty()) {
            		errorAlert.setContentText(ContentText);
					errorAlert.showAndWait();}
            	
            	addId.clear();
        		addMajor.clear();
        		addName.clear();
        		setRank.clear();
            }
            
    		}); // End of addButton.setOnAction.
       
        // Set an action when any row has been Clicked.
        tableView.setOnMouseClicked((MouseEvent ev) -> {	
        	// To get the selectedStudent from table View.
        	Student selectedStudent = tableView.getSelectionModel().getSelectedItem(); 
        	
        	//Set an action to prepareEmail.
        	prepareEmail.setOnAction(e -> {
        	CompetitionManger Manger = new CompetitionManger(competition);	        	
        	try {
				if(selectedStudent != null)
					Manger.prepareEmail(selectedStudent);
				else
					throw new Exception();
			} catch (Exception e1) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Error");
				errorAlert.setContentText("Select a student!");
				errorAlert.showAndWait();}
        	});// End of prepareEmail.setOnAction  
        	
        }); // End of tableView.setOnMouseClicked  
        
        // Create a scene and set it on thisStage to display.
    	Scene scene = new Scene(borderPane,810, 450);
    	Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.setScene(scene);
        thisStage.show();

	}// End of ShowSoloParticipants Method.


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
		
		BorderPane.setMargin(Vbox, new Insets(0, 10, 0, 10));
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
	
	public void browseWebsite(Competition competition) {

		if(competition != null) {
			
			WebView Webview = new WebView();
            WebEngine webEngine = Webview.getEngine();
            webEngine.load(competition.getLink());

		   	Scene scene = new Scene(Webview,Webview.getPrefWidth(), Webview.getPrefHeight());
		   	Stage stage = new Stage();
		   	stage.setTitle("KFUPM News Team Browser");
		    stage.setScene(scene);
		    stage.show();
        }
		else {
			Alert errorAlert = new Alert(AlertType.ERROR);
      		errorAlert.setContentText("Select a competition!");
    		errorAlert.showAndWait();
      		}

	}// End of browseWebsite Method.
	
	public void showTeams(ActionEvent event, Competition competition) {

		if (competition != null) {
			TableColumn<Team, Integer> column1 = new TableColumn<>("Team Number");
			column1.setCellValueFactory(new PropertyValueFactory<>("teamNumber"));
			TableColumn<Team, String> column2 = new TableColumn<>("Team Name");
			column2.setCellValueFactory(new PropertyValueFactory<>("teamName"));
			TableColumn<Team, String> column3 = new TableColumn<>("Rank");
			column3.setCellValueFactory(new PropertyValueFactory<>("rank"));
			column3.setCellFactory(TextFieldTableCell.forTableColumn());
			column3.setOnEditCommit((CellEditEvent<Team, String> t) -> ((Team) t.getTableView().getItems()
					.get(t.getTablePosition().getRow())).setRank(t.getNewValue()));
			column3.setEditable(true);

			column1.setPrefWidth(200);
			column2.setPrefWidth(200);
			column3.setPrefWidth(200);

			// Create Table View, add all columns and set all necessary things.
			TableView<Team> tableView = new TableView<Team>();
			tableView.setPrefSize(50, 500);
			tableView.getColumns().add(column1);
			tableView.getColumns().add(column2);
			tableView.getColumns().add(column3);
			tableView.setEditable(true);

			// To add all students to the Table View.
			ArrayList<Team> teams = ((CompetitionTB) competition).getTeams();
			for (int i = 0; i < teams.size(); ++i)
				tableView.getItems().add(teams.get(i));

			// Create Text Field to get typed team information from the user, and set all
			// necessary things.
			TextField addName = new TextField();
			addName.setPromptText("Name");
			addName.setMaxWidth(column1.getPrefWidth() - 100);
			TextField setRank = new TextField();
			setRank.setMaxWidth(column3.getPrefWidth() - 100);
			setRank.setPromptText("Rank");

			// Create Label to add names and all Text Fields.
			Label NameView = new Label("Name : ", addName);
			Label RankView = new Label("Rank : ", setRank);

			// Set all necessary things.
			NameView.setContentDisplay(ContentDisplay.RIGHT);

			RankView.setContentDisplay(ContentDisplay.RIGHT);
			NameView.setPadding(new Insets(5, 5, 5, 5));

			RankView.setPadding(new Insets(5, 5, 5, 5));

			// Button that will add the student.
			Button addButton = new Button("Add");
			addButton.setPrefSize(90, 20);

			// Create HBox, add buttons and set all necessary things.
			HBox hbox = new HBox();
			hbox.getChildren().addAll(NameView, RankView);
			hbox.setAlignment(Pos.CENTER);

			// Create BottomBorderPane and set all necessary things.
			BorderPane BottomBorderPane = new BorderPane();
			BottomBorderPane.setPadding(new Insets(10, 10, 10, 10));
			BottomBorderPane.setCenter(hbox);
			BottomBorderPane.setLeft(back);
			BottomBorderPane.setRight(addButton);

			// Button that will prepare email to a student.
			Button showMembers = new Button("Show Members");
			Button prepareEmail = new Button("Prepare Email");
			showMembers.setFont(new Font("Arial", 10));
			prepareEmail.setFont(new Font("Arial", 10));
			showMembers.setPrefSize(90, 20);
			prepareEmail.setPrefSize(90, 20);

			Label label = new Label("Teams");
			label.setFont(new Font("Arial", 20));
			
			HBox hb = new HBox();
			hb.setSpacing(5);
			hb.getChildren().addAll(showMembers, prepareEmail);
			// Create topBorder and set all necessary things.
			BorderPane topBorder = new BorderPane();
			
			topBorder.setPadding(new Insets(10, 10, 10, 10));
			topBorder.setLeft(label);
			topBorder.setRight(hb);

			// Create primary borderPane and set all necessary things.
			BorderPane borderPane = new BorderPane();
			borderPane.setBottom(BottomBorderPane);
			borderPane.setCenter(tableView);
			borderPane.setTop(topBorder);
			BorderPane.setMargin(tableView, new Insets(0, 10, 0, 10));

			// Set an action to "Add" Button, and do all necessary things to add a team to the competition.
			addButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					// setting Alert class for raised errors
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setHeaderText("Invalid input");

					// if text fields are not empty
					if (!addName.getText().isBlank() && !setRank.getText().isBlank()) {
						// if name is a string and rank is not a string
						if (addName.getText().matches("[a-zA-Z]+") && !setRank.getText().matches("[a-zA-Z]+")) {
							try {
								Team team = new Team(addName.getText(), tableView.getItems().size() + 1);
								team.setRank(setRank.getText());
								((CompetitionTB) competition).addTeam(team);
								
								tableView.getItems().add(((CompetitionTB) competition).getTeams()
										.get(((CompetitionTB) competition).getTeams().size() - 1));
							} catch (NumberFormatException er) {
								// To raise error if anything went wrong or id is not a number.
								errorAlert.setContentText("Team number should be numbers");
								errorAlert.showAndWait();
							}
						}
						// if name is not a string or rank is a string
						else {
							if(setRank.getText().matches("[a-zA-Z]+"))
								errorAlert.setContentText("Rank should be numbers or (-) if the competition is not over due.");
							
							if (!addName.getText().matches("[a-zA-Z]+"))
								errorAlert.setContentText("Name should be string");
							errorAlert.showAndWait();
						}
					}
					// if text fields are empty
					else {
						errorAlert.setContentText("Fill all information!");
						errorAlert.showAndWait();
					}
					//addTeamNumber.clear();
					addName.clear();
					setRank.clear();
				}
			});
			
			// Set an action when any row has been Clicked.
			tableView.setOnMouseClicked((MouseEvent ev) -> {
				// To get the selectedStudent from table View.
				Team selectedTeam = tableView.getSelectionModel().getSelectedItem();

				// Set an action to prepareEmail.
				prepareEmail.setOnAction(e -> {
					CompetitionManger Manger = new CompetitionManger(competition);
					try {
						if(selectedTeam != null)
							Manger.prepareEmail(selectedTeam);
						else
							throw new Exception();
					} catch (Exception e1) {
						Alert errorAlert = new Alert(AlertType.ERROR);
						errorAlert.setHeaderText("Error");
						errorAlert.setContentText("Select a team!");
						errorAlert.showAndWait();
					}
				});// End of prepareEmail.setOnAction
				showMembers.setOnAction(e -> {
					try {
						if(selectedTeam != null)
							showTeamMembers(e, competition, selectedTeam);
						else
							throw new Exception();
					} catch (Exception e1) {
						Alert errorAlert = new Alert(AlertType.ERROR);
						errorAlert.setHeaderText("Error");
						errorAlert.setContentText("Select a team!");
						errorAlert.showAndWait();
					}
				});
				
			}); // End of tableView.setOnMouseClicked
			
			

			// Create a scene and set it on thisStage to display.
			Scene scene = new Scene(borderPane, 810, 450);
			Node node = (Node) event.getSource();
			Stage thisStage = (Stage) node.getScene().getWindow();
			thisStage.setScene(scene);
			thisStage.show();
		}

	}// End of showTeams Method.
	
	public void showTeamMembers(ActionEvent event,Competition competition, Team team) {
		
		TableColumn<Student, Integer> column1 = new TableColumn<>("ID");
		column1.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Student, String> column2 = new TableColumn<>("Name");
		column2.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Student, String> column3 = new TableColumn<>("Major");
		column3.setCellValueFactory(new PropertyValueFactory<>("major"));

		// setting width of columns
		column1.setPrefWidth(200);
		column2.setPrefWidth(200);
		column3.setPrefWidth(187);

		// Table View contains all created columns
		TableView<Student> tableView = new TableView<Student>();
		tableView.setPrefSize(50, 500);
		tableView.getColumns().add(column1);
		tableView.getColumns().add(column2);
		tableView.getColumns().add(column3);
		tableView.setEditable(true);

		// ArrayList contains all members which will be added them to table view
		ArrayList<Student> members = team.getMembers();

		for (int i = 0; i < members.size(); ++i)
			tableView.getItems().add(members.get(i));

		// Text Fields for adding a new member
		TextField addName = new TextField();
		addName.setPromptText("Name");
		addName.setMaxWidth(column1.getPrefWidth() - 100);
		TextField addId = new TextField();
		addId.setMaxWidth(column2.getPrefWidth() - 100);
		addId.setPromptText("ID");
		TextField addMajor = new TextField();
		addMajor.setMaxWidth(column3.getPrefWidth() - 100);
		addMajor.setPromptText("Major");

		// Labels of adding member text fields
		Label IDView = new Label("ID : ", addId);
		Label NameView = new Label("Name : ", addName);
		Label MajorView = new Label("Major : ", addMajor);

		IDView.setContentDisplay(ContentDisplay.RIGHT);
		NameView.setContentDisplay(ContentDisplay.RIGHT);

		MajorView.setContentDisplay(ContentDisplay.RIGHT);
		IDView.setPadding(new Insets(5, 5, 5, 5));
		NameView.setPadding(new Insets(5, 5, 5, 5));
		MajorView.setPadding(new Insets(5, 5, 5, 5));

		// Button for adding a new member
		Button addButton = new Button("Add");
		addButton.setPrefSize(90, 20);

		// handling actions on adding button
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// setting Alert class for raised errors
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Invalid input");

				// if text fields are not empty
				if (!addId.getText().isBlank() && !addName.getText().isBlank() && !addMajor.getText().isBlank()) {
					// if name is a string
					if (addName.getText().matches("[a-zA-Z]+")) {
						try {
							Student student = new Student(Integer.parseInt(addId.getText()), addMajor.getText(),
									addName.getText());
							team.addStudent(student);
							tableView.getItems().add(team.getMembers().get(team.getMembers().size() - 1));
						} catch (NumberFormatException er) {
							// To raise error if anything went wrong or id is not a number.
							errorAlert.setContentText("ID should be numbers");
							errorAlert.showAndWait();
						}
					}
					// if name is not a string
					else {
						errorAlert.setContentText("Name should be string");
						errorAlert.showAndWait();
					}
				}
				// if text fields are empty
				else {
					errorAlert.setContentText("Fill all information!");
					errorAlert.showAndWait();
				}
				addId.clear();
				addName.clear();
				addMajor.clear();
			}
		});

		// HBox contains labels of adding member text fields
		HBox hbox = new HBox();
		hbox.getChildren().addAll(IDView, NameView, MajorView);
		hbox.setAlignment(Pos.CENTER);

		// back button to go back the previous page
		Button back = new Button("back");
		back.setPrefSize(90, 20);
		back.setOnAction(e -> showTeams(e, competition));

		// BorderPane contains HBox and back and add buttons
		BorderPane BottomBorderPane = new BorderPane();
		BottomBorderPane.setPadding(new Insets(10, 10, 10, 10));
		BottomBorderPane.setCenter(hbox);
		BottomBorderPane.setLeft(back);
		BottomBorderPane.setRight(addButton);

		// Page title
		Label label = new Label("Members");
		label.setFont(new Font("Arial", 20));

		// BorderPane contains page title
		BorderPane topBorder = new BorderPane();
		topBorder.setPadding(new Insets(10, 10, 10, 10));
		topBorder.setLeft(label);

		// BorderPane contains table view and other BorderPanes
		BorderPane borderPane = new BorderPane();
		borderPane.setBottom(BottomBorderPane);
		borderPane.setCenter(tableView);
		borderPane.setTop(topBorder);
		BorderPane.setMargin(tableView, new Insets(0, 10, 0, 10));

		// setting scene
		Scene scene = new Scene(borderPane, 810, 450);
		Node node = (Node) event.getSource();
		Stage thisStage = (Stage) node.getScene().getWindow();
		thisStage.setScene(scene);
		thisStage.show();
	    
	}// End of showTeamMembers Method.
	
	public void showNotification() {
		String ContentText = "";
		CompetitionManger competitionManger;
		for(int i = 0 ; i< competitions.size();++i) {
			
			competitionManger = new CompetitionManger(competitions.get(i));
			if(competitions.get(i).getStatus().equals("Off") && !competitionManger.isUpdated())
				ContentText += "\n"+competitions.get(i).getName();
		
		}
		if(!ContentText.isEmpty()) {
			Alert alertWarning = new Alert(AlertType.WARNING);
	        alertWarning.setTitle("Warning Alert");
	        alertWarning.setHeaderText("Update The Winners");
	        alertWarning.setContentText("Update these competitions:"+ ContentText);
	        alertWarning.showAndWait();
		}
		
	}
	
//-------------------------------------------------------------------------------------------	
} // End of competitionsTracker Class
