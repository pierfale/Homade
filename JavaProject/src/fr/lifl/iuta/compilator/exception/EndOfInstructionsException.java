package fr.lifl.iuta.compilator.exception;

@SuppressWarnings("serial")
public class EndOfInstructionsException extends Exception {
	
	private String message;
	
	public EndOfInstructionsException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return new String("fin des instructions : "+message);
	}

}
