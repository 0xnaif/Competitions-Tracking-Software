
import java.util.ArrayList;
import java.util.Date;

public class Project {

	public static void main(String[] args) {
		UserInterface test = new UserInterface();
		test.addCompetition(new CompetitionTB(new Date(1, 12, 2021), "//twitter.com/CyberhubSa", "CyberuHub"));
		test.getCompetition().get(0).addTeam(new Team("Team 1", 1));
		test.getCompetition().get(0).getTeam().get(0).addStudent(new Student(201955680, "CS", "Ahmad"));
		test.getCompetition().get(0).getTeam().get(0).addStudent(new Student(201948216, "SWE", "lbrahim"));
		test.getCompetition().get(0).getTeam().get(0).addStudent(new Student(201955680, "COE", "Taqi Al-Din"));
		int size = test.getCompetition().get(0).getTeam().get(0).getMembers().size();
		for (int i = 0; i < size; i++)
			System.out.println(test.getCompetition().get(0).getTeam().get(0).getMembers().get(i).getName());
	}

}

class UserInterface {
	private ArrayList<CompetitionTB> competitions;

	public UserInterface() {
		competitions = new ArrayList<CompetitionTB>();
	}

	public void addCompetition(CompetitionTB competition) {
		competitions.add(competition);
	}

	public ArrayList<CompetitionTB> getCompetition() {
		return competitions;
	}
}

class Competition {
	private Date endDate;
	private String link;
	private String name;

	public Competition(Date endDate, String link, String name) {
		this.endDate = new Date();
		this.link = link;
		this.name = name;
	}
}

class CompetitionManger {
	private Competition competition;

	public void browseWebSite() {

	}

	public void sendEmail() {

	}

	public void showNotification() {

	}

	public void showParticipants() {

	}
}

class CompetitionTB extends Competition {
	private ArrayList<Team> teams;

	public CompetitionTB(Date endDate, String link, String name) {
		super(endDate, link, name);
		teams = new ArrayList<Team>();
	}

	public void addTeam(Team team) {
		teams.add(team);
	}

	public ArrayList<Team> getTeam() {
		return teams;
	}

}

class CompetitionSolo extends Competition {
	private ArrayList<Student> individuals;

	public CompetitionSolo(Date endDate, String link, String name) {
		super(endDate, link, name);
	}

	public void addStudent(Student student) {
		individuals.add(student);
	}

	public ArrayList<Student> getIndividuals() {
		return individuals;
	}

}

class Team {
	private String teamName;
	private int teamNumber;
	private int rank;
	ArrayList<Student> members;

	public Team(String teamName, int teamNumber) {
		this.teamName = teamName;
		this.teamNumber = teamNumber;
		members = new ArrayList<Student>();
	}

	public void addStudent(Student student) {
		members.add(student);
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public ArrayList<Student> getMembers() {
		return members;
	}
}

class Student {
	private int id;
	private String major;
	private String name;
	private String rank;

	public Student(int id, String major, String name) {
		this.id = id;
		this.major = major;
		this.name = name;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}
}
