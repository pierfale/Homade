package iP;

public class DisplayIP implements IP {

	public int[] exec(int[] in) {
		System.out.println("[DISPLAY] "+in[0]);
		return new int[0];
	}
	
	public String toString() {
		return "Display";
	}


}