
public class prepareEmail {
	public static void SendEmail(String receiver, String subject, String body) throws Exception {

		// Generating mailto-URI.
		String mailto = "mailto:\"" + receiver + ("?subject=" + uriEncode(subject)) + ("&body=" + uriEncode(body))
				+ "\"";

		// To know the type of operating system
		String os = System.getProperty("os.name").toLowerCase();

		// To set the command that will open the default email application.
		String cmd = "";
		if (os.contains("win"))
			cmd = "cmd.exe /c start " + mailto;
		else if (os.contains("osx"))
			cmd = "open " + mailto;
		else if (os.contains("nix") || os.contains("aix") || os.contains("nux"))
			cmd = "xdg-open " + mailto;

		// Call the default email application.
		Runtime.getRuntime().exec(cmd);
	}

	private static String uriEncode(String input) {
		String output = new String();
		for (char ch : input.toCharArray())
			output += Character.isLetterOrDigit(ch) ? ch : String.format("%%%02X", (int) ch);
		return output;
	}
}
