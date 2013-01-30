package exception;

@SuppressWarnings("serial")
public class IncorrectFormatSourceException extends Exception {
	
	private String message;
	private int line;
	
	public IncorrectFormatSourceException(String message, int line) {
		this.message = message;
		this.line = line;
	}
	
	public String toString() {
		return new String("Code source incorrecte : "+message+" à la ligne "+line);
	}

}
