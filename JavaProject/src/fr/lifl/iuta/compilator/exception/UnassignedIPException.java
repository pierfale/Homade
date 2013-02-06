package fr.lifl.iuta.compilator.exception;

@SuppressWarnings("serial")
public class UnassignedIPException extends Exception {
	
	private String message;
	
	public UnassignedIPException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return new String("Ip non assigné : "+message);
	}
	
	

}
