package Exception;

@SuppressWarnings("serial")
public class IncorrectFormatSource extends Exception {
	
	private String message;
	private int line;
	
	public IncorrectFormatSource(String message, int line) {
		this.message = message;
		this.line = line;
	}
	
	public String toString() {
		return new String("Code source incorrecte : "+message+" à la ligne "+line);
	}

}
