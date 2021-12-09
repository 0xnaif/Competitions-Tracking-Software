import java.util.ArrayList;

public class Team {
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
	public int getTeamNumber() {

		return teamNumber;
	}
	public int getRank() {

		return rank;
	}
}