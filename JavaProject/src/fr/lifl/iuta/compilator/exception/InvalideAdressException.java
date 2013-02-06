package fr.lifl.iuta.compilator.exception;

@SuppressWarnings("serial")
public class InvalideAdressException extends Exception {
	
	private String message;
	
	public InvalideAdressException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return new String("adresse invalide : "+message);
	}

}
