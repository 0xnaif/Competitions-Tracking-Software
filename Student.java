
public class Student{
	private int id;
	private String major;
	private String name;
	private String rank;
	

	public Student(int id, String major, String name) {

		this.rank = "Unknown";
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
	public int getId() {
		
		return id;
	}
	public String getRank() {

		return rank;
	}
	public String getMajor() {

		return major;
	}
		
}