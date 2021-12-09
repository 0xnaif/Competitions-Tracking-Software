import java.util.ArrayList;


public class CompetitionSolo extends Competition {
	private ArrayList<Student> individuals;

	public CompetitionSolo(String link, String name) {
		super(link, name,"Solo");
		individuals = new ArrayList<Student>();
	}

	public void setDate(int year, int month,int day) {
		super.setDate(year, month, day);
	}
	public void addStudent(Student student) {
		individuals.add(student);
	}

	public ArrayList<Student> getIndividuals() {
		return individuals;
	}

}
