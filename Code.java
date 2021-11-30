
import java.util.ArrayList;
import java.util.Date;

public class Project {

	public static void main(String[] args) {
		UserInterface test = new UserInterface();
		CompetitionTB competition = new CompetitionTB(new Date(1, 12, 2021), "https://ultrahack.org/aiot-hackathon-stc",
				"AIoT Hackathon with stc");
		CompetitionSolo competition2 = new CompetitionSolo(new Date(3, 12, 2021), "//twitter.com/CyberhubSa",
				"CyberuHub");
		Team team = new Team("SuperDevops", 1);
		Team team2 = new Team("StackUnderflow", 2);
		team.addStudent(new Student(222243860, "CS", "Bassel Alqahtani"));
		team.addStudent(new Student(222246560, "SWE", "Naif Essam"));
		team2.addStudent(new Student(222219260, "COE", "Majed Ahmad"));
		team2.addStudent(new Student(222267500, "COE", "Saleh Mohammed"));
		competition.addTeam(team);
		competition.addTeam(team2);
		test.addCompetition(competition);

		competition2.addStudent(new Student(222253860, "CS", "Ahmad Mohammed"));
		competition2.addStudent(new Student(222256560, "EE", "Abdullah Ali"));
		competition2.addStudent(new Student(222279260, "MIS", "Abdulaziz fawwaz"));
		competition2.addStudent(new Student(222256700, "SWE", "Faris Ahmad"));
		test.addCompetition(competition2);

		for (int i = 0; i < test.getCompetitions().size(); i++) {
			Competition curr = test.getCompetitions().get(i);
			System.out.println("Competition Name: " + curr.getName());
			if (curr instanceof CompetitionTB) {
				CompetitionTB currTB = (CompetitionTB) curr;
				for (int j = 0; j < currTB.getTeam().size(); j++) {
					Team teamTB = currTB.getTeam().get(j);
					System.out.println("Team Name: " + teamTB.getTeamName());
					System.out.print("Team Student Names:");
					for (int z = 0; z < teamTB.getMembers().size(); z++) {
						Student studentTB = teamTB.getMembers().get(z);
						System.out.print(" " + studentTB.getName());
					}
					System.out.println();
					System.out.println();
				}
			} else {
				CompetitionSolo currSolo = (CompetitionSolo) curr;
				System.out.print("Competition Members:");
				for (int j = 0; j < currSolo.getIndividuals().size(); j++) {
					Student studentSolo = currSolo.getIndividuals().get(j);
					System.out.print(" " + studentSolo.getName());
				}
			}
			System.out.println();
		}
	}

}

class UserInterface {
	private ArrayList<Competition> competitions;

	public UserInterface() {
		competitions = new ArrayList<Competition>();
	}

	public void addCompetition(CompetitionTB competition) {
		competitions.add(competition);
	}

	public ArrayList<Competition> getCompetitions() {
		return competitions;
	}
}

class Competition {
	private Date endDate;
	private String link;
	private String name;

	public Competition(Date endDate, String link, String name) {
		this.endDate = endDate;
		this.link = link;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}

class CompetitionManger {
	
	private Competition competition;
	
	public CompetitionManger(Competition competition) {
		this.competition = competition;
	}

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

	public ArrayList<Team> getTeams() {
		return teams;
	}

}

class CompetitionSolo extends Competition {
	private ArrayList<Student> individuals;

	public CompetitionSolo(Date endDate, String link, String name) {
		super(endDate, link, name);
		individuals = new ArrayList<Student>();
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
	
	public String getTeamName() {
		return teamName;
	}
}

class Student {
	private int id;
	private String major;
	private String name;
	private int rank;

	public Student(int id, String major, String name) {
		this.id = id;
		this.major = major;
		this.name = name;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}
}
