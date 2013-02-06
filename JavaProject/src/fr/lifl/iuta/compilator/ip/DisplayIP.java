package fr.lifl.iuta.compilator.ip;

public class DisplayIP implements IP {

	public int[] exec(int[] in) {
		System.out.println("[DISPLAY] "+in[0]);
		return in;
	}
	
	public String toString() {
		return "Affichage";
	}


}