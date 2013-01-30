package exception;

@SuppressWarnings("serial")
public class FileNotFoundException extends Exception {
	
	private String message;
	
	public FileNotFoundException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return new String("Fichier non trouver : "+message);
	}

}
