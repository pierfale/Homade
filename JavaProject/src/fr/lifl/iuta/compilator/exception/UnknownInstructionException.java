package fr.lifl.iuta.compilator.exception;

@SuppressWarnings("serial")
public class UnknownInstructionException extends Exception {
	
	private String message;
	
	public UnknownInstructionException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return new String("instruction non reconnue : "+message);
	}
}
	