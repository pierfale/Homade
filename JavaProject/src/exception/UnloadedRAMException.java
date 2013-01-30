package exception;

@SuppressWarnings("serial")
public class UnloadedRAMException extends Exception {
	
	public UnloadedRAMException() {
		
	}
	
	public String toString() {
		return new String("memoire RAM non chargé");
	}

}
