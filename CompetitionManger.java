import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CompetitionManger {
	private Competition competition;

	CompetitionManger(Competition competition){
		this.competition = competition;
		
	}
	public static void browseWebSite() {

	}

	public void prepareEmail(Team team) throws Exception {
	
		if(competition instanceof CompetitionTB) {	
			ArrayList<String> addressesEmail = new ArrayList<String>();
				for(Student student : team.getMembers())
					addressesEmail.add("s" +student.getId()+"@kfupm.edu.sa");
				
				
			String receivers = String.join(";",addressesEmail);
			String subject = "Congratulation on achieving "+ team.getRank() + " place in "+ competition.getName();
			String message = "";
			
		
			File file = new File("Email Body template.txt");
		    try (Scanner input = new Scanner(file)) {
				while(input.hasNext())
					message = message + input.nextLine() +"\n";
				}
		    message = message.replace("[Student name/Team name]", team.getTeamName());
		    message = message.replace("[Competition name]", competition.getName());
		    prepareEmail.SendEmail(receivers,subject,message);
		    }
		
	}
	
    public void prepareEmail(Student student) throws Exception {
    	
    	if(competition instanceof CompetitionSolo) {
    		
    		String receiver = "s" + student.getId() + "@kfupm.edu.sa";
			String subject = "Congratulation on achieving "+student.getRank() + " place in "+ competition.getName();
			String message = "";
    		
			File file = new File("Email Body template.txt");
		    try (Scanner input = new Scanner(file)) {
				while(input.hasNext())
					message = message + input.nextLine() +"\n";
				}
		    message = message.replace("[Student name/Team name]", student.getName());
		    message = message.replace("[Competition name]", competition.getName());
		    prepareEmail.SendEmail(receiver,subject,message);
		    
		    
		    
    	}
    	
    }


	public void showNotification() {
		
	}
	public ArrayList<String> ReturnParticipants() {
		
		ArrayList<String> allInfo = new ArrayList<String>();
		if(competition instanceof CompetitionSolo) {
    		
			ArrayList<Student> x = ((CompetitionSolo) competition).getIndividuals();
			
			for(int i = 0 ; i< x.size(); ++i) 
				allInfo.add(x.get(i).toString());

		}
		
		
		
		
		
		return allInfo;
		
	}
}