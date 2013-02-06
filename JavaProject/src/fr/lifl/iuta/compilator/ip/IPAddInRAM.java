package fr.lifl.iuta.compilator.ip;

import fr.lifl.iuta.compilator.exception.InvalideAdressException;
import fr.lifl.iuta.compilator.exception.UnloadedRAMException;
import fr.lifl.iuta.compilator.processor.RAM;

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