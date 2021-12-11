import java.util.ArrayList;
import java.util.Date;

public class CompetitionTB extends Competition {
	private ArrayList<Team> teams;

	public CompetitionTB(String link, String name) {
		super(link, name,"Team Basd");
		teams = new ArrayList<Team>();
	}
	public void setDate(int year, int month,int day) {
		super.setDate(year, month, day);
	}
	public void addTeam(Team team) {
		teams.add(team);
	}
	public ArrayList<Team> getTeams() {
		return teams;
	}
}
