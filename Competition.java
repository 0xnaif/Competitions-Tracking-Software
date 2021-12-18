
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Competition {
	private LocalDate date;
	private String link;
	private String name;
	private String type;
	private String status;

	public Competition(String link, String name, String type) {
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

	public String getStatus() {
		return status;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(int year, int month, int day) {
		this.date = LocalDate.of(year, month, day);
		try {
			updateStatus();
		} catch (ParseException e) {
		}
	}

	public void updateStatus() throws ParseException {
		String year = this.date.getYear() + "-";
		String month = this.date.getMonthValue() + "-";
		String day = this.date.getDayOfMonth() + "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(year + month + day);
		Date dateToday = new Date();

		if (date.compareTo(dateToday) > 0)
			this.status = "On";
		else
			this.status = "Off";
	}
}
