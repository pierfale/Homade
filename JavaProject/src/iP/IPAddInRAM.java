package iP;

import exception.InvalideAdressException;
import exception.UnloadedRAMException;
import processor.RAM;

public class IPAddInRAM implements IP {

	public int[] exec(int[] in) {
		try {
			RAM.set(in[1], in[0]);
		} catch (InvalideAdressException e) {
			e.printStackTrace();
		} catch (UnloadedRAMException e) {
			e.printStackTrace();
		}
		return in;
	}
	
	public String toString() {
		return "Ajout valeur dans la RAM";
	}


}