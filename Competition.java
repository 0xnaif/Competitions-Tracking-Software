import java.time.LocalDate;

public class Competition {
	private LocalDate date;
	private String link;
	private String name;
	private String type;

	public Competition(String link, String name,String type) {
		this.type = type;
		this.link = link;
		this.name = name;
	}
	
	public String getName() {

		return name;
	}
	public String getType() {

		return type;
	}
	public String getLink() {

		return link;
	}
	public void setDate(int year, int month,int day) {
		this.date = LocalDate.of(year, month, day);
	}
	public LocalDate getDate() {

		return date;
	}
}